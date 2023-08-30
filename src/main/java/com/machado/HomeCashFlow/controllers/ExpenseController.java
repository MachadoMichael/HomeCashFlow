package com.machado.HomeCashFlow.controllers;

import com.machado.HomeCashFlow.dtos.ExpenseDTO;
import com.machado.HomeCashFlow.entities.Expense;
import com.machado.HomeCashFlow.services.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class ExpenseController {

    @Autowired
    ExpenseService expenseService;

    @PostMapping("/expense")
    public ResponseEntity<Expense> save(@RequestBody @Valid ExpenseDTO expenseDTO) {
        Expense expenseModel = new Expense();
        BeanUtils.copyProperties(expenseDTO, expenseModel);
        return expenseService.save(expenseModel);
    }

    @GetMapping("/expense")
    public ResponseEntity<List<Expense>> getAll() {
        return expenseService.getAll();
    }

    @GetMapping("/expense/{id}")
    public ResponseEntity<Object> getOne(@PathVariable(value = "id") UUID expense_id) {
        return expenseService.getOne(expense_id);
    }

    @PutMapping("/expense/{id}")
    public ResponseEntity<Object> update(@PathVariable(value = "id") UUID expense_id,
                                         @RequestBody @Valid ExpenseDTO expenseDTO) {
        return expenseService.update(expense_id, expenseDTO);
    }

    @DeleteMapping("/expense/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") UUID expense_id) {
      return expenseService.delete(expense_id);
    }


}
