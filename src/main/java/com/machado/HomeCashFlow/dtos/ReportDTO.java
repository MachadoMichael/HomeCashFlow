package com.machado.HomeCashFlow.dtos;

import com.machado.HomeCashFlow.entities.ExpenseCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record ReportDTO(
        UUID user_id,
        UUID team_id,
        ExpenseCategory category,
        @NotNull LocalDate starterDate,
        @NotNull LocalDate finishDate
) {
}
