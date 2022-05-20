package com.staxchat.db;

import com.staxchat.constants.ErrorMessages;

import java.sql.*;

public class User extends BaseEntity {
    private String username;
    private String password;
    private String token;

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
        String sql = "SELECT * FROM user WHERE username = ?";

        try {
            Connection connection = DataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();

            if(!resultSet.next()) {
                throw new SQLDataException(ErrorMessages.USER_NOT_FOUND);
            }

            User user = new User();
            user.setId(resultSet.getLong("id"));
            user.setUsername(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));
            user.setToken(resultSet.getString("token"));

            return user;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

}
