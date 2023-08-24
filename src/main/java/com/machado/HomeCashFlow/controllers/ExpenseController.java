package com.machado.HomeCashFlow.controllers;

import com.machado.HomeCashFlow.repositories.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExpenseController {

    @Autowired
    ExpenseRepository expenseRepository;


}
