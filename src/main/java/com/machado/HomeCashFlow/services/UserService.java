package com.machado.HomeCashFlow.services;

import com.machado.HomeCashFlow.entities.User;
import com.machado.HomeCashFlow.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> getOne(UUID user_id) {
        return userRepository.findById(user_id);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }
}
