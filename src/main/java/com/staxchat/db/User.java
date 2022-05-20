package com.staxchat.db;

import com.staxchat.constants.ErrorMessages;
import com.staxchax.core.exception.StaxException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


public class User extends BaseEntity {
    private String username;
    private String password;
    private String token;

    private static Logger logger = Logger.getLogger(User.class);

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static User findUserByUsername(String username) {
//        String sql = "SELECT * FROM user WHERE username = ?";
        String sql = "SELECT * FROM `user`";
        try {

            Connection connection = DataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rowData = statement.executeQuery();

            if (!rowData.next()) {
                throw new StaxException(ErrorMessages.USER_NOT_FOUND);
            }

            User user = new User();
            user.setId(rowData.getLong("id"));
            user.setUsername(rowData.getString("username"));
            user.setPassword(rowData.getString("password"));
            user.setToken(rowData.getString("token"));

            return user;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new StaxException(e.getMessage());
        }

    }

}
