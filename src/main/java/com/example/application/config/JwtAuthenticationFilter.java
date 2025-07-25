package com.example.application.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import jakarta.servlet.http.Cookie;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException
    {
        String path = request.getRequestURI();
        if (path.startsWith("/VAADIN/") || path.startsWith("/frontend/") || path.startsWith("/images/")) {
            filterChain.doFilter(request, response);
            return;
        }
        System.out.println("Processing JWT authentication filter");
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null) {
            // Cookie'den JWT'yi al
            if (request.getCookies() != null) {
                System.out.println("No Authorization header found, checking cookies");
                for (var cookie : request.getCookies()) {
                    System.out.println("Checking cookie: " + cookie.getName());
                    if ("jwtToken".equals(cookie.getName())) {
                        authHeader = "Bearer " + cookie.getValue();
                        break;
                    }
                }
            }
        }
        System.out.println("Authorization header: " + authHeader);
        final String jwt;
        final String userEmail;
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }
            jwt = authHeader.substring(7);
            userEmail = jwtService.extractUsername(jwt);
            if (
                userEmail != null &&
                SecurityContextHolder.getContext().getAuthentication() == null
            ) {
                UserDetails userDetails = this.userDetailsService
                        .loadUserByUsername(userEmail);
                System.out.println("User details loaded: " + userDetails);
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } else {
                    // Token geçersiz veya süresi dolmuş
                    Cookie cookie = new Cookie("jwtToken", null);
                    cookie.setPath("/");
                    cookie.setMaxAge(0); // Cookie'yi sil
                    response.addCookie(cookie);
                    response.sendRedirect("/login/login"); // Login sayfasına yönlendir
                    return;
                }
            }
            filterChain.doFilter(request, response);
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            // Token süresi dolmuşsa cookie'yi sil ve login'e yönlendir
            Cookie cookie = new Cookie("jwtToken", null);
            cookie.setPath("/");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            response.sendRedirect("/login/login");
        }
    }
}