package com.machado.HomeCashFlow.controllers;

import com.machado.HomeCashFlow.dtos.ExpenseDTO;
import com.machado.HomeCashFlow.dtos.ReportDTO;
import com.machado.HomeCashFlow.entities.Expense;
import com.machado.HomeCashFlow.services.expense.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/expense")
public class ExpenseController {

    @Autowired
    ExpenseService service;

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid ExpenseDTO expenseDTO) {
        Expense expenseModel = new Expense();
        BeanUtils.copyProperties(expenseDTO, expenseModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(expenseModel));
    }

    @GetMapping
    public ResponseEntity<List<Expense>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOne(@PathVariable(value = "id") UUID id) {

        Optional<Expense> expense = service.getOne(id);
        if (expense.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Expense not found.");

        return ResponseEntity.status(HttpStatus.OK).body(expense);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable(value = "id") UUID id,
                                         @RequestBody @Valid ExpenseDTO expenseDTO) {

        Optional<Expense> expense = service.getOne(id);
        if (expense.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Expense not found.");

        BeanUtils.copyProperties(expenseDTO, expense.get());
        return ResponseEntity.status(HttpStatus.OK).body(service.save(expense.get()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") UUID id) {

        Optional<Expense> expense = service.getOne(id);
        if (expense.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Expense not found.");

        service.delete(id);

        return ResponseEntity.status(HttpStatus.OK).body("Expense deleted with success");
    }

    @GetMapping("/report")
    public ResponseEntity<List<Object>> report(@RequestBody @Valid ReportDTO reportDTO) {

        if (reportDTO.category() != null) {
            List<Expense> report = service.getAll().stream()
                    .filter(expense -> expense.getCustomer().equals(reportDTO.user_id()))
                    .filter(expense -> expense.getTeam().equals(reportDTO.team_id()))
                    .filter(expense -> expense.getDate().isBefore(reportDTO.finishDate()))
                    .filter(expense -> expense.getDate().isAfter(reportDTO.starterDate()))
                    .filter(expense -> expense.getCategory().equals(reportDTO.category()))
                    .toList();

            return ResponseEntity.status(HttpStatus.OK).body(Collections.singletonList(report));
        }

        List<Expense> report = service.getAll().stream()
                .filter(expense -> expense.getCustomer().equals(reportDTO.user_id()))
                .filter(expense -> expense.getTeam().equals(reportDTO.team_id()))
                .filter(expense -> expense.getDate().isBefore(reportDTO.finishDate()))
                .filter(expense -> expense.getDate().isAfter(reportDTO.starterDate()))
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(Collections.singletonList(report));
    }
}


