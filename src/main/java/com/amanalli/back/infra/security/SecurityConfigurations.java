package com.amanalli.back.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity //Anotacion para que spring sepa que es del contexto de seguridad
public class SecurityConfigurations {

    private final SecurityFilter securityFilter;

    @Autowired
    public SecurityConfigurations(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }

    // tipo de objeto para autenticacion stateless
    @Bean // Disponible en mi contexto de spring
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                //.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // le indicamos a spring el tipo de sesion
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers(HttpMethod.POST, "/auth").permitAll()
                        //requestMatchers(HttpMethod.POST, "/productos/**").hasRole("ADMIN")

                                /*.requestMatchers(HttpMethod.PUT, "/productos/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/productos/**").hasRole("ADMIN")

                                .requestMatchers(HttpMethod.PUT, "/usuarios/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/usuarios/**").hasRole("ADMIN") */

                                .requestMatchers(HttpMethod.POST, "/auth").permitAll()
                                //.requestMatchers(HttpMethod.GET, "/productos/**").permitAll()
                                .requestMatchers("/productos/**").permitAll()
                                .requestMatchers("/usuarios/**").permitAll()
                                .requestMatchers("/categorias/**").permitAll()
                                .requestMatchers("/regiones/**").permitAll()
                                .requestMatchers("/detalle-pedido/**").permitAll()
                                .requestMatchers("/venta-pedidos**").permitAll()
                                .requestMatchers("/swagger-ui.html", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(AuthService userDetailsService,
                                                         PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }


    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Tipo de algoritmo hashing que usamos para la authentication
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
