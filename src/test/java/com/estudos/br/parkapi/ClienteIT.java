package com.estudos.br.parkapi;

import com.estudos.br.parkapi.web.dtos.ClienteCreateDTO;
import com.estudos.br.parkapi.web.dtos.ClienteResponseDTO;
import com.estudos.br.parkapi.web.dtos.PageableDto;
import com.estudos.br.parkapi.web.exceptionhandler.ErrorMessage;

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
    public void buscarCliente_ComIdExistentePeloAdmin_RetornarClienteStatus200 () {
        ClienteResponseDTO responseBody = testClient
                .get()
                .uri("/clientes/10")
                .headers(JwtAuthentication.getHeaderAuthentication(testClient, "joelton@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ClienteResponseDTO.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(10);
    }

    @Test
    public void buscarCliente_ComIdInexistentePeloAdmin_RetornarErrorMessageComStatus403 () {
        ErrorMessage responseBody = testClient
                .get()
                .uri("/clientes/0")
                .headers(JwtAuthentication.getHeaderAuthentication(testClient, "joe@email.com", "123456"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
    }

    @Test
    public void buscarCliente_ComIdInexistentePeloCliente_RetornarErrorMessageComStatus404 () {
        ErrorMessage responseBody = testClient
                .get()
                .uri("/clientes/0")
                .headers(JwtAuthentication.getHeaderAuthentication(testClient, "joelton@email.com", "123456"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }

    @Test
    public void buscarClientes_ComPaginacaoPeloAdmin_RetornarClientesComStatus200 () {
        PageableDto responseBody = testClient
                .get()
                .uri("/clientes")
                .headers(JwtAuthentication.getHeaderAuthentication(testClient, "joelton@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(PageableDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getContent().size()).isEqualTo(2);
        org.assertj.core.api.Assertions.assertThat(responseBody.getNumber()).isEqualTo(0);
        org.assertj.core.api.Assertions.assertThat(responseBody.getTotalPages()).isEqualTo(1);

        responseBody = testClient
                .get()
                .uri("/clientes?size=1&page=1")
                .headers(JwtAuthentication.getHeaderAuthentication(testClient, "joelton@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(PageableDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getContent().size()).isEqualTo(1);
        org.assertj.core.api.Assertions.assertThat(responseBody.getNumber()).isEqualTo(1);
        org.assertj.core.api.Assertions.assertThat(responseBody.getTotalPages()).isEqualTo(2);
    }

    @Test
    public void buscarClientes_ComPaginacaoPeloCliente_RetornarErrorMessageComStatus403 () {
        ErrorMessage responseBody = testClient
                .get()
                .uri("/clientes")
                .headers(JwtAuthentication.getHeaderAuthentication(testClient, "joe@email.com", "123456"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
    }

    @Test
    public void buscarCliente_ComDadosTokenDeCliente_RetornarClienteComStatus200 () {
        ClienteResponseDTO responseBody = testClient
                .get()
                .uri("/clientes/detalhes")
                .headers(JwtAuthentication.getHeaderAuthentication(testClient, "joe@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ClienteResponseDTO.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getCpf()).isEqualTo("64867663018");
        org.assertj.core.api.Assertions.assertThat(responseBody.getNome()).isEqualTo("Joe Gomes");
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(20);
    }

    @Test
    public void buscarCliente_ComDadosTokenDeAdmin_RetornarErrorMessageComStatus403 () {
        ErrorMessage responseBody = testClient
                .get()
                .uri("/clientes/detalhes")
                .headers(JwtAuthentication.getHeaderAuthentication(testClient, "joelton@email.com", "123456"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
    }

    @Test
    public void criarCliente_ComDadosValidos_RetornarClienteCriadoStatus201 () {
        ClienteResponseDTO responseBody = testClient
                .post()
                .uri("/clientes")
                .headers(JwtAuthentication.getHeaderAuthentication(testClient, "joselu@email.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ClienteCreateDTO("Juca Fernandes", "84869827077"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ClienteResponseDTO.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getNome()).isEqualTo("Juca Fernandes");
        org.assertj.core.api.Assertions.assertThat(responseBody.getCpf()).isEqualTo("84869827077");
    }

    @Test
    public void criarCliente_ComUsuarioNaoPermitido_RetornarErrorMessageStatus403 () {
        ErrorMessage responseBody = testClient
                .post()
                .uri("/clientes")
                .headers(JwtAuthentication.getHeaderAuthentication(testClient, "joelton@email.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ClienteCreateDTO("Juca Fernandes", "84869827077"))
                .exchange()
                .expectStatus().isEqualTo(403)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
    }

    @Test
    public void criarCliente_ComCpfJaCadastrado_RetornarErrorMessageStatus409 () {
        ErrorMessage responseBody = testClient
                .post()
                .uri("/clientes")
                .headers(JwtAuthentication.getHeaderAuthentication(testClient, "joselu@email.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ClienteCreateDTO("Juca Fernandes", "14931923020"))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(409);
    }

    @Test
    public void criarCliente_ComDadosInvalidos_RetornarErrorMessageStatus422 () {
        ErrorMessage responseBody = testClient
                .post()
                .uri("/clientes")
                .headers(JwtAuthentication.getHeaderAuthentication(testClient, "joselu@email.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ClienteCreateDTO("", ""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        testClient
                .post()
                .uri("/clientes")
                .headers(JwtAuthentication.getHeaderAuthentication(testClient, "joselu@email.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ClienteCreateDTO("Joe", "00000000000"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        testClient
                .post()
                .uri("/clientes")
                .headers(JwtAuthentication.getHeaderAuthentication(testClient, "joselu@email.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new ClienteCreateDTO("Joelton", "149.319.230-20"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }


}
