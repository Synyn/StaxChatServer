package com.staxchat.db;

import com.staxchat.constants.ErrorMessages;
import com.staxchat.db.model.User;
import com.staxchax.core.exception.StaxException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    public static User findUserByUsername(String username) {
        String sql = "SELECT * FROM user WHERE username = ?";
//        String sql = "SELECT * FROM `user`";
        try {

            Connection connection = DataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);

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
