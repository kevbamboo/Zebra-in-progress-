package com.zebra.vault;

import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseInitializer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) {
        jdbcTemplate.execute("""
                    CREATE TABLE IF NOT EXISTS merchants (
                        id UUID PRIMARY KEY
                    )
                """);

        jdbcTemplate.execute("""
                    CREATE TABLE IF NOT EXISTS payment_methods (
                        id UUID PRIMARY KEY,
                        merchant_id UUID NOT NULL REFERENCES merchants(id),
                        encryption_key_version SMALLINT NOT NULL,
                        encrypted_pan BYTEA NOT NULL,
                        last_four_digits VARCHAR(4) NOT NULL,
                        expiration_month SMALLINT NOT NULL CHECK (expiration_month BETWEEN 1 AND 12),
                        expiration_year SMALLINT NOT NULL,
                        card_brand VARCHAR(20) NOT NULL
                    )
                """);

        System.out.println("VAULT TABLES CREATED!");
    }
}
