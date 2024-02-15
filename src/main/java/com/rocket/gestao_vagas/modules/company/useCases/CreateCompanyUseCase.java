package com.rocket.gestao_vagas.modules.company.useCases;

import com.rocket.gestao_vagas.exceptions.UserFoundException;
import com.rocket.gestao_vagas.modules.company.repositories.CompanyRepository;
import com.rocket.gestao_vagas.modules.company.entities.CompanyEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateCompanyUseCase {

    @Autowired
    private CompanyRepository companyRepository;

    public CompanyEntity execute(CompanyEntity company) {
        this.companyRepository
                .findByUsernameOrEmail(company.getUsername(), company.getEmail())
                .ifPresent(user -> {
                    throw new UserFoundException();
                });

        return this.companyRepository.save(company);
    }
}
