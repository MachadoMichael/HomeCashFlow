package com.machado.HomeCashFlow.services;

import com.machado.HomeCashFlow.dtos.ExpenseDTO;
import com.machado.HomeCashFlow.entities.Expense;
import com.machado.HomeCashFlow.entities.ExpenseCategory;
import com.machado.HomeCashFlow.repositories.ExpenseRepository;
import jdk.jshell.Snippet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;

import java.time.Instant;
import java.util.Date;
import java.lang.Enum.EnumDesc;

@ExtendWith(MockitoExtension.class)
public class ExpenseServiceTest {

    @InjectMocks
    ExpenseService expenseService;

    @Mock
    ExpenseRepository expenseRepository;

    Expense expense;

    @BeforeEach
    public void setUp() {
        expense = new Expense();
        ExpenseDTO expenseModel = new ExpenseDTO(
                "book",
                "ultraKnowledge",
                Instant.now(),
                "Michael",
                1,
                ExpenseCategory.EDUCATION,
                45.00);
        BeanUtils.copyProperties(expenseModel, expense);

    }
}
