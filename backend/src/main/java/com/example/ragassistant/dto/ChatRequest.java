package com.example.ragassistant.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ChatRequest(
        @NotNull Long documentId,
        @NotBlank String question
) {
}
