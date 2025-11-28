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
    public AuthenticationSuccessHandler oauth2AuthenticationSucessHandler() {
        return ((request, response, authentication) -> {

            //pega informacoes do usuario google
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
            String email = oAuth2User.getAttribute("email");
            String nome = oAuth2User.getAttribute("name");

            try {
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
    public GrantedAuthoritiesMapper grantedAuthoritiesMapper() {
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
    ) {
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
                                                   AuthenticationSuccessHandler oauth2AuthenticationSuccessHandler) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sessionConfig ->
                        sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2Login(oauth2 -> oauth2
                        .successHandler(oauth2AuthenticationSuccessHandler)
                        .authorizationEndpoint(endpoint -> endpoint.baseUri("/oauth2/authorization"))
                        .redirectionEndpoint(redirection -> redirection.baseUri("/oauth2/callback/*"))
                )
                .authorizeHttpRequests(auth -> auth
                        //GERAIS
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/swagger-ui.html").permitAll()

                        //Health
                        .requestMatchers(HttpMethod.GET, "/api/health").permitAll()

                        //Auth
                        .requestMatchers("/api/auth/**").permitAll()
                        //Recuperação
                        .requestMatchers("/api/*/recuperacao/**").permitAll() // Rotas de recuperação
                        .requestMatchers(HttpMethod.POST, "/**/recuperar-senha", "/**/redefinir-senha").permitAll()

                        //PRA FACILITAR INTEGRAÇÃO
                        .requestMatchers("/api/**").permitAll()

//                        //Adicional
//                        .requestMatchers(HttpMethod.POST, "/api/adicional").hasRole("RESTAURANTE")
//                        .requestMatchers(HttpMethod.GET, "/api/adicional/{id}").hasAnyRole("RESTAURANTE","CLIENTE")
//                        .requestMatchers(HttpMethod.GET, "/api/adicional/produto/{produtoId}").hasAnyRole("RESTAURANTE","CLIENTE")
//                        .requestMatchers(HttpMethod.GET, "/api/adicional/disponiveis").hasAnyRole("RESTAURANTE","CLIENTE")
//                        .requestMatchers(HttpMethod.PUT, "/api/adicional/{id}").hasRole("RESTAURANTE")
//                        .requestMatchers(HttpMethod.PATCH, "/api/adicional/{id}/ativar").hasRole("RESTAURANTE")
//                        .requestMatchers(HttpMethod.PATCH, "/api/adicional/{id}/desativar").hasRole("RESTAURANTE")
//                        .requestMatchers(HttpMethod.DELETE, "/api/adicional/{id}").hasRole("RESTAURANTE")
//
//                        //Admin
//                        .requestMatchers("/api/admin").hasRole("ADMIN")
//
//                        //Avaliacao
//                        .requestMatchers(HttpMethod.POST, "/api/avaliacao").hasRole("CLIENTE")
//                        .requestMatchers(HttpMethod.GET, "/api/avaliacao/restaurante/{restauranteId}").permitAll()
//                        .requestMatchers(HttpMethod.DELETE, "/api/avaliacao/{id}").hasRole("CLIENTE")
//
//                        //Cliente
//                        .requestMatchers(HttpMethod.POST, "/api/cliente").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/cliente/{idCliente}").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/cliente/ativos").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.PUT, "/api/cliente/{idCliente}").hasRole("CLIENTE")
//                        .requestMatchers(HttpMethod.DELETE, "/api/cliente/{idCliente}").hasRole("CLIENTE")
//                        .requestMatchers(HttpMethod.GET, "/api/cliente/{idCliente}/enderecos").hasRole("CLIENTE")
//                        .requestMatchers(HttpMethod.POST, "/api/cliente/{idCliente}/enderecos").hasRole("CLIENTE")
//                        .requestMatchers(HttpMethod.PUT, "/api/cliente/{idCliente}/enderecos/{idEndereco}").hasRole("CLIENTE")
//                        .requestMatchers(HttpMethod.GET, "/api/cliente/{idCliente}/pedidos").hasRole("CLIENTE")
//                        .requestMatchers(HttpMethod.GET, "/api/cliente/{idCliente}/restaurantes-distancia").permitAll()
//
//                        //Cupom
//                        .requestMatchers(HttpMethod.POST, "/api/cupom").hasRole("RESTAURANTE")
//                        .requestMatchers(HttpMethod.GET, "/api/cupom/{id}").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/cupom/codigo/{codigo}").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/cupom").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/cupom/restaurante/{restauranteId}").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/api/cupom/{codigo}/validar").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/cupom/{id}/verificar-validade").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/api/cupom/{id}/aplicar-desconto").permitAll()
//                        .requestMatchers(HttpMethod.PATCH, "/api/cupom/{id}/incrementar-uso").permitAll()
//                        .requestMatchers(HttpMethod.PUT, "/api/cupom/{id}").hasRole("RESTAURANTE")
//                        .requestMatchers(HttpMethod.PATCH, "/api/cupom/{id}/desativar").hasRole("RESTAURANTE")
//                        .requestMatchers(HttpMethod.PATCH, "/api/cupom/{id}/ativar").hasRole("RESTAURANTE")
//                        .requestMatchers(HttpMethod.DELETE, "/api/cupom/{id}").hasRole("RESTAURANTE")
//
//                        //Endereço
//                        .requestMatchers(HttpMethod.GET, "/api/endereco/{cep}").permitAll()
//                        .requestMatchers(HttpMethod.DELETE, "/api/endereco/{id}").hasAnyRole("RESTAURANTE","CLIENTE")
//                        .requestMatchers(HttpMethod.POST, "/api/endereco").hasAnyRole("RESTAURANTE","CLIENTE")
//                        .requestMatchers(HttpMethod.GET, "/api/endereco/buscar/{id}").permitAll()
//                        .requestMatchers(HttpMethod.PATCH, "/api/endereco/alterar/{id}").hasAnyRole("RESTAURANTE","CLIENTE")
//                        .requestMatchers(HttpMethod.PATCH, "/api/endereco/{id}/padrao").hasAnyRole("RESTAURANTE","CLIENTE")
//                        .requestMatchers(HttpMethod.PATCH, "/api/endereco/{idEndereco}/vincular").hasAnyRole("RESTAURANTE","CLIENTE")
//
//                        //Entrega
//                        .requestMatchers(HttpMethod.POST, "/api/entrega").permitAll()
//                        .requestMatchers(HttpMethod.PATCH, "/api/entrega/{id}").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/entrega/{id}").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/entrega/pedido/{idPedido}").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/entrega/status").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/entrega/entregador/{idEntregador}").hasRole("ENTREGADOR")
//                        .requestMatchers(HttpMethod.GET, "/api/entrega/entregador/{idEntregador}/status").hasRole("ENTREGADOR")
//                        .requestMatchers(HttpMethod.PATCH, "/api/entrega/{idEntrega}/avaliacao/{idAvaliacao}").hasRole("ENTREGADOR")
//                        .requestMatchers(HttpMethod.GET, "/api/entrega/entregador/{id}/dashboard").hasRole("ENTREGADOR")
//
//                        //Entregador
//                        .requestMatchers(HttpMethod.POST, "/api/entregador").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/entregador/{id}").hasRole("ENTREGADOR")
//                        .requestMatchers(HttpMethod.GET, "/api/entregador/disponiveis").permitAll()
//                        .requestMatchers(HttpMethod.PUT, "/api/entregador/{id}").hasRole("ENTREGADOR")
//                        .requestMatchers(HttpMethod.DELETE, "/api/entregador/{id}").hasRole("ENTREGADOR")
//                        .requestMatchers(HttpMethod.PATCH, "/api/entregador/{id}/disponivel").hasRole("ENTREGADOR")
//                        .requestMatchers(HttpMethod.PATCH, "/api/entregador/{id}/indisponivel").hasRole("ENTREGADOR")
//                        .requestMatchers(HttpMethod.PATCH, "/api/entregador/{idEntregador}/atribuir/{idEntrega}").hasRole("ENTREGADOR")
//                        .requestMatchers(HttpMethod.PATCH, "/api/entregador/entrega/{idEntrega}/iniciar").hasRole("ENTREGADOR")
//                        .requestMatchers(HttpMethod.PATCH, "/api/entregador/entrega/{idEntrega}/finalizar").hasRole("ENTREGADOR")
//                        .requestMatchers(HttpMethod.PATCH, "/api/entregador/entrega/{idEntrega}/cancelar").hasRole("ENTREGADOR")
//
//                        //ItemPedido
//                        .requestMatchers(HttpMethod.POST, "/api/item").hasAnyRole("RESTAURANTE","CLIENTE")
//                        .requestMatchers(HttpMethod.GET, "/api/item/{id}").hasAnyRole("RESTAURANTE","CLIENTE")
//                        .requestMatchers(HttpMethod.GET, "/api/item/pedido/{pedidoId}").hasAnyRole("RESTAURANTE","CLIENTE")
//                        .requestMatchers(HttpMethod.PATCH, "/api/item/{id}/quantidade").hasRole("CLIENTE")
//                        .requestMatchers(HttpMethod.PATCH, "/api/item/{id}/observacao").hasRole("CLIENTE")
//                        .requestMatchers(HttpMethod.PATCH, "/api/item/{id}/adicionais").hasRole("CLIENTE")
//                        .requestMatchers(HttpMethod.DELETE, "/api/item/{id}/adicionais").hasRole("CLIENTE")
//                        .requestMatchers(HttpMethod.GET, "/api/item/{id}/subtotal").hasRole("RESTAURANTE")
//                        .requestMatchers(HttpMethod.DELETE, "/api/item/{id}").hasAnyRole("RESTAURANTE","CLIENTE")
//                        .requestMatchers(HttpMethod.POST, "/api/item/{id}/duplicar").hasRole("CLIENTE")
//
//                        //Pedido
//                        .requestMatchers(HttpMethod.POST, "/api/pedido").hasRole("CLIENTE")
//                        .requestMatchers(HttpMethod.PATCH, "/api/pedido/{id}/aceitar").hasRole("RESTAURANTE")
//                        .requestMatchers(HttpMethod.PATCH, "/api/pedido/{id}/recusar").hasRole("RESTAURANTE")
//                        .requestMatchers(HttpMethod.GET, "/api/pedido/{id}").hasAnyRole("RESTAURANTE","CLIENTE")
//                        .requestMatchers(HttpMethod.GET, "/api/pedido/cliente/{clienteId}").hasRole("CLIENTE")
//                        .requestMatchers(HttpMethod.GET, "/api/pedido/restaurante/{restauranteId}").hasRole("RESTAURANTE")
//                        .requestMatchers(HttpMethod.GET, "/api/pedido/restaurante/{restauranteId}/pendentes").hasRole("RESTAURANTE")
//                        .requestMatchers(HttpMethod.GET, "/api/pedido/restaurante/{restauranteId}/aceitos").hasRole("RESTAURANTE")
//                        .requestMatchers(HttpMethod.GET, "/api/pedido/restaurante/{restauranteId}/recusados").hasRole("RESTAURANTE")
//                        .requestMatchers(HttpMethod.PATCH, "/api/pedido/{id}/status").hasRole("RESTAURANTE")
//                        .requestMatchers(HttpMethod.DELETE, "/api/pedido/{id}").hasAnyRole("RESTAURANTE","CLIENTE")
//                        .requestMatchers(HttpMethod.GET, "/api/pedido/{id}/subtotal").hasRole("RESTAURANTE")
//                        .requestMatchers(HttpMethod.GET, "/api/pedido/{id}/total").hasRole("RESTAURANTE")
//                        .requestMatchers(HttpMethod.GET, "/api/pedido/{id}/valor-entrega").hasRole("RESTAURANTE")
//                        .requestMatchers(HttpMethod.GET, "/api/pedido/restaurante/{restauranteId}/dashboard").hasRole("RESTAURANTE")
//                        .requestMatchers(HttpMethod.GET, "/api/pedido/{id}/tempo-estimado").permitAll()
//                        .requestMatchers(HttpMethod.PATCH, "/api/pedido/{id}/finalizar").permitAll()
//                        .requestMatchers(HttpMethod.PATCH, "/api/pedido/{id}/cupom").hasAnyRole("RESTAURANTE","CLIENTE")
//                        .requestMatchers(HttpMethod.DELETE, "/api/pedido/{id}/cupom").hasAnyRole("RESTAURANTE","CLIENTE")
//                        .requestMatchers(HttpMethod.GET, "/api/pedido/{id}/desconto").permitAll()
//
//                        //Produto
//                        .requestMatchers(HttpMethod.POST, "/api/produto").hasRole("RESTAURANTE")
//                        .requestMatchers(HttpMethod.GET, "/api/produto/{id}").hasRole("RESTAURANTE")
//                        .requestMatchers(HttpMethod.GET, "/api/produto/restaurante/{restauranteId}").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/produto/promocoes").permitAll()
//                        .requestMatchers(HttpMethod.PUT, "/api/produto/{id}").hasRole("RESTAURANTE")
//                        .requestMatchers(HttpMethod.DELETE, "/api/produto/{id}").hasRole("RESTAURANTE")
//                        .requestMatchers(HttpMethod.PUT, "/api/produto/{id}/imagem").hasRole("RESTAURANTE")
//                        .requestMatchers(HttpMethod.GET, "/api/produto/{id}/imagem").permitAll()
//                        .requestMatchers(HttpMethod.PATCH, "/api/produto/{id}/promocao").hasRole("RESTAURANTE")
//                        .requestMatchers(HttpMethod.GET, "/api/produto/restaurante/{restauranteId}/categorias").permitAll()
//
//                        //Restaurante
//                        .requestMatchers(HttpMethod.POST, "/api/restaurante").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/restaurante/{id}").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/restaurante/cnpj/{cnpj}").permitAll()
//                        .requestMatchers(HttpMethod.PUT, "/api/restaurante/{id}").hasRole("RESTAURANTE")
//                        .requestMatchers(HttpMethod.DELETE, "/api/restaurante/{id}").hasAnyRole("RESTAURANTE","ADMIN")
//                        .requestMatchers(HttpMethod.PUT, "/api/restaurante/{idRestaurante}/enderecos/{idEndereco}").hasRole("RESTAURANTE")
//                        .requestMatchers(HttpMethod.GET, "/api/restaurante/abertos").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/restaurante/{idRestaurante}/enderecos").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/restaurante/{idRestaurante}/produtos").permitAll()
//                        .requestMatchers(HttpMethod.PATCH, "/api/restaurante/{id}/status/abrir").permitAll()
//                        .requestMatchers(HttpMethod.PATCH, "/api/restaurante/{id}/status/fechar").permitAll()
//                        .requestMatchers(HttpMethod.PATCH, "/api/restaurante/{id}/status/disponibilizar").hasAnyRole("RESTAURANTE","ADMIN")
//                        .requestMatchers(HttpMethod.PATCH, "/api/restaurante/{id}/status/indisponibilizar").hasAnyRole("RESTAURANTE","ADMIN")
//                        .requestMatchers(HttpMethod.PUT, "/api/restaurante/{id}/logo").hasRole("RESTAURANTE")
//                        .requestMatchers(HttpMethod.GET, "/api/restaurante/{id}/logo").permitAll()
//                        .requestMatchers(HttpMethod.PUT, "/api/restaurante/{id}/banner").hasRole("RESTAURANTE")
//                        .requestMatchers(HttpMethod.GET, "/api/restaurante/{id}/banner").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/api/restaurante/{id}/enderecos").hasRole("RESTAURANTE")
//                        .requestMatchers(HttpMethod.PATCH, "/api/restaurante/{id}/calcular-tempo-medio").permitAll()

                        .anyRequest().authenticated())
                .authenticationProvider(daoAuthenticationProvider)
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
}
