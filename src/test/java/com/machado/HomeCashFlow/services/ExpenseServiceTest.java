package com.machado.HomeCashFlow.services;

import com.machado.HomeCashFlow.dtos.ExpenseDTO;
import com.machado.HomeCashFlow.entities.Expense;
import com.machado.HomeCashFlow.entities.ExpenseCategory;
import com.machado.HomeCashFlow.repositories.ExpenseRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.machado.HomeCashFlow.services.expense.ExpenseServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class ExpenseServiceTest {

    @InjectMocks
    ExpenseServiceImp service;

    @Mock
    ExpenseRepository repository;

    Expense expense;

    @BeforeEach
    public void setUp() {
        expense = new Expense();
        ExpenseDTO expenseDTO = new ExpenseDTO(
                "book",
                "ultraKnowledge",
                Instant.now(),
                2,
                1,
                ExpenseCategory.EDUCATION,
                45.00);
        BeanUtils.copyProperties(expenseDTO, expense);
    }

    @Test
    void getAllExpenses() {
        Mockito.when(repository.findAll()).thenReturn(Collections.singletonList(expense));
        List<Expense> expenseList = service.getAll();

        assertEquals(Collections.singletonList(expense), expenseList);
        Mockito.verify(repository).findAll();
        Mockito.verifyNoMoreInteractions(repository);

    }

    @Test
    void saveNewExpense() {
        Expense newExpense = new Expense();
        BeanUtils.copyProperties(expense, newExpense);
        newExpense.setId(UUID.randomUUID());

        Mockito.when(service.save(expense)).thenReturn(newExpense);
        assertEquals(newExpense, service.save(expense));
    }

    @Test
    void getOneExpenseById() {
        expense.setId(UUID.randomUUID());
        Mockito.when(service.getOne(expense.getId())).thenReturn(Optional.ofNullable(expense));

        assertEquals(service.getOne(expense.getId()), Optional.ofNullable(expense));
    }
}
