package com.estudos.br.parkapi.services;

import com.estudos.br.parkapi.entities.ClienteVaga;
import com.estudos.br.parkapi.repositories.ClienteVagaRepository;
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
