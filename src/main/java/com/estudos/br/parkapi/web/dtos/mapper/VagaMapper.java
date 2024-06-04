package com.estudos.br.parkapi.web.dtos.mapper;

import com.estudos.br.parkapi.entities.Vaga;
import com.estudos.br.parkapi.web.dtos.VagaCreateDTO;
import com.estudos.br.parkapi.web.dtos.VagaResponseDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VagaMapper {

    public static Vaga toVaga(VagaCreateDTO dto) {
        return new ModelMapper().map(dto, Vaga.class);
    }

    public static VagaResponseDTO toDto(Vaga vaga) {
        return new ModelMapper().map(vaga, VagaResponseDTO.class);
    }

}
