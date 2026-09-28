package com.zebra.vault;

import com.zebra.vault.payment_method.PaymentMethod;

import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class VaultService {
    private final JdbcTemplate jdbcTemplate;

    public VaultService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String checkPaymentMethod(String merchantId, String pmId, long amount, String currency) {
        PaymentMethod pm = jdbcTemplate.queryForObject(
                """
                        SELECT merchant_id, encryption_key_version, encrypted_pan, last_four_digits, expiration_month, expiration_year, card_brand
                        FROM payment_methods
                        WHERE id = ? AND merchant_id = ?
                        """,
                (rs, rowNum) -> new PaymentMethod(
                        rs.getString("merchant_id"),
                        rs.getInt("encryption_key_version"),
                        rs.getBytes("encrypted_pan"),
                        rs.getString("last_four_digits"),
                        rs.getInt("expiration_month"),
                        rs.getInt("expiration_year"),
                        rs.getString("card_brand")),
                UUID.fromString(pmId.substring(3)),
                UUID.fromString(merchantId));

        return "";
    }

    public String savePaymentMethod(PaymentMethod pm) {
        UUID paymentMethodId = UUID.randomUUID();
        int result = jdbcTemplate.update("""
                INSERT INTO payment_methods (
                    id,
                    merchant_id,
                    encryption_key_version,
                    encrypted_pan,
                    last_four_digits,
                    expiration_month,
                    expiration_year,
                    card_brand
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                    """,
                paymentMethodId,
                UUID.fromString(pm.getMerchantId()),
                pm.getEncryptionKeyVersion(),
                pm.getEncryptedPan(),
                pm.getLastFourDigits(),
                pm.getExpMonth(),
                pm.getExpYear(),
                pm.getCardBrand());

        if (result != 1)
            throw new IllegalArgumentException(); // change
        return "pm_" + paymentMethodId;
    }
}
