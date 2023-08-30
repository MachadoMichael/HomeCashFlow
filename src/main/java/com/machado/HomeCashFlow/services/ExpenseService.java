package com.machado.HomeCashFlow.services;

import com.machado.HomeCashFlow.dtos.ExpenseDTO;
import com.machado.HomeCashFlow.entities.Expense;
import com.machado.HomeCashFlow.repositories.ExpenseRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ExpenseService {
    @Autowired
    ExpenseRepository expenseRepository;

    public ResponseEntity<Expense> save(Expense expense) {
        return ResponseEntity.status(HttpStatus.CREATED).body(expenseRepository.save(expense));
    }

    public ResponseEntity<List<Expense>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(expenseRepository.findAll());
    }

    public ResponseEntity<Object> getOne(UUID expense_id) {
        Optional<Expense> expense = expenseRepository.findById(expense_id);
        return expense.<ResponseEntity<Object>>map(
                value -> ResponseEntity.status(HttpStatus.OK).body(value)).orElseGet(
                () -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Expense not found."));
    }

    public ResponseEntity<Object> update(UUID expense_id, ExpenseDTO expenseDTO) {
        Optional<Expense> expense = expenseRepository.findById(expense_id);
        if (expense.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Expense not found.");
        }
        BeanUtils.copyProperties(expenseDTO, expense.get());
        return ResponseEntity.status(HttpStatus.OK).body(expenseRepository.save(expense.get()));
    }

    public ResponseEntity<Object> delete(UUID expense_id) {
        Optional<Expense> expense = expenseRepository.findById(expense_id);
        if (expense.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Expense not found.");
        }
        expenseRepository.delete(expense.get());
        return ResponseEntity.status(HttpStatus.OK).body("Expense deleted successfully.");
    }
}
