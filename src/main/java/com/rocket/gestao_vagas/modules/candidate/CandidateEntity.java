package com.rocket.gestao_vagas.modules.candidate;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "candidate")
public class CandidateEntity {

    @Id()
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    private String name;

    @NotBlank
    @Column(unique = true)
    private String username;

    @Email
    @Column(unique = true)
    private String email;

    @Length(min = 5, max = 100)
    private String password;

    private String description;

    private String curriculum;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
