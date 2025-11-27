package com.comy_delivery_back.controller;

import com.comy_delivery_back.dto.request.ClienteRequestDTO;
import com.comy_delivery_back.dto.request.EntregadorRequestDTO;
import com.comy_delivery_back.dto.request.LoginRequestDTO;
import com.comy_delivery_back.dto.request.SignupRequestDTO;
import com.comy_delivery_back.dto.response.LoginResponseDTO;
import com.comy_delivery_back.dto.response.SignupResponseDTO;
import com.comy_delivery_back.service.AuthService;
import com.comy_delivery_back.service.ClienteService;
import com.comy_delivery_back.service.EntregadorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final EntregadorService entregadorService;
    private final ClienteService clienteService;
    private final ObjectMapper objectMapper;

    public AuthController(AuthService authService, EntregadorService entregadorService, ClienteService clienteService, ObjectMapper objectMapper) {
        this.authService = authService;
        this.entregadorService = entregadorService;
        this.clienteService = clienteService;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO){
        return ResponseEntity.ok(authService.login(loginRequestDTO)); //mudar depois
    }

    @PostMapping("register-complete")
    public ResponseEntity<?> completeRegistration(@RequestParam String role,
                                                  @RequestBody Map<String, Object> dadosRegistro){
        if (!role.equalsIgnoreCase("CLIENTE") && !role.equalsIgnoreCase("ENTREGADOR")){
            return ResponseEntity.badRequest().body("Role inválida. Escolha CLIENTE ou ENTREGADOR.");
        }

        if (role.equalsIgnoreCase("CLIENTE")){
            ClienteRequestDTO clienteRequestDTO = objectMapper.convertValue(dadosRegistro, ClienteRequestDTO.class)
        }
    }
}
