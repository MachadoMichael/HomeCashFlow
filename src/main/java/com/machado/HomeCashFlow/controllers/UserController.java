package com.machado.HomeCashFlow.controllers;

import com.machado.HomeCashFlow.dtos.UserDTO;
import com.machado.HomeCashFlow.entities.User;
import com.machado.HomeCashFlow.services.user.UserService;
import com.machado.HomeCashFlow.services.user.UserServiceImp;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService service;

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid UserDTO userDTO) {
        User userModel = new User();
        BeanUtils.copyProperties(userDTO, userModel);
        User selectedUser = service.getByEmail(userDTO.email());

        if (selectedUser != null)
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Email already registered.");

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(userModel));
    }

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOne(@PathVariable(value = "id") UUID user_id) {

        Optional<User> user = service.getOne(user_id);
        if (user.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable(value = "id") UUID user_id,
                                         @RequestBody @Valid UserDTO userDTO) {

        Optional<User> user = service.getOne(user_id);
        if (user.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");

        BeanUtils.copyProperties(userDTO, user.get());
        return ResponseEntity.status(HttpStatus.OK).body(service.save(user.get()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") UUID user_id) {

        Optional<User> user = service.getOne(user_id);
        if (user.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");

        User userModel = new User();
        BeanUtils.copyProperties(user, userModel);
        service.delete(userModel);

        return ResponseEntity.status(HttpStatus.OK).body("User deleted with success");
    }
}
