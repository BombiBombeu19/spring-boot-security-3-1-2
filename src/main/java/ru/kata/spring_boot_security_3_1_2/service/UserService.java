package ru.kata.spring_boot_security_3_1_2.service;


import ru.kata.spring_boot_security_3_1_2.model.User;

import java.util.List;

public interface UserService {
    List<User> listUsers();

    User showUser(int id);

    void addUser(User user);

    void updateUser(int id, User updatedUser);

    void deleteUser(int id);
}
