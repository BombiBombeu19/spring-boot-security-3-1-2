package ru.kata.spring_boot_security_3_1_2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kata.spring_boot_security_3_1_2.model.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByAuthority(String authority);
}
