package com.machado.HomeCashFlow.services;

import com.machado.HomeCashFlow.entities.Expense;
import com.machado.HomeCashFlow.repositories.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ExpenseService {

    @Autowired
    ExpenseRepository expenseRepository;

    public Expense save(Expense expense) {
        return expenseRepository.save(expense);
    }

    public List<Expense> getAll() {
        return expenseRepository.findAll();
    }

    public Optional<Expense> getOne(UUID expense_id) {
        return expenseRepository.findById(expense_id);
    }

    public void delete(Expense expense) {
        expenseRepository.delete(expense);
    }
}
