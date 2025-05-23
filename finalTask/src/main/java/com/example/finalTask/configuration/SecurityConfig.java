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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Отключаем CSRF и CORS для REST API
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)

                // Настройка авторизации запросов
                .authorizeHttpRequests(auth -> auth
                        // Публичные эндпоинты
                        .requestMatchers(
                                "/",
                                "/error",
                                "/favicon.ico",
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-resources/**",
                                "/webjars/**"
                        ).permitAll()

                        // Аутентификация и регистрация
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/users/register").permitAll()

                        // Фильтрация отелей (публичный доступ)
                        .requestMatchers(HttpMethod.GET, "/api/v1/hotels/filter").permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/v1/rooms/filter").permitAll()
                        .requestMatchers("/api/v1/admin/statistics/**").hasRole("ADMIN")

                        // Просмотр отелей (публичный доступ)
                        .requestMatchers(HttpMethod.GET, "/api/v1/hotels/**").permitAll()

                        // Управление отелями (только админ)
                        .requestMatchers(HttpMethod.POST, "/api/v1/hotels/**").hasRole(UserRole.ROLE_ADMIN.name())
                        .requestMatchers(HttpMethod.PUT, "/api/v1/hotels/**").hasRole(UserRole.ROLE_ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/hotels/**").hasRole(UserRole.ROLE_ADMIN.name())

                        // Оценка отелей (авторизованные пользователи)
                        .requestMatchers(HttpMethod.POST, "/api/v1/hotels/**/rating").hasAnyRole(
                                UserRole.ROLE_USER.name(),
                                UserRole.ROLE_ADMIN.name()
                        )

                        // Просмотр комнат (публичный доступ)
                        .requestMatchers(HttpMethod.GET, "/api/v1/rooms/**").permitAll()

                        // Управление комнатами (только админ)
                        .requestMatchers(HttpMethod.POST, "/api/v1/rooms/**").hasRole(UserRole.ROLE_ADMIN.name())
                        .requestMatchers(HttpMethod.PUT, "/api/v1/rooms/**").hasRole(UserRole.ROLE_ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/rooms/**").hasRole(UserRole.ROLE_ADMIN.name())

                        // Бронирования (авторизованные пользователи)
                        .requestMatchers("/api/v1/bookings/**").hasAnyRole(
                                UserRole.ROLE_USER.name(),
                                UserRole.ROLE_ADMIN.name()
                        )

                        // Админские эндпоинты
                        .requestMatchers("/api/v1/admin/**").hasRole(UserRole.ROLE_ADMIN.name())

                        // Все остальные запросы требуют аутентификации
                        .anyRequest().authenticated()
                )

                // Настройка аутентификации
                .httpBasic(basic -> basic
                        .realmName("Hotel Booking API")
                )

                // Настройка сессий (без состояния)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
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