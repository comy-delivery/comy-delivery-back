package com.comy_delivery_back.client;

import com.comy_delivery_back.configuration.FeignConfig;
import com.comy_delivery_back.dto.response.ApiCepDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "api-cep-client", url = "https://cep.awesomeapi.com.br/json", configuration = FeignConfig.class)
public interface ApiCepClient {

    @GetMapping("/{cep}")
    ResponseEntity<ApiCepDTO> buscarCep( @PathVariable("cep") String cep);
}
