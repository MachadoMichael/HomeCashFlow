package com.machado.HomeCashFlow.dtos;

import com.machado.HomeCashFlow.entities.ExpenseCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.Instant;

public record ExpenseDTO(
        @NotBlank String name,
        String note,
        @NotNull Instant date,
        @NotBlank String customer,
        @NotBlank String team,
        ExpenseCategory category,
        @NotNull BigDecimal value) {
}