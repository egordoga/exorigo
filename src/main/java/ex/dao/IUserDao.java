package ex.dao;


import ex.entity.User;

import java.util.List;

public interface IUserDao {
    List<User> findAll();

    List<User> findUsersByName(String name);

    List<User> findUsersByLastName(String lastName);

    void addUser(User user);

    void update(User user);

    void delete(Long id);

    User find(Long id);
}
