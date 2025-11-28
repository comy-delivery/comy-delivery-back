package com.comy_delivery_back.security;

import com.comy_delivery_back.exception.UsuarioNaoRegistradoException;
import com.comy_delivery_back.model.Usuario;
import com.comy_delivery_back.service.TokenService;
import com.comy_delivery_back.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final TokenService tokenService;
    private final UsuarioService usuarioService;

    public OAuth2AuthenticationSuccessHandler(TokenService tokenService, UsuarioService usuarioService) {
        this.tokenService = tokenService;
        this.usuarioService = usuarioService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        String nome = oAuth2User.getAttribute("name");

        try {
            Usuario usuario = usuarioService.processOAuth2User(email, nome);
            String accessToken = tokenService.generateToken(usuario);
            String refreshToken = tokenService.refreshToken(usuario);

            // Redireciona pro Angular com os tokens
            String redirectUrl = String.format(
                    "http://localhost:4200/oauth2/callback?access_token=%s&refresh_token=%s&user_id=%s",
                    accessToken,
                    refreshToken,
                    usuario.getId()
            );

            response.sendRedirect(redirectUrl);

        } catch (UsuarioNaoRegistradoException e) {
            // Se não tiver cadastro, manda pra cadastro
            response.sendRedirect("http://localhost:4200/cadastro?email=" + email + "&nome=" + nome);
        }
    }
}