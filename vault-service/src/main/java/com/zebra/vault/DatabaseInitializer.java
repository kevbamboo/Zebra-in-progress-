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
                    CREATE TABLE IF NOT EXISTS vault (
                        id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                        card_number VARCHAR(19) NOT NULL,
                        expiration_month SMALLINT NOT NULL,
                        expiration_year SMALLINT NOT NULL,
                        cardholder_name VARCHAR(255) NOT NULL
                    )
                """);

        System.out.println("VAULT TABLE CREATED!");
    }
}