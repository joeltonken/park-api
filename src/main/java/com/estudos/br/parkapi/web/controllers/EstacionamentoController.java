package com.estudos.br.parkapi.web.controllers;

import com.estudos.br.parkapi.entities.ClienteVaga;
import com.estudos.br.parkapi.services.ClienteVagaService;
import com.estudos.br.parkapi.services.EstacionamentoService;
import com.estudos.br.parkapi.web.dto.EstacionamentoCreateDTO;
import com.estudos.br.parkapi.web.dto.EstacionamentoResponseDTO;
import com.estudos.br.parkapi.web.dto.mapper.ClienteVagaMapper;
import com.estudos.br.parkapi.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.PATH;

@Tag(name = "Estacionamento", description = "Contém todas as operações relativas ao recurso de estacionamento.")
@RequiredArgsConstructor
@RestController
@RequestMapping("estacionamentos")
public class EstacionamentoController {

    private final EstacionamentoService estacionamentoService;
    private final ClienteVagaService clienteVagaService;

    @Operation(summary = "Operação de check-in", description = "Recurso para dar entrada de um veículo no estacionamento. " +
            "Requisição exige uso de um bearer token.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                            headers = @Header(name = HttpHeaders.LOCATION, description = "URL de acesso ao recurso criado"),
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = EstacionamentoResponseDTO.class))),
                    @ApiResponse(responseCode = "403", description = "Recurso não permitido ao perfil do CLIENTE",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Causas possiveis: <br/>" +
                            "- CPF do cliente não cadastrado no sistema; <br/>" +
                            "- Nenhuma vaga livre foi localizada",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Recurso não processado por falta de dados ou dados inválidos",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping("/check-in")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EstacionamentoResponseDTO> checkin(@RequestBody @Valid EstacionamentoCreateDTO dto) {
        ClienteVaga clienteVaga = ClienteVagaMapper.toClienteVaga(dto);
        estacionamentoService.checkIn(clienteVaga);
        EstacionamentoResponseDTO responseDTO = ClienteVagaMapper.toDTO(clienteVaga);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri().path("/{recibo}")
                .buildAndExpand(clienteVaga.getRecibo())
                .toUri();
        return ResponseEntity.created(location).body(responseDTO);
    }

    @Operation(summary = "Localizar um veículo estacionado", description = "Recurso para retornar um veículo estacionado " +
            "pelo nº do recibo. Requisição exige uso de um bearer token.",
            security = @SecurityRequirement(name = "security"),
            parameters = {
                @Parameter(in = PATH, name = "recibo", description = "Número do recibo gerado pelo check-in")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso localizado com sucesso",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = EstacionamentoResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Número do recibo não encontrado.",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
            })
    @GetMapping("/check-in/{recibo}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE')")
    public ResponseEntity<EstacionamentoResponseDTO> getByRecibo (@PathVariable String recibo) {
        ClienteVaga clienteVaga = clienteVagaService.buscarPorRecibo(recibo);
        EstacionamentoResponseDTO dto = ClienteVagaMapper.toDTO(clienteVaga);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/check-out/{recibo}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EstacionamentoResponseDTO> checkout (@PathVariable String recibo) {
        ClienteVaga clienteVaga = estacionamentoService.checkout(recibo);
        EstacionamentoResponseDTO dto = ClienteVagaMapper.toDTO(clienteVaga);
        return ResponseEntity.ok(dto);
    }

}
