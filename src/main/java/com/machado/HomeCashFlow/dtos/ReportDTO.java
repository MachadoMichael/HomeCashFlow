package com.machado.HomeCashFlow.dtos;

import com.machado.HomeCashFlow.entities.ExpenseCategory;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record ReportDTO(
        UUID user_id,
        UUID team_id,
        ExpenseCategory category,
        @NotNull LocalDateTime starterDate,
        @NotNull LocalDateTime finishDate
) {
}
