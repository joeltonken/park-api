package com.estudos.br.parkapi;

import com.estudos.br.parkapi.web.dto.VagaCreateDTO;
import com.estudos.br.parkapi.web.dto.VagaResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/vagas/vagas-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/vagas/vagas-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class VagaIT {

    @Autowired
    WebTestClient testClient;

    @Test
    public void criarVaga_ComDadosValidos_RetornarLocationCriadoStatus201 () {
        testClient
                .post()
                .uri("/api/v1/vagas")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthentication(testClient, "joelton@email.com", "123456"))
                .bodyValue(new VagaCreateDTO("B-06", "LIVRE"))
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().exists(HttpHeaders.LOCATION);

    }

    @Test
    public void criarVaga_ComCodigoExistente_RetornarErrorMessageComStatus409 () {
        testClient
                .post()
                .uri("/api/v1/vagas")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthentication(testClient, "joelton@email.com", "123456"))
                .bodyValue(new VagaCreateDTO("B-05", "LIVRE"))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody()
                .jsonPath("status").isEqualTo(409)
                .jsonPath("method").isEqualTo("POST")
                .jsonPath("path").isEqualTo("/api/v1/vagas");
    }

    @Test
    public void criarVaga_ComDadosInvalidos_RetornarErrorMessageComStatus422 () {
        testClient
                .post()
                .uri("/api/v1/vagas")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthentication(testClient, "joelton@email.com", "123456"))
                .bodyValue(new VagaCreateDTO("", ""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody()
                .jsonPath("status").isEqualTo(422)
                .jsonPath("method").isEqualTo("POST")
                .jsonPath("path").isEqualTo("/api/v1/vagas");

        testClient
                .post()
                .uri("/api/v1/vagas")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthentication(testClient, "joelton@email.com", "123456"))
                .bodyValue(new VagaCreateDTO("A-503", "DESOCUPADA"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody()
                .jsonPath("status").isEqualTo(422)
                .jsonPath("method").isEqualTo("POST")
                .jsonPath("path").isEqualTo("/api/v1/vagas");
    }

    @Test
    public void buscarVaga_ComCodigoExistente_RetornarVagaComStatus200 () {
        testClient
                .get()
                .uri("/api/v1/vagas/{codigo}", "B-02")
                .headers(JwtAuthentication.getHeaderAuthentication(testClient, "joelton@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("id").isEqualTo(10)
                .jsonPath("codigo").isEqualTo("B-02")
                .jsonPath("status").isEqualTo("LIVRE");
    }

    @Test
    public void buscarVaga_ComCodigoInexistente_RetornarErrorMessageComStatus404 () {
        testClient
                .get()
                .uri("/api/v1/vagas/{codigo}", "B-11")
                .headers(JwtAuthentication.getHeaderAuthentication(testClient, "joelton@email.com", "123456"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("status").isEqualTo(404)
                .jsonPath("method").isEqualTo("GET")
                .jsonPath("path").isEqualTo("/api/v1/vagas/B-11");
    }

}
