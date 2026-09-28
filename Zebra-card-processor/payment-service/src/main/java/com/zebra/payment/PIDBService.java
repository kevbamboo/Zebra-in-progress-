package main.java.com.zebra.payment;

import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

// Payment Intent DataBase Service
@Service
public class PIDBService {
    private final JdbcTemplate jdbcTemplate;

    public PIDBService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
