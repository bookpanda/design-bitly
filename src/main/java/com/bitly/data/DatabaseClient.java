package com.bitly.data;

import javax.sql.DataSource;
import org.springframework.stereotype.Component;

@Component
public class DatabaseClient {
    public final DataSource dataSource;

    public DatabaseClient(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
