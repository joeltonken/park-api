package com.estudos.br.parkapi.service;

import com.estudos.br.parkapi.entity.ClienteVaga;
import com.estudos.br.parkapi.repository.ClienteVagaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClienteVagaService {

    private final ClienteVagaRepository clienteVagaRepository;

    @Transactional
    public ClienteVaga salvar (ClienteVaga clienteVaga){
        return clienteVagaRepository.save(clienteVaga);
    }

}
