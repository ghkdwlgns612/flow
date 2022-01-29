package com.interpark.ncl;

import java.sql.SQLException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RecommendProductApplication {
    public static void main(String[] args) throws SQLException {
        SpringApplication.run(RecommendProductApplication.class, args);
    }
}
