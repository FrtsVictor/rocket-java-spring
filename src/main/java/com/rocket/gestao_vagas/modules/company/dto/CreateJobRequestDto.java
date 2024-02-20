package com.rocket.gestao_vagas.modules.company.dto;

import lombok.Data;

@Data
public class CreateJobRequestDto {
    private String description;
    private String benefits;
    private String level;
}
