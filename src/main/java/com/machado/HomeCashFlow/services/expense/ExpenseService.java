package com.machado.HomeCashFlow.services.expense;

import com.machado.HomeCashFlow.entities.Expense;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ExpenseService {


    Expense save(Expense expense);

     List<Expense> getAll();

     Optional<Expense> getOne(UUID id);

     void delete(UUID id);
}
