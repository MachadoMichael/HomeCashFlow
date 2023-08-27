package com.machado.HomeCashFlow.controllers;

import com.machado.HomeCashFlow.dtos.ExpenseDTO;
import com.machado.HomeCashFlow.entities.Expense;
import com.machado.HomeCashFlow.entities.ExpenseCategory;
import com.machado.HomeCashFlow.repositories.ExpenseRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RestController
public class ExpenseController {

    @Autowired
    ExpenseRepository expenseRepository;

    @PostMapping("/expense")
    public ResponseEntity<Expense> save(@RequestBody @Valid ExpenseDTO expenseDTO) {
        Expense expenseModel = new Expense();
        BeanUtils.copyProperties(expenseDTO, expenseModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(expenseRepository.save(expenseModel));
    }

    @GetMapping("/expense")
    public ResponseEntity<List<Expense>> getAllExpense() {
        return ResponseEntity.status(HttpStatus.OK).body(expenseRepository.findAll());
    }

    @GetMapping("/expense/{id}")
    public ResponseEntity<Object> getOne(@PathVariable(value = "id") UUID expense_id) {
        Optional<Expense> expense = expenseRepository.findById(expense_id);
        return expense.<ResponseEntity<Object>>map(
                value -> ResponseEntity.status(HttpStatus.OK).body(value)).orElseGet(
                () -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Expense not found."));
    }

    @PutMapping("/expense/{id}")
    public ResponseEntity<Object> update(@PathVariable(value = "id") UUID expense_id,
                                         @RequestBody @Valid ExpenseDTO expenseDTO) {
        Optional<Expense> expense = expenseRepository.findById(expense_id);
        if (expense.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Expense not found.");
        }
        BeanUtils.copyProperties(expenseDTO, expense.get());
        return ResponseEntity.status(HttpStatus.OK).body(expenseRepository.save(expense.get()));
    }

    @DeleteMapping("/expense/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id")UUID expense_id){
        Optional<Expense> expense = expenseRepository.findById(expense_id);
        if(expense.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Expense not found.");
        }
        expenseRepository.delete(expense.get());
        return ResponseEntity.status(HttpStatus.OK).body("Expense deleted successfully.");
    }


}
