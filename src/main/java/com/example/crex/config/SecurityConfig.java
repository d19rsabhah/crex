package com.example.crex.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(
                                "/api/v1/auth/user/logIn",
                                "/api/v1/auth/user/register",
                                "/api/v1/auth/user/logOut"
                        ).permitAll()

                        // PUBLIC AUTH APIS
                        .requestMatchers("/api/v1/public/**").permitAll()

                        // USER ROLE APIs
                        .requestMatchers(HttpMethod.POST, "/api/v1/teams/**", "/api/v1/players/**").hasRole("USER")


                        .requestMatchers(HttpMethod.PUT, "/api/v1/players/**")
                        .hasRole("USER")

                        // ADMIN ROLE APIs
                        .requestMatchers("/api/v1/tournaments/**", "/api/v1/series/**", "/api/v1/matches/**")
                        .hasRole("ADMIN")

                        // USER + ADMIN allowed to GET players
                        .requestMatchers(HttpMethod.GET, "/api/v1/players/**")
                        .hasAnyRole("USER", "ADMIN")


                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 🔥 You will need this for login (AuthenticationManager)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}

