package com.estudos.br.parkapi;

import com.estudos.br.parkapi.web.dto.ClienteCreateDTO;
import com.estudos.br.parkapi.web.dto.ClienteResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/clientes/clientes-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/clientes/clientes-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ClienteIT {

    @Autowired
    WebTestClient testClient;

    @Test
    public void createCliente_ComDadosValidos_RetornarClienteCriadoStatus201 () {
        ClienteResponseDTO responseBody = testClient
                .post()
                .uri("/api/v1/clientes")
                .headers(JwtAuthentication.getHeaderAuthentication(testClient, "joselu@email.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ClienteCreateDTO("Juca Fernandes", "40332850080"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ClienteResponseDTO.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getNome()).isEqualTo("Juca Fernandes");
        org.assertj.core.api.Assertions.assertThat(responseBody.getCpf()).isEqualTo("40332850080");
    }
}
