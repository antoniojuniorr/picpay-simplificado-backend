package io.github.antoniojuniorr.picpaysimplificadobackend.notificacao;

import io.github.antoniojuniorr.picpaysimplificadobackend.transacao.Transacao;
import org.springframework.stereotype.Service;

@Service
public class NotificacaoService {
    private final NotificacaoProducer notificacaoProducer;

    public NotificacaoService(NotificacaoProducer notificacaoProducer) {
        this.notificacaoProducer = notificacaoProducer;
    }

    public void notificar(Transacao transacao) {
        notificacaoProducer.enviarNotificao(transacao);
    }
}
