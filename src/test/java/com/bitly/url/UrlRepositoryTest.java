package com.bitly.url;

import com.bitly.data.DatabaseClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class UrlRepositoryTest {

    private UrlRepository urlRepository;
    private DatabaseClient databaseClient;

    @BeforeEach
    public void setUp() throws SQLException {
        // H2 in-memory database setup
        var dataSource = new SingleConnectionDataSource("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "sa", "", true);
        databaseClient = new DatabaseClient(dataSource);
        urlRepository = new UrlRepository(databaseClient);

        try (Connection conn = dataSource.getConnection()) {
            conn.createStatement().execute("""
                        CREATE TABLE IF NOT EXISTS urls (
                            short_code VARCHAR(255) PRIMARY KEY,
                            original_url VARCHAR(2048)
                        )
                    """);
        }
    }

    @Test
    public void testSaveAndFindUrlMapping() {
        String shortCode = "abc123";
        String originalUrl = "https://example.com";

        urlRepository.saveUrlMapping(shortCode, originalUrl);
        String result = urlRepository.findOriginalUrlByCode(shortCode);

        assertEquals(originalUrl, result);
    }

    @Test
    public void testFindNonexistentUrl() {
        assertNull(urlRepository.findOriginalUrlByCode("not_exist"));
    }
}
