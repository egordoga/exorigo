package ex.service;

import ex.entity.User;

import java.util.List;

public interface IUserServise {
    List<User> findUsers();

    List<User> findUsersByName(String name);

    List<User> findUsersByLastName(String lastMame);

    void addUser(User user);
}
