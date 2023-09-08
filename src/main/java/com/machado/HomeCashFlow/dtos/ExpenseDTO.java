package com.machado.HomeCashFlow.dtos;

import com.machado.HomeCashFlow.entities.ExpenseCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record ExpenseDTO(
        @NotBlank String name,
        String note,
        @NotNull LocalDate date,
        @NotNull UUID customer,
        @NotNull Integer team,
        ExpenseCategory category,
        @NotNull Double value) {
}