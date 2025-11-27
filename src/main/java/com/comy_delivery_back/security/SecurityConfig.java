package com.comy_delivery_back.security;

import com.comy_delivery_back.dto.response.LoginResponseDTO;
import com.comy_delivery_back.exception.UsuarioNaoRegistradoException;
import com.comy_delivery_back.model.Usuario;
import com.comy_delivery_back.service.TokenService;
import com.comy_delivery_back.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtTokenFilter jwtTokenFilter; //valida jwt
    private final TokenService tokenService;
    private final UsuarioService usuarioService;
    private final ObjectMapper objectMapper;


    public SecurityConfig(JwtTokenFilter jwtTokenFilter, TokenService tokenService, UsuarioService usuarioService, ObjectMapper objectMapper) {
        this.jwtTokenFilter = jwtTokenFilter;
        this.tokenService = tokenService;
        this.usuarioService = usuarioService;
        this.objectMapper = objectMapper;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler oauth2AuthenticationSucessHandler(){
        return ((request, response, authentication) -> {

            //pega informacoes do usuario google
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
            String email = oAuth2User.getAttribute("email");
            String nome = oAuth2User.getAttribute("name");

            try{
                //pega usuario existent
                Usuario usuario = usuarioService.processOAuth2User(email, nome);
                String token = tokenService.generateToken(usuario);
                String refreshToken = tokenService.refreshToken(usuario);

                LoginResponseDTO loginResponseDTO = new LoginResponseDTO(token, refreshToken, usuario.getId());

                response.setContentType("application/json;charset=UTF-8"); //tipo
                response.setStatus(HttpServletResponse.SC_OK); //resposta

                //converte o dto para json string
                String jsonResponse = objectMapper.writeValueAsString(loginResponseDTO);

                //escreve string
                response.getWriter().write(jsonResponse);

            } catch (UsuarioNaoRegistradoException e) {
                response.setContentType("application/json;charset=UTF-8");
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);

                //criando a resposta
                String jsonError = objectMapper.writeValueAsString(Map.of(
                        "status", "REGISTRO_PENDENTE",
                        "message", "Usuário não encontrado. Complete o cadastro com este e-mail.",
                        "email", email,
                        "nome", nome
                ));

                //escreve a string
                response.getWriter().write(jsonError);
            }

            //finaliza requisicao
            response.getWriter().flush();
        });
    }

    //correcao do problema de prefixamento de roles
    @Bean
    public GrantedAuthoritiesMapper grantedAuthoritiesMapper(){
        SimpleAuthorityMapper authorityMapper = new SimpleAuthorityMapper();
        authorityMapper.setPrefix(""); //nao adiciona prefixo nas authorities que ja enviei

        return authorityMapper;
    }

    //validação inicial das credenciais
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(
            UserDetailsService userDetailsService, //busca usuario no banco
            PasswordEncoder passwordEncoder,
            GrantedAuthoritiesMapper grantedAuthoritiesMapper //mapeia roles
    ){
        //"mostra" como validar as credenciais
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        authenticationProvider.setAuthoritiesMapper(grantedAuthoritiesMapper);

        return authenticationProvider;
    }

    //ponto de entrada p iniciar login
    //controller de Login chama o AuthenticationManager e ele chama o DaoAuthenticationProvider
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
                                                   DaoAuthenticationProvider daoAuthenticationProvider,
                                                   AuthenticationSuccessHandler oauth2AuthenticationSuccessHandler) throws Exception{
        httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sessionConfig->
                        sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2Login(oauth2 -> oauth2
                        .successHandler(oauth2AuthenticationSuccessHandler)
                        .authorizationEndpoint(endpoint -> endpoint.baseUri("/oauth2/authorization"))
                        .redirectionEndpoint(redirection -> redirection.baseUri("/oauth2/callback/*"))
                )
                .authorizeHttpRequests(auth -> auth
                        //GERAIS
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/admin").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/swagger-ui.html").permitAll()


                        .requestMatchers(HttpMethod.POST, "/api/cliente", "/api/entregador", "/api/restaurante").permitAll()
                        .requestMatchers(HttpMethod.POST, "/**/recuperar-senha", "/**/redefinir-senha").permitAll()
                        .requestMatchers("/api/*/recuperacao/**").permitAll() // Rotas de recuperação
                        .requestMatchers(HttpMethod.GET, "/api/restaurante/abertos", "/api/restaurante/{id}", "/api/restaurante/{idRestaurante}/produtos").permitAll()

                        .requestMatchers("api/admin/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/cliente/ativos").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/cliente/{idCliente}").hasRole("ADMIN")

                        .requestMatchers("/api/entregador/**").hasRole("ENTREGADOR")
                        .requestMatchers("/api/restaurante/**").hasRole("RESTAURANTE")

                        .requestMatchers("/api/cliente/**").hasRole("CLIENTE")
                        .anyRequest().authenticated())
                .authenticationProvider(daoAuthenticationProvider)
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
}
