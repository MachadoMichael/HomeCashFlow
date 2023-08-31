package com.machado.HomeCashFlow.dtos;

import com.machado.HomeCashFlow.entities.User;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record TeamDTO(
        @NotBlank String name,
        @NotBlank List<Integer> members
) {
}
