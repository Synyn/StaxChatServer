package com.staxchat.constants;

import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

public class Constants {
    /**
     * For local environment
     */
    public static final String HOSTNAME = "127.0.0.1";
    public static final int PORT = 8080;
    public static final Charset DEFAULT_CHARSET = CharsetUtil.UTF_8;

    /**
     * Database Config
     */
    public static final String DB_HOSTNAME = "127.0.0.1";
    public static final Integer DB_PORT = 3306;
    public static final String DB_NAME = "stax_db";
    public static final String DB_JDBC_URL = "jdbc:mysql://" + Constants.DB_HOSTNAME + ":" + Constants.DB_PORT + "/" + Constants.DB_NAME;
    public static final String DB_USERNAME = "stax_user";
    public static final String DB_PASSWORD = "test123";
    public static final int DB_POOL_MAX_CONNECTIONS = 15;
    public static final long DB_POOL_MAX_IDLE_TIME = 3600;
}
