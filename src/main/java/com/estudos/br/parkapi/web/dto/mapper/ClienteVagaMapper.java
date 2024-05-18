package com.estudos.br.parkapi.web.dto.mapper;

import com.estudos.br.parkapi.entity.ClienteVaga;
import com.estudos.br.parkapi.web.dto.EstacionamentoResponseDTO;
import com.estudos.br.parkapi.web.dto.EstacionamentoCreateDTO;
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
