package com.foundation.core.controller;

import com.foundation.core.dto.GreetingResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Greetings", description = "Sample API for Foundation Core")
@RestController
@RequestMapping("/api/v1/greetings")
@Profile("dev")
public class GreetingsController {

    @Operation(summary = "Mandatory", description = "just doing what needs to be done")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully completed mandatory task"),
            @ApiResponse(responseCode = "404", description = "I had one job but couldn't find it.")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public GreetingResponseDto greetings() {
        return GreetingResponseDto.builder().message("Hello World").build();
    }

}
