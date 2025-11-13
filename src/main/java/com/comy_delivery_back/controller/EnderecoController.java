package com.comy_delivery_back.controller;

import com.comy_delivery_back.client.ApiCepClient;
import com.comy_delivery_back.dto.response.ApiCepDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/endereco")
public class EnderecoController {

    private final ApiCepClient apiCepClient;

    public EnderecoController(ApiCepClient apiCepClient) {
        this.apiCepClient = apiCepClient;
    }

    @GetMapping("/{cep}")
    public ResponseEntity<ApiCepDTO> buscarPorCep(@PathVariable("cep") String cep) {
        return this.apiCepClient.buscarCep(cep);
    }
}
