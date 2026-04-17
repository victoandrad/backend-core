package com.victoandrad.backend.domain.user;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    // ==============================
    // FIELDS
    // ==============================

    private final UserRepository userRepository;

    // ==============================
    // CONSTRUCTORS
    // ==============================

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ==============================
    // METHODS
    // ==============================

    @Transactional
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}
