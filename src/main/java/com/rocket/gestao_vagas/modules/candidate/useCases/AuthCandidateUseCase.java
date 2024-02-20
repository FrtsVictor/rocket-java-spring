package com.rocket.gestao_vagas.modules.candidate.useCases;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.rocket.gestao_vagas.exceptions.AppAuthenticationException;
import com.rocket.gestao_vagas.modules.candidate.dto.AuthCandidateRequestDto;
import com.rocket.gestao_vagas.modules.candidate.dto.AuthCandidateResponseDto;
import com.rocket.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
public class AuthCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Value("${security.auth.secret.candidate}")
    private String secret;

    public AuthCandidateResponseDto execute(AuthCandidateRequestDto authCandidateRequestDto) {
        var candidate = this.candidateRepository.findByUsername(authCandidateRequestDto.getUsername()).orElseThrow(AppAuthenticationException::new);

        if (!this.passwordEncoder.matches(authCandidateRequestDto.getPassword(), candidate.getPassword())) {
            throw new AppAuthenticationException();
        }

        var expiresIn = Instant.now().plus(Duration.ofHours(4));

        Algorithm algorithm = Algorithm.HMAC256(this.secret);

        var token = JWT.create()
                .withIssuer("javagas")
                .withSubject(candidate.getId().toString())
                .withExpiresAt(expiresIn)
                .withClaim("roles", List.of("CANDIDATE"))
                .sign(algorithm);

        return AuthCandidateResponseDto.builder().accessToken(token).expiresIn(expiresIn).build();
    }

}
