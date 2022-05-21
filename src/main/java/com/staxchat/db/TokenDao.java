package com.staxchat.db;

import com.staxchat.constants.ErrorMessages;
import com.staxchat.db.model.Role;
import com.staxchat.db.model.Token;

import java.awt.*;
import java.sql.*;
import java.util.Arrays;

public class TokenDao {
    public static Token findToken(String token) throws SQLException {
        Connection connection = DataSource.getConnection();

        String sql = "SELECT token, roleId, r.role FROM tokens t INNER JOIN roles r ON r.id = t.roleId WHERE token = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, token);

        ResultSet resultSet = statement.executeQuery();

        if (!resultSet.next()) {
            return null;
        }

        Role role = new Role();
        role.setRole(resultSet.getString("role"));
        role.setId(resultSet.getLong("roleId"));

        return new Token(resultSet.getString("token"), role);
    }
}
