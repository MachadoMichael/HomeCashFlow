package com.machado.HomeCashFlow.controllers;

import com.machado.HomeCashFlow.dtos.ExpenseDTO;
import com.machado.HomeCashFlow.entities.Expense;
import com.machado.HomeCashFlow.entities.ExpenseCategory;
import com.machado.HomeCashFlow.repositories.ExpenseRepository;
import com.machado.HomeCashFlow.services.ExpenseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ExpenseControllerTest {

    @InjectMocks
    ExpenseController controller;

    @Mock
    private ExpenseService service;

    @Mock
    private ExpenseRepository repository;

    private Expense expense;
    private ExpenseDTO expenseDTO;

    @BeforeEach
    public void setup() {
        expenseDTO = new ExpenseDTO(
                "book",
                "ultraKnowledge",
                Instant.now(),
                2,
                1,
                ExpenseCategory.EDUCATION,
                45.00);
        expense = new Expense();
        BeanUtils.copyProperties(expenseDTO, expense);

    }

    @Test
    void saveExpenseAndReturnHttpCreated() {
        ResponseEntity<Object> response = assertDoesNotThrow(() -> controller.save(expenseDTO));
        assertEquals(ResponseEntity.status(HttpStatus.CREATED).body(service.save(expense)), response);
    }

    @Test
    void getAllExpenseAndResponseOkAndListIntoBody() {
        ResponseEntity<List<Expense>> expenseList = assertDoesNotThrow(() -> controller.getAll());
        assertEquals(ResponseEntity.status(HttpStatus.OK).body(service.getAll()), expenseList);
    }

    @Test
    void getExpenseByIdIfExpenseIdFounded() {

        UUID id = new UUID(122, 3);
        expense.setId(id);

        ResponseEntity<Object> responseGetById = controller.getOne(expense.getId());
        Optional<Expense> expenseByService = service.getOne(expense.getId());
        if (expenseByService.isEmpty()) {
            assertEquals(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Expense not found."), responseGetById);
        } else {
            assertEquals(ResponseEntity.status(HttpStatus.OK).body(expenseByService), responseGetById);
        }
    }

    @Test
    void updateExpenseById() {
        Expense newExpense = new Expense();
        BeanUtils.copyProperties(expense, newExpense);
        newExpense.setId(UUID.randomUUID());

        ResponseEntity<Object> responsePutById = controller.update(newExpense.getId(), expenseDTO);
        Optional<Expense> selectedExpense = service.getOne(newExpense.getId());
        if (selectedExpense.isEmpty())
            assertEquals(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Expense not found."), responsePutById);
        else {
            BeanUtils.copyProperties(expenseDTO, selectedExpense.get());
            repository.save(selectedExpense.get());
        }

    }

    @Test
    void deleteExpenseById() {
        Optional<Expense> responseGet = service.getOne(expense.getId());
        ResponseEntity<Object> responseDelete = controller.delete(expense.getId());

        if (responseGet.isEmpty())
            assertEquals(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Expense not found."), responseDelete);
        else
            assertEquals(ResponseEntity.status(HttpStatus.OK).body("Expense deleted with success"), responseDelete);
    }


}
