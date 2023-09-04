package com.machado.HomeCashFlow.controllers;

import com.machado.HomeCashFlow.dtos.ExpenseDTO;
import com.machado.HomeCashFlow.entities.Expense;
import com.machado.HomeCashFlow.services.ExpenseService;
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
@RequestMapping("/expense")
public class ExpenseController {

    @Autowired
    ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid ExpenseDTO expenseDTO) {
        Expense expenseModel = new Expense();
        BeanUtils.copyProperties(expenseDTO, expenseModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(expenseService.save(expenseModel));
    }

    @GetMapping
    public ResponseEntity<List<Expense>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(expenseService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOne(@PathVariable(value = "id") UUID id) {

        Optional<Expense> expense = expenseService.getOne(id);
        if (expense.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Expense not found.");

        return ResponseEntity.status(HttpStatus.OK).body(expense);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable(value = "id") UUID id,
                                         @RequestBody @Valid ExpenseDTO expenseDTO) {

        Optional<Expense> expense = expenseService.getOne(id);
        if (expense.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Expense not found.");

        BeanUtils.copyProperties(expenseDTO, expense.get());
        return ResponseEntity.status(HttpStatus.OK).body(expenseService.save(expense.get()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") UUID id) {

        Optional<Expense> expense = expenseService.getOne(id);
        if (expense.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Expense not found.");

        expenseService.delete(id);

        return ResponseEntity.status(HttpStatus.OK).body("Expense deleted with success");
    }
}
