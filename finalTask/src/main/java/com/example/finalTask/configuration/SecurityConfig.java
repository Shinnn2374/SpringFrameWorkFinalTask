package com.example.finalTask.configuration;

import com.example.finalTask.utils.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // Публичные эндпоинты
                        .requestMatchers(
                                "/api/v1/auth/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-resources/**",
                                "/webjars/**"
                        ).permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/v1/users/register").permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/v1/hotels/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/hotels/**").hasRole(UserRole.ROLE_ADMIN.name())
                        .requestMatchers(HttpMethod.PUT, "/api/v1/hotels/**").hasRole(UserRole.ROLE_ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/hotels/**").hasRole(UserRole.ROLE_ADMIN.name())
                        .requestMatchers(HttpMethod.POST, "/api/v1/hotels/**/rating").hasAnyRole(
                                UserRole.ROLE_USER.name(),
                                UserRole.ROLE_ADMIN.name()
                        )

                        .requestMatchers(HttpMethod.GET, "/api/v1/rooms/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/rooms/**").hasRole(UserRole.ROLE_ADMIN.name())
                        .requestMatchers(HttpMethod.PUT, "/api/v1/rooms/**").hasRole(UserRole.ROLE_ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/rooms/**").hasRole(UserRole.ROLE_ADMIN.name())

                        .requestMatchers(HttpMethod.GET, "/api/v1/bookings/**").hasAnyRole(
                                UserRole.ROLE_USER.name(),
                                UserRole.ROLE_ADMIN.name()
                        )
                        .requestMatchers(HttpMethod.POST, "/api/v1/bookings/**").hasAnyRole(
                                UserRole.ROLE_USER.name(),
                                UserRole.ROLE_ADMIN.name()
                        )

                        .requestMatchers("/api/v1/admin/**").hasRole(UserRole.ROLE_ADMIN.name())

                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .httpBasic(basic -> basic
                        .realmName("Hotel Booking API")
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}