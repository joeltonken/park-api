package com.estudos.br.parkapi.services;

import com.estudos.br.parkapi.entities.ClienteVaga;
import com.estudos.br.parkapi.exceptions.EntityNotFoundException;
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

    @Transactional(readOnly = true)
    public ClienteVaga buscarPorRecibo(String recibo) {
        return clienteVagaRepository.findByReciboAndDataSaidaIsNull(recibo)
                .orElseThrow(
                        () -> new EntityNotFoundException(String.format("Recibo '%s' não encontrado no sistema ou check-out já realizado", recibo)
                    )
                );
    }

    @Transactional(readOnly = true)
    public long getTotalDeVezesEstacionamentoCompleto(String cpf) {
        return clienteVagaRepository.countByClienteCpfAndDataSaidaIsNotNull(cpf);
    }

}
