package com.comy_delivery_back.controller;

import com.comy_delivery_back.dto.request.ClienteRequestDTO;
import com.comy_delivery_back.dto.request.EntregadorRequestDTO;
import com.comy_delivery_back.dto.request.LoginRequestDTO;
import com.comy_delivery_back.dto.request.SignupRequestDTO;
import com.comy_delivery_back.dto.response.ClienteResponseDTO;
import com.comy_delivery_back.dto.response.EntregadorResponseDTO;
import com.comy_delivery_back.dto.response.LoginResponseDTO;
import com.comy_delivery_back.dto.response.SignupResponseDTO;
import com.comy_delivery_back.model.Cliente;
import com.comy_delivery_back.service.AuthService;
import com.comy_delivery_back.service.ClienteService;
import com.comy_delivery_back.service.EntregadorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/register-complete")
    public ResponseEntity<?> completeRegistration(@RequestParam String role, //?role=CLIENTE ou ?role=ENTREGADOR
                                                  @RequestBody Map<String, Object> dadosRegistro){
        if (!role.equalsIgnoreCase("CLIENTE") && !role.equalsIgnoreCase("ENTREGADOR")){
            return ResponseEntity.badRequest().body("Role inválida. Escolha CLIENTE ou ENTREGADOR.");
        }
        try{
            if (role.equalsIgnoreCase("CLIENTE")){
                ClienteRequestDTO clienteRequestDTO = objectMapper.convertValue(dadosRegistro, ClienteRequestDTO.class);

                ClienteResponseDTO novoCliente = clienteService.cadastrarCliente(clienteRequestDTO);

                return ResponseEntity.status(HttpStatus.CREATED).body(novoCliente);

            }else if (role.equalsIgnoreCase("ENTREGADOR")){
                EntregadorRequestDTO entregadorRequestDTO = objectMapper.convertValue(dadosRegistro, EntregadorRequestDTO.class);

                EntregadorResponseDTO entregadorResponseDTO = entregadorService.cadastrarEntregador(entregadorRequestDTO);

                return ResponseEntity.status(HttpStatus.CREATED).body(entregadorResponseDTO);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro de cadastro: " + e.getMessage()); //erro de cadastro ex: cpf existe
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro interno no processamento do cadastro: " + e.getMessage()); //erro de servidor

        }
        return ResponseEntity.internalServerError().body("Erro genérico");
    }
}
