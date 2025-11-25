package com.comy_delivery_back.service;

import com.comy_delivery_back.dto.request.LoginRequestDTO;
import com.comy_delivery_back.dto.response.LoginResponseDTO;
import com.comy_delivery_back.dto.response.SignupResponseDTO;
import com.comy_delivery_back.model.Usuario;
import com.comy_delivery_back.repository.UsuarioRepository;
import com.comy_delivery_back.security.CustomUserDetails;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(AuthenticationManager authenticationManager, TokenService tokenService, UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.username(), loginRequestDTO.password())
        );

        // recupera o objeto CustomUserDetails
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        //obtem objeto do modelo
        Usuario usuario = userDetails.getUsuario();


        String token = tokenService.generateToken(usuario);

        return new LoginResponseDTO(token, usuario.getId()); //ver depois

    }

    public SignupResponseDTO signup(LoginRequestDTO signupRequestDto) {
        Usuario usuario = usuarioRepository.findByUsername(signupRequestDto.username()).orElse(null);

        if (usuario != null) throw new IllegalArgumentException("usuario já existe");

        //usuario = usuarioRepository.save(Usuario.builder)
    }
}
