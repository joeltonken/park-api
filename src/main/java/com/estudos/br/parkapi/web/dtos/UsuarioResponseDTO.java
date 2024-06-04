package com.estudos.br.parkapi.web.dtos;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class UsuarioResponseDTO {

    private Long id;
    private String username;
    private String role;

}
