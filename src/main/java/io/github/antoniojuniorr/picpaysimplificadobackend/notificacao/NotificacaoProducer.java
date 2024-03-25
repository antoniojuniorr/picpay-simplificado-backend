package io.github.antoniojuniorr.picpaysimplificadobackend.notificacao;

import io.github.antoniojuniorr.picpaysimplificadobackend.transacao.Transacao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificacaoProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificacaoProducer.class);
    private final KafkaTemplate<String, Transacao> kafkaTemplate;

    public NotificacaoProducer(KafkaTemplate<String, Transacao> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void enviarNotificao(Transacao transacao) {
        LOGGER.info("Enviando notificacao: {}...", transacao);

        kafkaTemplate.send("notificacao-transacao", transacao);

        LOGGER.info("Notificacao enviada: {}", transacao);
    }
}
