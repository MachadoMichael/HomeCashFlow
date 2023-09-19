package com.machado.HomeCashFlow.dtos;

import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.UUID;

public record TeamDTO(
        @NotBlank String name,
        @NotBlank String ownerEmail
) {
}
