package ex.dao;

import ex.controller.UserRowMapper;
import ex.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao implements IUserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UserRowMapper userRowMapper;


    @Override
    public List<User> findAll() {
        String sql = "select * from users";
        return jdbcTemplate.query(sql, new UserRowMapper());
    }

    @Override
    public List<User> findUsersByName(String name) {
        String sql = "select * from users where name like '%" + name + "%'";
        return jdbcTemplate.query(sql, new UserRowMapper());
    }

    @Override
    public List<User> findUsersByLastName(String lastName) {
        String sql = "select * from users where last_name like '%" + lastName + "%'";
        return jdbcTemplate.query(sql, new UserRowMapper());
    }

    @Override
    public void addUser(User user) {
        String sql = "insert into users (name, last_name, pass) values(?, ?, ?)";
        jdbcTemplate.update(sql, new Object[]{user.getName(), user.getLastName(), user.getPass()});
    }

    @Override
    public void update(User user) {
        String sql = "update  users set name=?, last_name=? where id=?";
        jdbcTemplate.update(sql, new Object[]{user.getName(), user.getLastName(), user.getId()});
    }

    @Override
    public void delete(Long id) {
        String sql = "delete from users where id=?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public User find(Long id) {
        String sql = "select * from users where id=" + id;
        return jdbcTemplate.queryForObject(sql, new UserRowMapper());
    }
}
