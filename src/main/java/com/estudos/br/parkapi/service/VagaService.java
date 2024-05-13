package com.estudos.br.parkapi.service;

import com.estudos.br.parkapi.entity.Vaga;
import com.estudos.br.parkapi.exception.CodigoUniqueViolationException;
import com.estudos.br.parkapi.exception.EntityNotFoundException;
import com.estudos.br.parkapi.repository.VagaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class VagaService {

    private final VagaRepository vagaRepository;

    @Transactional
    public Vaga salvar(Vaga obj){
        try {
            return vagaRepository.save(obj);
        } catch (DataIntegrityViolationException e) {
            throw new CodigoUniqueViolationException(String.format("Vaga com código '%s' já cadastrada", obj.getCodigo()));
        }
    }

    @Transactional(readOnly = true)
    public Vaga buscarPorCodigo(String codigo) {
        return vagaRepository.findByCodigo(codigo).orElseThrow(
                () -> new EntityNotFoundException(String.format("Vaga com código '%s' não foi encontrada", codigo))
        );
    }

}
