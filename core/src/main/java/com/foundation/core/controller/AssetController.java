package com.foundation.core.controller;

import com.foundation.core.entity.Asset;
import com.foundation.core.entity.Money;
import com.foundation.core.service.AssetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Tag(name = "Assets", description = "List all assets for a given customer.")
@RestController
@RequestMapping("/api/assets/")
public class AssetController {

    @Autowired
    private AssetService assetService;

    @Operation(summary = "List Assets", description = "List all assets for a given customer.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully completed"),
            @ApiResponse(responseCode = "404", description = "Not found. Check request or API definition.")
    })
    @GetMapping("v1/list")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Asset>> listOrders(@RequestParam Long customerId, Principal principal) {
        return ResponseEntity.ok(assetService.listAssets(customerId, principal));
    }

    @Operation(summary = "Deposit Money", description = "Deposit TRY for a given customer and amount")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully completed"),
            @ApiResponse(responseCode = "404", description = "Not found. Check request or API definition.")
    })
    @PostMapping("v1/deposit")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Integer> deposit(@RequestBody Money money, Principal principal) {
        return ResponseEntity.ok(assetService.depositMoney(money, principal));
    }

    @Operation(summary = "Withdraw Money", description = "Withdraw TRY for a given customer, amount and IBAN")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully completed"),
            @ApiResponse(responseCode = "404", description = "Not found. Check request or API definition.")
    })
    @PostMapping("v1/withdraw")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Integer> withdraw(@RequestBody Money money, Principal principal) {
        return ResponseEntity.ok(assetService.withdrawMoney(money, principal));
    }
}
