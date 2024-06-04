package com.estudos.br.parkapi.web.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioSenhaDTO {

    @NotBlank
    @Size(min = 6, max = 10)
    private String senhaAtual;
    @NotBlank
    @Size(min = 6, max = 10)
    private String novaSenha;
    @NotBlank
    @Size(min = 6, max = 10)
    private String confirmaSenha;
}
