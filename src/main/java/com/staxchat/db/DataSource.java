package com.staxchat.db;

import com.staxchat.constants.Constants;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {
    private static HikariConfig hikariConfig = new HikariConfig();
    private static HikariDataSource dataSource;

    static {
        hikariConfig.setJdbcUrl(Constants.DB_URL);
        hikariConfig.setUsername(Constants.DB_USERNAME);
        hikariConfig.setPassword(Constants.DB_PASSWORD);
        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        dataSource = new HikariDataSource(hikariConfig);
    }

    private DataSource() {}

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
