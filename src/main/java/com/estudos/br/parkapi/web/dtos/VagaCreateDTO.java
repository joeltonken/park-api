package com.estudos.br.parkapi.web.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class VagaCreateDTO {

    @NotBlank (message = "{NotBlank.vagaCreateDTO.codigo}")
    @Size(min = 4, max = 4, message = "{Size.vagaCreateDTO.codigo}")
    private String codigo;

    @NotBlank (message = "{NotBlank.vagaCreateDTO.status}")
    @Pattern(regexp = "LIVRE|OCUPADA", message = "Pattern.vagaCreateDTO.status}")
    private String status;

}
