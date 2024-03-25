package io.github.antoniojuniorr.picpaysimplificadobackend.notificacao;

import io.github.antoniojuniorr.picpaysimplificadobackend.transacao.Transacao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class NotificacaoConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificacaoConsumer.class);
    private RestClient restClient;

    public NotificacaoConsumer(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("https://run.mocky.io/v3/54dc2cf1-3add-45b5-b5a9-6bf7e7f1f4a6")
                .build();
    }

    @KafkaListener(topics = "notificacao-transacao", groupId = "picpay-simplificado-backend")
    public void receberNotificacao(Transacao transacao) {
        LOGGER.info("Recebendo notificacao: {}...", transacao);

        var response = restClient.get().retrieve().toEntity(Notificacao.class);

        if (response.getStatusCode().isError() || !response.getBody().message()) {
            throw new NotificaoExcepetion("Error ao enviar notificao!");
        }

        LOGGER.info("Notificacao foi recebida: {}", response.getBody());
    }
}
