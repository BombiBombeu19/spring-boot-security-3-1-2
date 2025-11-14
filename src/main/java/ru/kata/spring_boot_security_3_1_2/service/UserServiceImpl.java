package ru.kata.spring_boot_security_3_1_2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring_boot_security_3_1_2.model.Role;
import ru.kata.spring_boot_security_3_1_2.repository.RoleRepository;
import ru.kata.spring_boot_security_3_1_2.repository.UserRepository;
import ru.kata.spring_boot_security_3_1_2.model.User;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository
            , RoleRepository roleRepository
            , PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User showUser(int id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Override
    @Transactional
    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Set<Role> roles = user.getRoles().stream()
                .map(role -> roleRepository.findById(role.getId())
                        .orElseThrow(() -> new RuntimeException("Role not found: "
                                + role.getId())))
                .collect(Collectors.toSet());

        user.setRoles(roles);

        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateUser(int id, User updatedUser) {
        User user = userRepository.getById(id);

        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setAge(updatedUser.getAge());
        user.setEmail(updatedUser.getEmail());
        user.setRoles(updatedUser.getRoles());

        if (!(updatedUser.getPassword() == null
                || updatedUser.getPassword().isBlank())) {
            user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found!");
        }
        return user.get();
    }
}
