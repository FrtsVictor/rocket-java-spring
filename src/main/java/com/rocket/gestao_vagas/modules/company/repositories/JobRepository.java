package com.rocket.gestao_vagas.modules.company.repositories;

import com.rocket.gestao_vagas.modules.company.entities.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;
import java.util.UUID;

public interface JobRepository extends JpaRepository<JobEntity, UUID> {
    Set<JobEntity> findAllByDescriptionContainingIgnoreCase(String filter);
}
