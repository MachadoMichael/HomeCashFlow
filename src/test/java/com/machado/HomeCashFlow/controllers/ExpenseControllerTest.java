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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

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
                "Michael",
                1,
                ExpenseCategory.EDUCATION,
                45.00);
        expense = new Expense();
        BeanUtils.copyProperties(expenseDTO, expense);
    }

    @Test
    public void acceptRequestAndCallServiceToSave() {
        ResponseEntity<Object> response = assertDoesNotThrow(() -> controller.save(expenseDTO));
        assertEquals(ResponseEntity.status(HttpStatus.CREATED).body(service.save(expense)), response);
    }
}
