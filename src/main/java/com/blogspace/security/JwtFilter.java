package com.blogspace.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Collections;

@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    private final SecretKey key;

    public JwtFilter(SecretKey jwtKey) {
        this.key = jwtKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = extractToken(request);

            log.debug("Processing request to '{}' with token: {}", request.getRequestURI(),
                    token != null ? "present" : "not present");

            if (token != null) {
                Claims claims = validateToken(token);
                if (claims != null) {
                    String userEmail = claims.getSubject();
                    Boolean isUser = claims.get("user", Boolean.class);


                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userEmail, null, isUser ? Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")) : Collections.emptyList());
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    log.debug("Authentication successful for user: {}", userEmail);
                }
            }

        } catch (Exception e) {
            log.error("Authentication error: {}", e.getMessage());
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String token =  request.getHeader("x-auth-token");
        log.debug("Extracted token: {}", token != null ? "present" : "not present");
        return token;
    }

    private Claims validateToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            log.debug("Token validation successful");
            return claims;
        } catch (JwtException e) {
            log.error("Token validation failed: {}", e.getMessage());
            return null;
        }
    }
}
