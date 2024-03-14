package com.rocket.gestao_vagas.modules.company.use_cases;

import com.rocket.gestao_vagas.modules.company.entities.JobEntity;
import com.rocket.gestao_vagas.modules.company.repositories.JobRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ListAllJobsByDescriptionContainingUseCase {

    JobRepository jobRepository;

    public ListAllJobsByDescriptionContainingUseCase(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public Set<JobEntity> execute(String filter) {
        return this.jobRepository.findAllByDescriptionContainingIgnoreCase(filter);
    }
}
