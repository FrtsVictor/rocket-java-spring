package com.rocket.gestao_vagas.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CreateJobRequestDto {
    @Schema(example = "Vaga para xpto...")
    private String description;

    @Schema(example = "Gym pass..")
    private String benefits;

    @Schema(example = "Senior/Pleno...")
    private String level;
}
