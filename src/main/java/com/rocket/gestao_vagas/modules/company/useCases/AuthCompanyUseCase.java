package com.rocket.gestao_vagas.modules.company.useCases;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.rocket.gestao_vagas.exceptions.AppAuthenticationException;
import com.rocket.gestao_vagas.modules.company.dto.AuthCompanyDto;
import com.rocket.gestao_vagas.modules.company.repositories.CompanyRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthCompanyUseCase {

    @Value("${secyrity.auth.secret}")
    private String secret;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public String execute(AuthCompanyDto authCompanyDto) {
        var company = this.companyRepository.findByUsername(authCompanyDto.getUsername()).orElseThrow(AppAuthenticationException::new);

        if (!this.passwordEncoder.matches(authCompanyDto.getPassword(), company.getPassword())) {
            throw new AppAuthenticationException();
        }

        Algorithm algorithm = Algorithm.HMAC256(this.secret);

        return JWT.create().withIssuer("javagas").withSubject(company.getId().toString()).sign(algorithm);
    }
}
