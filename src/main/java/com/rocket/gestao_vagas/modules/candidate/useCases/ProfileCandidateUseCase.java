package com.rocket.gestao_vagas.modules.candidate.useCases;

import com.rocket.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDto;
import com.rocket.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProfileCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    public ProfileCandidateResponseDto execute(UUID idCandidate) {
        var candidate = this.candidateRepository.findById(idCandidate).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return ProfileCandidateResponseDto.builder()
                .username(candidate.getUsername())
                .name(candidate.getName())
                .email(candidate.getEmail())
                .id(candidate.getId())
                .description(candidate.getDescription())
                .build();
    }
}
