package com.machado.HomeCashFlow.controllers;

import com.machado.HomeCashFlow.dtos.UserDTO;
import com.machado.HomeCashFlow.entities.User;
import com.machado.HomeCashFlow.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class UserController {


    @Autowired
    UserService userService;

    @PostMapping("/user")
    public ResponseEntity<Object> save(@RequestBody @Valid UserDTO userDTO) {
        User userModel = new User();
        BeanUtils.copyProperties(userDTO, userModel);
        List<User> filteredUsers = userService.getAll().stream().
                filter(user -> user.getEmail().equals(userModel.getEmail())).toList();

        if (!filteredUsers.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Email already registered.");

        return ResponseEntity.status(HttpStatus.OK).body(userService.save(userModel));
    }

    @GetMapping("/user")
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAll());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Object> getOne(@PathVariable(value = "id") UUID user_id) {

        Optional<User> user = userService.getOne(user_id);
        if (user.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<Object> update(@PathVariable(value = "id") UUID user_id,
                                         @RequestBody @Valid UserDTO userDTO) {

        Optional<User> user = userService.getOne(user_id);
        if (user.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");

        BeanUtils.copyProperties(userDTO, user.get());
        return ResponseEntity.status(HttpStatus.OK).body(userService.save(user.get()));
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") UUID user_id) {

        Optional<User> user = userService.getOne(user_id);
        if (user.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");

        User userModel = new User();
        BeanUtils.copyProperties(user, userModel);
        userService.delete(userModel);

        return ResponseEntity.status(HttpStatus.OK).body("User deleted with success");
    }
}