package com.rocket.gestao_vagas.modules.company.use_cases;

import com.rocket.gestao_vagas.exceptions.UserFoundException;
import com.rocket.gestao_vagas.modules.company.entities.CompanyEntity;
import com.rocket.gestao_vagas.modules.company.repositories.CompanyRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateCompanyUseCase {

    private final CompanyRepository companyRepository;

    private final PasswordEncoder passwordEncoder;

    public CreateCompanyUseCase(CompanyRepository companyRepository, PasswordEncoder passwordEncoder) {
        this.companyRepository = companyRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public CompanyEntity execute(CompanyEntity company) {
        this.companyRepository
                .findByUsernameOrEmail(company.getUsername(), company.getEmail())
                .ifPresent(user -> {
                    throw new UserFoundException();
                });
        var password = this.passwordEncoder.encode(company.getPassword());
        company.setPassword(password);
        return this.companyRepository.save(company);
    }
}