package com.rocket.gestao_vagas.modules.security;

import com.rocket.gestao_vagas.modules.providers.CandidateJWTProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilterCandidate extends OncePerRequestFilter {

    private final CandidateJWTProvider candidateJWTProvider;

    public SecurityFilterCandidate(CandidateJWTProvider candidateJWTProvider) {
        this.candidateJWTProvider = candidateJWTProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().startsWith("/candidate")) {
            String header = request.getHeader("Authorization");

            if (header != null) {
                var decodedJWT = this.candidateJWTProvider.validateToken(header);

                if (decodedJWT == null) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }

                request.setAttribute("candidate_id", decodedJWT.getSubject());
                var roles = decodedJWT.getClaim("roles").asList(String.class).stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase())).toList();

                var usernamePasswordToken = new UsernamePasswordAuthenticationToken(decodedJWT.getSubject(), null, roles);
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
