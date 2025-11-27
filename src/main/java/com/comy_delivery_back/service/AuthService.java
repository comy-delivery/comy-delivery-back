package com.comy_delivery_back.service;

import com.comy_delivery_back.dto.request.LoginRequestDTO;
import com.comy_delivery_back.dto.response.LoginResponseDTO;
import com.comy_delivery_back.model.Usuario;
import com.comy_delivery_back.repository.UsuarioRepository;
import com.comy_delivery_back.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;


    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.username(), loginRequestDTO.password())
        );

        // recupera o objeto CustomUserDetails
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        //obtem objeto do modelo
        Usuario usuario = userDetails.getUsuario();

        String token = tokenService.generateToken(usuario);
        String refreshToken = tokenService.refreshToken(usuario);

        return new LoginResponseDTO(token, refreshToken, usuario.getId()); //ver depois

    }
}
