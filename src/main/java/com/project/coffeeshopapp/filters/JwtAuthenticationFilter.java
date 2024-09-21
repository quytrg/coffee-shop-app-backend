package com.project.coffeeshopapp.filters;

import com.project.coffeeshopapp.customexceptions.UnauthorizedException;
import com.project.coffeeshopapp.models.CustomUserDetails;
import com.project.coffeeshopapp.utils.JwtUtil;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Value("${api.prefix}")
    private String apiPrefix;

    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    private List<Pair<String, String>> bypassTokens;

    @PostConstruct
    public void init() {
        bypassTokens = Arrays.asList(
                Pair.of(String.format("%s/users/login", apiPrefix), "POST")
        );
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            // bypass the filter for endpoints that are allowed by permitAll()
            if(isBypassToken(request)) {
                filterChain.doFilter(request, response);
                return;
            }
            final String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new UnauthorizedException("Unauthorized");
            }
            final String token = authHeader.substring(7);
            final String phoneNumber = jwtUtil.extractPhoneNumber(token);
            if (phoneNumber != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                CustomUserDetails customUserDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(phoneNumber);
                if (customUserDetails != null && !customUserDetails.isEnabled()) {
                    throw new UnauthorizedException("Unauthorized");
                }
                if(jwtUtil.validateToken(token, customUserDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    customUserDetails,
                                    null,
                                    customUserDetails.getAuthorities()
                            );
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            throw new UnauthorizedException("Unauthorized");
        }
    }

    private boolean isBypassToken(@NonNull  HttpServletRequest request) {
        return bypassTokens.stream().anyMatch(
                pair -> request.getServletPath().contains(pair.getFirst()) &&
                        request.getMethod().equals(pair.getSecond())
        );
    }
}