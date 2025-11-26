package com.comy_delivery_back.controller;

import com.comy_delivery_back.dto.request.LoginRequestDTO;
import com.comy_delivery_back.dto.request.SignupRequestDTO;
import com.comy_delivery_back.dto.response.LoginResponseDTO;
import com.comy_delivery_back.dto.response.SignupResponseDTO;
import com.comy_delivery_back.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticação", description = "Endpoint para login na api")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO){
        return ResponseEntity.ok(authService.login(loginRequestDTO)); //mudar depois
    }
}
