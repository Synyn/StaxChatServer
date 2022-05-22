package com.staxchat.db;


import com.staxchat.constants.Constants;
import com.staxchat.core.exception.StaxException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource dataSource;

    static {
        config.setUsername(Constants.DB_USERNAME);
        config.setPassword(Constants.DB_PASSWORD);
        config.setJdbcUrl(Constants.DB_JDBC_URL);

        config.addDataSourceProperty( "cachePrepStmts" , "true" );
        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );

        dataSource = new HikariDataSource(config);
    }

    private DataSource() {
    }

    public static Connection getConnection() {
        try { return dataSource.getConnection();}
        catch (SQLException sqlException) {
            throw new StaxException(sqlException.getMessage());
        }
    }
}
