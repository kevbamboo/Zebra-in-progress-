package com.zebra.vault;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;
import org.springframework.core.env.Environment;

@Service
public class PanEncryptionService {
    private static final String ALGORITHM = "AES/GCM/NoPadding";
    private static final int IV_LENGTH = 12;
    private static final int TAG_LENGTH = 128;

    private final Map<Integer, SecretKey> keys;
    private final int activeKeyVersion;
    private final SecureRandom secureRandom = new SecureRandom();

    public PanEncryptionService(Environment environment) {
        activeKeyVersion = Integer.parseInt(environment.getRequiredProperty("PAN_ACTIVE_KEY_VERSION"));
        Map<Integer, SecretKey> loadedKeys = new HashMap<>();
        for (String value : environment.getRequiredProperty("PAN_KEY_VERSIONS").split(",")) {
            int version = Integer.parseInt(value.trim());
            if (version < 1 || version > Short.MAX_VALUE || loadedKeys.containsKey(version)) {
                throw new IllegalStateException("PAN key versions must be unique positive SMALLINT values");
            }
            String property = "PAN_ENCRYPTION_KEY_V" + version;
            String encodedKey = environment.getRequiredProperty(property);
            byte[] keyBytes;
            try {
                keyBytes = Base64.getDecoder().decode(encodedKey);
            } catch (IllegalArgumentException e) {
                throw new IllegalStateException(property + " must be valid Base64");
            }
            if (keyBytes.length != 32) {
                throw new IllegalStateException(property + " must decode to 32 bytes");
            }
            loadedKeys.put(version, new SecretKeySpec(keyBytes, "AES"));
        }
        if (!loadedKeys.containsKey(activeKeyVersion)) {
            throw new IllegalStateException("The active PAN key version is not configured");
        }
        keys = Map.copyOf(loadedKeys);
    }

    public int getActiveKeyVersion() {
        return activeKeyVersion;
    }

    public byte[] encrypt(String pan) {
        try {
            byte[] iv = new byte[IV_LENGTH];
            secureRandom.nextBytes(iv);

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(
                    Cipher.ENCRYPT_MODE,
                    keys.get(activeKeyVersion),
                    new GCMParameterSpec(TAG_LENGTH, iv));

            byte[] ciphertext = cipher.doFinal(pan.getBytes(StandardCharsets.UTF_8));

            byte[] result = new byte[iv.length + ciphertext.length];

            System.arraycopy(iv, 0, result, 0, iv.length);
            System.arraycopy(ciphertext, 0, result, iv.length, ciphertext.length);

            return result;
        } catch (Exception e) {
            throw new IllegalStateException("Failed to encrypt PAN", e);
        }
    }

    public String decrypt(byte[] encryptedPan, int keyVersion) {
        if (encryptedPan == null || encryptedPan.length < IV_LENGTH + TAG_LENGTH / Byte.SIZE) {
            throw new IllegalArgumentException("Encrypted PAN must contain a 12-byte IV and a 16-byte authentication tag");
        }

        SecretKey key = keys.get(keyVersion);
        if (key == null) {
            throw new IllegalArgumentException("Unknown PAN key version: " + keyVersion);
        }

        try {
            byte[] iv = Arrays.copyOfRange(
                    encryptedPan,
                    0,
                    IV_LENGTH);

            byte[] ciphertext = Arrays.copyOfRange(
                    encryptedPan,
                    IV_LENGTH,
                    encryptedPan.length);

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(
                    Cipher.DECRYPT_MODE,
                    key,
                    new GCMParameterSpec(TAG_LENGTH, iv));

            byte[] plaintext = cipher.doFinal(ciphertext);

            return new String(plaintext, StandardCharsets.UTF_8);
        } catch (GeneralSecurityException e) {
            throw new IllegalStateException("Failed to decrypt PAN", e);
        }
    }
}
