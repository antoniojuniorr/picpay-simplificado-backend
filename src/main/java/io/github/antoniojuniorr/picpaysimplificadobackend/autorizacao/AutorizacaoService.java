package io.github.antoniojuniorr.picpaysimplificadobackend.autorizacao;

import io.github.antoniojuniorr.picpaysimplificadobackend.transacao.Transacao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class AutorizacaoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AutorizacaoService.class);
    private RestClient restClient;

    public AutorizacaoService(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("https://run.mocky.io/v3/5794d450-d2e2-4412-8131-73d0293ac1cc")
                .build();
    }

    public void autorizar(Transacao transacao) {
        LOGGER.info("Autorizando transacao: {}", transacao);

        var response = restClient.get().retrieve().toEntity(Autorizacao.class);

        if (response.getStatusCode().isError() || !response.getBody().isAutorizado()) {
            throw new TransacaoNaoAutorizadaException("Transacao nao autorizada");
        }

        LOGGER.info("Transacao autorizada: {}", transacao);
    }
}
