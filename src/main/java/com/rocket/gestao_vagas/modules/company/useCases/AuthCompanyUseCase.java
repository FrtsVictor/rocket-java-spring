package com.rocket.gestao_vagas.modules.company.useCases;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.rocket.gestao_vagas.exceptions.AppAuthenticationException;
import com.rocket.gestao_vagas.modules.company.dto.AuthCompanyRequestDto;
import com.rocket.gestao_vagas.modules.company.dto.AuthCompanyResponseDto;
import com.rocket.gestao_vagas.modules.company.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
public class AuthCompanyUseCase {

    @Value("${security.auth.secret.company}")
    private String secret;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public AuthCompanyResponseDto execute(AuthCompanyRequestDto authCompanyRequestDto) {
        var company = this.companyRepository.findByUsername(authCompanyRequestDto.getUsername()).orElseThrow(AppAuthenticationException::new);

        if (!this.passwordEncoder.matches(authCompanyRequestDto.getPassword(), company.getPassword())) {
            throw new AppAuthenticationException();
        }

        Algorithm algorithm = Algorithm.HMAC256(this.secret);
        var expiresIn = Instant.now().plus(Duration.ofHours(4));
        var token = JWT.create().withIssuer("javagas")
                .withSubject(company.getId().toString())
                .withExpiresAt(expiresIn)
                .withClaim("roles", List.of("COMPANY"))
                .sign(algorithm);

        return AuthCompanyResponseDto.builder()
                .accessToken(token)
                .expiresIn(expiresIn)
                .build();
    }
}
