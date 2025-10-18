package ru.kata.spring_boot_security_3_1_2.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring_boot_security_3_1_2.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<User> listUsers() {
        return entityManager.createQuery("SELECT u from User u", User.class).getResultList();
    }

    @Override
    public User showUser(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void addUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void updateUser(int id, User updatedUser) {
        User userToUpdate = entityManager.find(User.class, id);

        if (userToUpdate != null) {
            userToUpdate.setName(updatedUser.getName());
            userToUpdate.setLastName(updatedUser.getLastName());
            userToUpdate.setAge(updatedUser.getAge());
        }
    }

    @Override
    public void deleteUser(int id) {
        User user = entityManager.find(User.class, id);

        if (user != null) {
            entityManager.remove(user);
        }
    }
}
