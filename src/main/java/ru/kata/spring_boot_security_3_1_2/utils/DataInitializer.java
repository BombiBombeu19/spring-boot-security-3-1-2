package ru.kata.spring_boot_security_3_1_2.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.kata.spring_boot_security_3_1_2.model.Role;
import ru.kata.spring_boot_security_3_1_2.repository.RoleRepository;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {
        createRoleIfNotExists("ROLE_USER");
        createRoleIfNotExists("ROLE_ADMIN");
    }

    private void createRoleIfNotExists(String roleName) {
        roleRepository.findByAuthority(roleName)
                .orElseGet(() -> roleRepository.save(new Role(roleName)));
    }
}
