package com.comy_delivery_back.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/health")
@Tag(name = "Health Check", description = "Endpoint para verificação de saúde da aplicação")
public class HealthController {

    @GetMapping
    @Operation(summary = "Verificar status da aplicação",
            description = "Retorna informações sobre o status e versão da aplicação")
    @ApiResponse(responseCode = "200", description = "Aplicação funcionando normalmente")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("timestamp", LocalDateTime.now());
        response.put("application", "Comy Delivery API");
        response.put("version", "1.0.0");
        return ResponseEntity.ok(response);
    }


}
