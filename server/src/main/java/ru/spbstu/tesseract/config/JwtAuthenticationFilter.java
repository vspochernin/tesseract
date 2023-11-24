package ru.spbstu.tesseract.config;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    public static final String AUTHORIZATION_HEADER_STARTS_WITH = "Bearer ";

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException
    {
        final String authHeader = request.getHeader(AUTHORIZATION_HEADER_NAME);
        final String jwt;
        final String userLogin;

        if (authHeader == null || !authHeader.startsWith(AUTHORIZATION_HEADER_STARTS_WITH)) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(AUTHORIZATION_HEADER_STARTS_WITH.length());
        userLogin = jwtService.extractUsername(jwt);  // TODO: extract user login from JWT.
    }
}
