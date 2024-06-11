package com.foundation.core.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GreetingResponseDto {
    @NotNull
    @NotEmpty
    private String message;
}
