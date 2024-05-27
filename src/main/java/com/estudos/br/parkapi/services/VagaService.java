package com.estudos.br.parkapi.services;

import com.estudos.br.parkapi.entities.Vaga;
import com.estudos.br.parkapi.exceptions.CodigoUniqueViolationException;
import com.estudos.br.parkapi.exceptions.EntityNotFoundException;
import com.estudos.br.parkapi.repositories.VagaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.estudos.br.parkapi.entities.Vaga.StatusVaga.LIVRE;

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

    @Transactional(readOnly = true)
    public Vaga buscarPorVagaLivre() {
        return vagaRepository.findFirstByStatus(LIVRE).orElseThrow(
                () -> new EntityNotFoundException("Nenhuma vaga livre foi encontrada")
        );
    }


}
