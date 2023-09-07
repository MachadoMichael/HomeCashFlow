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
    UserRepository repository;

    public User save(User user) {
        return repository.save(user);
    }

    public List<User> getAll() {
        return repository.findAll();
    }

    public Optional<User> getOne(UUID user_id) {
        return repository.findById(user_id);
    }

    public User getByEmail(String email) {
        return repository.findByEmail(email);
    }

    public void delete(User user) {
        repository.delete(user);
    }
}
