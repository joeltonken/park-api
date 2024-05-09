package com.estudos.br.parkapi.service;

import com.estudos.br.parkapi.entity.Cliente;
import com.estudos.br.parkapi.exception.CpfUniqueViolationException;
import com.estudos.br.parkapi.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Transactional
    public Cliente salvar(Cliente cliente) {
        try {
            return clienteRepository.save(cliente);
        } catch (DataIntegrityViolationException ex) {
            throw new CpfUniqueViolationException
                    (String.format("CPF '%s' não pode ser cadastrado, pois já existe no sistema.", cliente.getCpf()));
        }
    }


}
