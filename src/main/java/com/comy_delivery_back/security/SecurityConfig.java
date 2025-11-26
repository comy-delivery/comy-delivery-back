package com.comy_delivery_back.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtTokenFilter jwtTokenFilter; //valida jwt

    public SecurityConfig(JwtTokenFilter jwtTokenFilter) {
        this.jwtTokenFilter = jwtTokenFilter;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
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
                                                   DaoAuthenticationProvider daoAuthenticationProvider) throws Exception{

        httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sessionConfig->
                        sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/swagger-ui.html").permitAll()
                        .requestMatchers("/admins").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/cliente", "/api/entregador", "/api/restaurante").permitAll()
                        .requestMatchers(HttpMethod.POST, "/**/recuperar-senha", "/**/redefinir-senha").permitAll()
                        .requestMatchers("/api/*/recuperacao/**").permitAll() // Rotas de recuperação
                        .requestMatchers(HttpMethod.GET, "/api/restaurante/abertos", "/api/restaurante/{id}", "/api/restaurante/{idRestaurante}/produtos").permitAll()

                        .requestMatchers("/admins/**").hasRole("ADMIN")

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
