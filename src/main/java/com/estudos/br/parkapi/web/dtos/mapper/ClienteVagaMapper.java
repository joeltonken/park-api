package com.estudos.br.parkapi.web.dtos.mapper;

import com.estudos.br.parkapi.entities.ClienteVaga;
import com.estudos.br.parkapi.web.dtos.EstacionamentoResponseDTO;
import com.estudos.br.parkapi.web.dtos.EstacionamentoCreateDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClienteVagaMapper {

    public static ClienteVaga toClienteVaga(EstacionamentoCreateDTO dto) {
        return new ModelMapper().map(dto, ClienteVaga.class);
    }

    public static EstacionamentoResponseDTO toDTO (ClienteVaga cliente) {
        return new ModelMapper().map(cliente, EstacionamentoResponseDTO.class);
    }

}
