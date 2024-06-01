package com.estudos.br.parkapi.services;

import com.estudos.br.parkapi.entities.Cliente;
import com.estudos.br.parkapi.entities.ClienteVaga;
import com.estudos.br.parkapi.entities.Vaga;
import com.estudos.br.parkapi.util.EstacionamentoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EstacionamentoService {

    private final ClienteVagaService clienteVagaService;
    private final ClienteService clienteService;
    private final VagaService vagaService;

    @Transactional
    public ClienteVaga checkIn(ClienteVaga clienteVaga) {
        Cliente cliente = clienteService.buscarPorCpf(clienteVaga.getCliente().getCpf());
        clienteVaga.setCliente(cliente);

        Vaga vaga = vagaService.buscarPorVagaLivre();
        vaga.setStatus(Vaga.StatusVaga.OCUPADA);
        clienteVaga.setVaga(vaga);

        clienteVaga.setDataEntrada(LocalDateTime.now());
        clienteVaga.setRecibo(EstacionamentoUtils.gerarRecibo());

        return clienteVagaService.salvar(clienteVaga);
    }

    @Transactional
    public ClienteVaga checkout(String recibo) {
        ClienteVaga clienteVaga = clienteVagaService.buscarPorRecibo(recibo);
        LocalDateTime dataSaida = LocalDateTime.now();

        BigDecimal valor = EstacionamentoUtils.calcularCusto(clienteVaga.getDataEntrada(), dataSaida);
        clienteVaga.setValor(valor);

        long totalDeVezes = clienteVagaService.getTotalDeVezesEstacionamentoCompleto(clienteVaga.getCliente().getCpf());
        BigDecimal desconto = EstacionamentoUtils.calcularDesconto(valor, totalDeVezes);
        clienteVaga.setDesconto(desconto);

        clienteVaga.setDataSaida(dataSaida);
        clienteVaga.getVaga().setStatus(Vaga.StatusVaga.LIVRE);

        return clienteVagaService.salvar(clienteVaga);
    }


}
