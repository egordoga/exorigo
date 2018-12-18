package ex.controller;

import ex.entity.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserRowMapper implements RowMapper<User> {
    public User mapRow(ResultSet resultSet, int i) throws SQLException {

        return new User(resultSet.getLong("id"), resultSet.getString("name"),
                resultSet.getString("last_name"),
                resultSet.getString("pass"));
    }
}
