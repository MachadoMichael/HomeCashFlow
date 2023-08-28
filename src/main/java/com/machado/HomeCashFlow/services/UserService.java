package com.machado.HomeCashFlow.services;

import com.machado.HomeCashFlow.dtos.UserDTO;
import com.machado.HomeCashFlow.entities.Expense;
import com.machado.HomeCashFlow.entities.User;
import com.machado.HomeCashFlow.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserService {

    @Autowired
    UserRepository userRepository;

    public ResponseEntity<User> save(UserDTO userDTO) {
        User userModel = new User();
        BeanUtils.copyProperties(userDTO, userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userRepository.save(userRepository.save(userModel)));
    }

    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.findAll());
    }

    public ResponseEntity<Object> getOne(UUID user_id) {
        Optional<User> user = userRepository.findById(user_id);
        return user.<ResponseEntity<Object>>map(
                value -> ResponseEntity.status(HttpStatus.OK).body(value)).orElseGet(
                () -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found."));
    }

    public ResponseEntity<Object> update(UUID user_id, UserDTO userDTO) {
        Optional<User> user = userRepository.findById(user_id);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
        BeanUtils.copyProperties(userDTO, user.get());
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.save(user.get()));
    }

    public ResponseEntity<Object> delete(UUID user_id) {
        Optional<User> user = userRepository.findById(user_id);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
        userRepository.delete(user.get());
        return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully.");
    }
}
