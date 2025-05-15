package com.bitly.url;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.bitly.data.DatabaseClient;

@Repository
public class UrlRepository {

    private final DatabaseClient databaseClient;

    public UrlRepository(DatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
    }

    public String findOriginalUrlByCode(String shortCode) {
        String sql = "SELECT original_url FROM urls WHERE short_code = ?";

        try (Connection conn = databaseClient.dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, shortCode);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("original_url");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error querying original url", e);
        }
        return null;
    }

    public void saveUrlMapping(String shortCode, String originalUrl) {
        String sql = "INSERT INTO urls (short_code, original_url) VALUES (?, ?)";

        try (Connection conn = databaseClient.dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, shortCode);
            ps.setString(2, originalUrl);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error saving url mapping", e);
        }
    }
}
