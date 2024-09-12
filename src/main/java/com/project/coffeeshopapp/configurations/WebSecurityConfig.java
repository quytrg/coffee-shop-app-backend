package com.project.coffeeshopapp.configurations;

import com.project.coffeeshopapp.filters.ExceptionHandlerFilter;
import com.project.coffeeshopapp.filters.JwtAuthenticationFilter;
import com.project.coffeeshopapp.security.handlers.CustomAccessDeniedHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final ExceptionHandlerFilter exceptionHandlerFilter;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Value("${api.prefix}")
    private String apiPrefix;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests ->
                        requests
                                .requestMatchers(
                                        String.format("%s/users/login", apiPrefix)
                                ).permitAll()
                                .requestMatchers(
                                        HttpMethod.GET,
                                        String.format("%s/users/**", apiPrefix)
                                ).hasAnyAuthority("USER_VIEW")
                                .requestMatchers(
                                        HttpMethod.POST,
                                        String.format("%s/users/**", apiPrefix)
                                ).hasAnyAuthority("USER_CREATE")
                                .requestMatchers(
                                        HttpMethod.PATCH,
                                        String.format("%s/users/**", apiPrefix)
                                ).hasAnyAuthority("USER_EDIT")
                                .requestMatchers(
                                        HttpMethod.DELETE,
                                        String.format("%s/users/**", apiPrefix)
                                ).hasAnyAuthority("USER_DELETE")
                                .requestMatchers(
                                        HttpMethod.GET,
                                        String.format("%s/roles/**", apiPrefix)
                                ).hasAnyAuthority("ROLE_VIEW")
                                .requestMatchers(
                                        HttpMethod.POST,
                                        String.format("%s/roles/**", apiPrefix)
                                ).hasAnyAuthority("ROLE_CREATE")
                                .requestMatchers(
                                        HttpMethod.PATCH,
                                        String.format("%s/roles/**", apiPrefix)
                                ).hasAnyAuthority("ROLE_EDIT")
                                .requestMatchers(
                                        HttpMethod.DELETE,
                                        String.format("%s/roles/**", apiPrefix)
                                ).hasAnyAuthority("ROLE_DELETE")
                            .anyRequest().authenticated()
                )
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                .accessDeniedHandler(customAccessDeniedHandler)
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(exceptionHandlerFilter, JwtAuthenticationFilter.class);

        return http.build();
    }
}