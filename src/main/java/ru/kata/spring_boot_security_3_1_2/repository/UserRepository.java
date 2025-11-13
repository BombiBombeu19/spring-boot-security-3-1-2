package ru.kata.spring_boot_security_3_1_2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kata.spring_boot_security_3_1_2.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
