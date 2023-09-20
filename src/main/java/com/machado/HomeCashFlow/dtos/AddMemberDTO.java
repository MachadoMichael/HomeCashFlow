package com.machado.HomeCashFlow.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AddMemberDTO(
        @NotBlank String userEmail,
        @NotNull UUID teamId
) {
}
