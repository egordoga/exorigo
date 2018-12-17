package ex.service;

import ex.dao.UserDao;
import ex.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserServise {

    @Autowired
    private UserDao userDao;


    @Override
    public List<User> findUsers() {
        return userDao.findAll();
    }

    @Override
    public List<User> findUsersByName(String name) {
        return userDao.findUsersByName(name);
    }

    @Override
    public List<User> findUsersByLastName(String lastMame) {
        return userDao.findUsersByLastName(lastMame);
    }

    @Override
    public void addUser(User user) {
        userDao.addUser(user);
    }
}
