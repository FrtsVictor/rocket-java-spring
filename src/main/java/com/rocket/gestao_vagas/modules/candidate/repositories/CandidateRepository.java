package com.rocket.gestao_vagas.modules.candidate.repositories;

import com.rocket.gestao_vagas.modules.candidate.CandidateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CandidateRepository extends JpaRepository<CandidateEntity, UUID> {
    Optional<CandidateEntity> findByUsernameOrEmail(String username, String password);

    Optional<CandidateEntity> findByUsername(String username);
}
