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
    ExpenseRepository repository;

    public Expense save(Expense expense) {
        return repository.save(expense);
    }

    public List<Expense> getAll() {
        return repository.findAll();
    }

    public Optional<Expense> getOne(UUID id) {
        return repository.findById(id);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
