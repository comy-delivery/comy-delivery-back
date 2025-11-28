package com.comy_delivery_back.service;

import com.comy_delivery_back.exception.UsuarioNaoRegistradoException;
import com.comy_delivery_back.model.Admin;
import com.comy_delivery_back.model.Cliente;
import com.comy_delivery_back.model.Entregador;
import com.comy_delivery_back.model.Usuario;
import com.comy_delivery_back.repository.AdminRepository;
import com.comy_delivery_back.repository.ClienteRepository;
import com.comy_delivery_back.repository.EntregadorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

//apenas p/ utilizacao do oauth2
@Service
@Slf4j
@RequiredArgsConstructor
public class UsuarioService {
    private final ClienteRepository clienteRepository;
    private final EntregadorRepository entregadorRepository;
    private final AdminRepository adminRepository;

    public Usuario processOAuth2User (String email, String nome){
        Optional<Cliente> clienteExistente = clienteRepository.findByEmailCliente(email);
        if (clienteExistente.isPresent()) {
            return clienteExistente.get();
        }

        Optional<Entregador> entregadorExistente = entregadorRepository.findByEmailEntregador(email);
        if (entregadorExistente.isPresent()){
            return entregadorExistente.get();
        }

        Optional<Admin> adminExistente = adminRepository.findByEmailAdmin(email);
        if (adminExistente.isPresent()){
            return adminExistente.get();
        }

        log.warn("Login Social: E-mail '{}' não registrado. Necessário cadastro completo.", email);

        throw new UsuarioNaoRegistradoException(email);
    }

}
