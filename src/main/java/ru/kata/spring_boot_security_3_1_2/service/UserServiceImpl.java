package ru.kata.spring_boot_security_3_1_2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring_boot_security_3_1_2.dao.UserDao;
import ru.kata.spring_boot_security_3_1_2.model.User;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> listUsers() {
        return userDao.listUsers();
    }

    @Override
    @Transactional(readOnly = true)
    public User showUser(int id) {
        User user = userDao.showUser(id);
        if (user == null) {
            throw new EntityNotFoundException("Не существует пользователя с id: " + id);
        }
        return user;
    }

    @Override
    @Transactional
    public void addUser(User user) {
        userDao.addUser(user);
    }

    @Override
    @Transactional
    public void updateUser(int id, User updatedUser) {
        User user = userDao.showUser(id);
        if (user == null) {
            throw new EntityNotFoundException("Не существует пользователя с id: " + id);
        }
        userDao.updateUser(id, updatedUser);
    }

    @Override
    @Transactional
    public void deleteUser(int id) {
        User user = userDao.showUser(id);
        if (user == null) {
            throw new EntityNotFoundException("Не существует пользователя с id: " + id);
        }
        userDao.deleteUser(id);
    }
}
