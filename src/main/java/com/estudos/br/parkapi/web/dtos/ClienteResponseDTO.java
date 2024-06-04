package com.estudos.br.parkapi.web.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ClienteResponseDTO {

    private Long id;
    private String nome;
    private String cpf;
}
