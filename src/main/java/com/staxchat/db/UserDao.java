package com.staxchat.db;

import com.staxchat.db.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    /**
     * Find a user by Username
     *
     * @param username - the username of the user
     * @return User - the found user
     * @throws SQLException - if there is some problem with the SQL
     */
    public static User findUserByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM user WHERE username = ?";
//        String sql = "SELECT * FROM `user`";

        Connection connection = DataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, username);

        ResultSet rowData = statement.executeQuery();

        if (!rowData.next()) {
            return null;
        }

        User user = new User();
        user.setId(rowData.getLong("id"));
        user.setUsername(rowData.getString("username"));
        user.setPassword(rowData.getString("password"));
        user.setToken(rowData.getString("token"));

        return user;


    }
}
