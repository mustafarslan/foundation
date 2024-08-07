package com.foundation.core.controller;

import com.foundation.core.dto.GreetingResponseDto;
import com.foundation.core.service.GreetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Tag(name = "Greetings", description = "Sample API for Foundation Core")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/")
@Profile("dev")
public class GreetingsController {

    private final GreetService greetService;

    @Operation(summary = "Greeting - Normal", description = "just doing what needs to be done")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully completed mandatory task"),
            @ApiResponse(responseCode = "404", description = "I had one job but couldn't find it.")
    })
    @GetMapping(value = "v1/greetings")
    @ResponseStatus(HttpStatus.OK)
    public GreetingResponseDto greetings() {
        return greetService.greet();
    }

    @Operation(summary = "Greeting - Async", description = "just doing what needs to be done")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully completed mandatory task"),
            @ApiResponse(responseCode = "404", description = "I had one job but couldn't find it.")
    })
    @GetMapping(value = "v1/greetingsAsync")
    @ResponseStatus(HttpStatus.OK)
    public CompletableFuture<GreetingResponseDto> greetingsAsync() throws ExecutionException, InterruptedException {
        return greetService.greetAsync();
    }

}
