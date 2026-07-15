package com.store.www.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Se ejecuta UNA vez por cada petición (OncePerRequestFilter).
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        // 1. Sin "Authorization: Bearer xxx", no autentica y deja seguir. (Si el
        // endpoint es público, pasará; si es protegido, Security lo rechazará luego.)
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2. Quita el prefijo "Bearer " (7 caracteres) para dejar el token.
        final String token = authHeader.substring(7);
        String username = null;
        try {
            username = jwtService.extraerUsername(token);
        } catch (Exception e) {
            // token inválido/expirado/malformado → no autenticamos, seguimos sin usuario
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Si hay username y aún no hay nadie autenticado en este hilo...
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // 4. Si el token es válido para ese usuario, lo marca como autenticado.
            if (jwtService.esValido(token, username)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // 5. Seguir con la cadena de filtros (llegar al controller si todo bien).
        filterChain.doFilter(request, response);
    }
}
