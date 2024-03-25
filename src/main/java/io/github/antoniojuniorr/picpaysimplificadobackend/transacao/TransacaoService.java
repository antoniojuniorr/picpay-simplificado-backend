package io.github.antoniojuniorr.picpaysimplificadobackend.transacao;

import io.github.antoniojuniorr.picpaysimplificadobackend.autorizacao.AutorizacaoService;
import io.github.antoniojuniorr.picpaysimplificadobackend.carteira.Carteira;
import io.github.antoniojuniorr.picpaysimplificadobackend.carteira.CarteiraRepository;
import io.github.antoniojuniorr.picpaysimplificadobackend.notificacao.NotificacaoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransacaoService {

    private final CarteiraRepository carteiraRepository;
    private final TransacaoRepository transacaoRepository;
    private final AutorizacaoService autorizacaoService;
    private final NotificacaoService notificacaoService;

    public TransacaoService(CarteiraRepository carteiraRepository, TransacaoRepository transacaoRepository, AutorizacaoService autorizacaoService, NotificacaoService notificacaoService) {
        this.carteiraRepository = carteiraRepository;
        this.transacaoRepository = transacaoRepository;
        this.autorizacaoService = autorizacaoService;
        this.notificacaoService = notificacaoService;
    }

    @Transactional
    public Transacao criar(Transacao transacao) {
        validar(transacao);

        var novaTransacao = transacaoRepository.save(transacao);

        var carteiraPagador = carteiraRepository.findById(transacao.pagador()).get();
        var carteiraRecebedor = carteiraRepository.findById(transacao.recebedor()).get();
        carteiraRepository.save(carteiraPagador.debitar(transacao.valor()));
        carteiraRepository.save(carteiraRecebedor.credito(transacao.valor()));

        autorizacaoService.autorizar(transacao);
        notificacaoService.notificar(transacao);

        return novaTransacao;
    }

    /*
     * A Transacao valida se:
     * - O pagador tem carteira comum
     * - O pagador tem saldo suficiente
     * - O pagador nao pode ser o recebedor
     */
    private void validar(Transacao transacao) {
        if (!transacaoValida(transacao)) {
            throw new TransacaoInvalidaException("Transacao invalida - %s".formatted(transacao));
        }
    }

    private boolean transacaoValida(Transacao transacao) {
        Carteira pagador = carteiraRepository.findById(transacao.pagador())
                .orElseThrow(() -> new TransacaoInvalidaException("Transacao invalida - %s".formatted(transacao)));
        Carteira recebedor = carteiraRepository.findById(transacao.recebedor())
                .orElseThrow(() -> new TransacaoInvalidaException("Transacao invalida - %s".formatted(transacao)));

        if (pagador.tipo() == TipoTransacao.COMUM.getValor() && pagador.saldo().compareTo(transacao.valor()) >= 0 &&
                !pagador.id().equals(recebedor.id())) {
            return true;
        }
        return false;
    }

    public List<Transacao> listar() {
        return transacaoRepository.findAll();
    }
}
