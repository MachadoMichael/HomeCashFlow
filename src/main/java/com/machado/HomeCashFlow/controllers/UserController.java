package com.machado.HomeCashFlow.controllers;

import com.machado.HomeCashFlow.dtos.ExpenseDTO;
import com.machado.HomeCashFlow.entities.Expense;
import com.machado.HomeCashFlow.services.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class UserController {


    @Autowired
    ExpenseService userService;

    @PostMapping("/user")
    public ResponseEntity<Expense> save(@RequestBody @Valid ExpenseDTO userDTO) {
        return userService.save(userDTO);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Expense>> getAll() {
        return userService.getAll();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Object> getOne(@PathVariable(value = "id") UUID user_id) {
        return userService.getOne(user_id);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<Object> update(@PathVariable(value = "id") UUID user_id,
                                         @RequestBody @Valid ExpenseDTO userDTO) {
        return userService.update(user_id, userDTO);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") UUID user_id) {
        return userService.delete(user_id);
    }
}
