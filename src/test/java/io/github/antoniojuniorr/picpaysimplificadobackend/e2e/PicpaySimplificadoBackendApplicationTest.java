package io.github.antoniojuniorr.picpaysimplificadobackend.e2e;

import io.github.antoniojuniorr.picpaysimplificadobackend.transacao.Transacao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EmbeddedKafka(partitions = 1, brokerProperties = {
        "listeners=PLAINTEXT://localhost:9092", "port=9092"})
class PicpaySimplificadoBackendApplicationTest {

    @Autowired
    private WebTestClient webClient;

    @Test
    void testCriarTransacaoSucesso() {
        var transacao = new Transacao(null, 1L, 2L, new BigDecimal(1000), null);

        var postResponse = webClient
                .post()
                .uri("/transacao")
                .bodyValue(transacao)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Transacao.class)
                .value(t -> assertNotNull(t.id()))
                .value(t -> assertEquals(transacao.pagador(), t.pagador()))
                .value(t -> assertEquals(transacao.recebedor(), t.recebedor()))
                .value(t -> assertEquals(transacao.valor(), t.valor()))
                .value(t -> assertNotNull(t.criadoEm()))
                .returnResult();

        webClient
                .get()
                .uri("/transacao")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(Transacao.class)
                .hasSize(1)
                .returnResult();
    }

    @ParameterizedTest
    @MethodSource("providenciarTransacaoInvalida")
    void testCriarTransacaoInvalida(Transacao transacao) {
        webClient
                .post()
                .uri("/transacao")
                .bodyValue(transacao)
                .exchange()
                .expectStatus().is4xxClientError();
    }

    private static Stream<Arguments> providenciarTransacaoInvalida() {
        var transactionLojista = new Transacao(null, 2L, 1L, new BigDecimal(1000), null);
        var trnsacaoSaldoInsuficiente = new Transacao(null, 1L, 2L, new BigDecimal(1001), null);
        var transacaoPagadorIgualRecebedor = new Transacao(null, 1L, 1L, new BigDecimal(1000), null);
        var transacaoRecebedorNaoExiste = new Transacao(null, 1L, 11L, new BigDecimal(1000), null);

        return Stream.of(
                Arguments.of(transactionLojista),
                Arguments.of(trnsacaoSaldoInsuficiente),
                Arguments.of(transacaoPagadorIgualRecebedor),
                Arguments.of(transacaoRecebedorNaoExiste));
    }
}