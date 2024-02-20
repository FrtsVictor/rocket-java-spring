package com.rocket.gestao_vagas.modules.company.use_cases;

import com.rocket.gestao_vagas.modules.company.entities.JobEntity;
import com.rocket.gestao_vagas.modules.company.repositories.JobRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateJobUseCase {

    private final JobRepository jobRepository;

    public CreateJobUseCase(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public JobEntity execute(JobEntity jobEntity) {
        return this.jobRepository.save(jobEntity);
    }

}
