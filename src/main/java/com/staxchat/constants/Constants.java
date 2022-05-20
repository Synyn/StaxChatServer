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
    public static final String DB_URL = "jdbc:mysql://localhost:3306/stax_db";
    public static final String DB_USERNAME = "stax_user";
    public static final String DB_PASSWORD = "test123";
}
