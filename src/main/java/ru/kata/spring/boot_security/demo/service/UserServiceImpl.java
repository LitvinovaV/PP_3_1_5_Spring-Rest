package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public User getUser(long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UsernameNotFoundException(String.format("User not found!"));
        }
    }

    @Override
    @Transactional
    public void deleteUser(long id) {
        userRepository.deleteById(id);

    }

    @Override
    @Transactional
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public void updateUser(User user, Long id) {
        User updateUser = userRepository.getById(id);
        updateUser.setId(user.getId());
        updateUser.setPassword(
                (updateUser.getPassword().equals(user.getPassword())) ?
                        user.getPassword() : bCryptPasswordEncoder.encode(user.getPassword()));
        updateUser.setAge(user.getAge());
        updateUser.setFirstName(user.getFirstName());
        updateUser.setLastName(user.getLastName());
        updateUser.setUsername(user.getUsername());
        updateUser.setRoles(user.getRoles());
        userRepository.save(updateUser);
    }

}

