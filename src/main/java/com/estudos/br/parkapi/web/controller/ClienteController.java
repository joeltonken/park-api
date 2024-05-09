package com.estudos.br.parkapi.web.controller;

import com.estudos.br.parkapi.entity.Cliente;
import com.estudos.br.parkapi.jwt.JwtUserDetails;
import com.estudos.br.parkapi.service.ClienteService;
import com.estudos.br.parkapi.service.UsuarioService;
import com.estudos.br.parkapi.web.dto.ClienteCreateDTO;
import com.estudos.br.parkapi.web.dto.ClienteResponseDTO;
import com.estudos.br.parkapi.web.dto.mapper.ClienteMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Clientes", description = "Contém todas as operações relativas aos recursos para cadastro, edição e leitura de um cliente.")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/clientes")
public class ClienteController {

    private final ClienteService clienteService;
    private final UsuarioService usuarioService;

    @PostMapping
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<ClienteResponseDTO> create(@RequestBody @Valid ClienteCreateDTO dto,
                                                     @AuthenticationPrincipal JwtUserDetails userDetails) {
        Cliente cliente = ClienteMapper.toCliente(dto);
        cliente.setUsuario(usuarioService.buscarPorId(userDetails.getId()));
        clienteService.salvar(cliente);
        return ResponseEntity.status(201).body(ClienteMapper.toDTO(cliente));
    }

}
