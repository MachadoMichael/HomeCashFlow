package com.machado.HomeCashFlow.dtos;

import jakarta.validation.constraints.NotBlank;

public record UserDTO(
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotBlank String userName,
        @NotBlank String email,
        @NotBlank String password
) {
}
