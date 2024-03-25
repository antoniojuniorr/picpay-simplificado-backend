package io.github.antoniojuniorr.picpaysimplificadobackend.test;

import io.github.antoniojuniorr.picpaysimplificadobackend.autorizacao.AutorizacaoService;
import io.github.antoniojuniorr.picpaysimplificadobackend.carteira.Carteira;
import io.github.antoniojuniorr.picpaysimplificadobackend.carteira.CarteiraRepository;
import io.github.antoniojuniorr.picpaysimplificadobackend.notificacao.NotificacaoService;
import io.github.antoniojuniorr.picpaysimplificadobackend.transacao.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransacaoServiceTest {
    @InjectMocks
    private TransacaoService transacaoService;

    @Mock
    private TransacaoRepository transacaoRepository;

    @Mock
    private CarteiraRepository carteiraRepository;

    @Mock
    private AutorizacaoService autorizacaoService;

    @Mock
    private NotificacaoService notificacaoService;

    @Test
    public void testCriarTransacaoSucesso() {
        var transacao = new Transacao(null, 1L, 2L, new BigDecimal(1000), null);
        var recebedor = new Carteira(transacao.recebedor(), null, null, null, null, TipoTransacao.COMUM.getValor(),
                BigDecimal.ZERO, 1L);
        var pagador = new Carteira(transacao.pagador(), null, null, null, null, TipoTransacao.COMUM.getValor(),
                new BigDecimal(1000), 1L);

        when(carteiraRepository.findById(transacao.recebedor())).thenReturn(Optional.of(recebedor));
        when(carteiraRepository.findById(transacao.pagador())).thenReturn(Optional.of(pagador));
        when(transacaoRepository.save(transacao)).thenReturn(transacao);

        var newTransaction = transacaoService.criar(transacao);

        assertEquals(transacao, newTransaction);
    }

    @ParameterizedTest
    @MethodSource("providesInvalidTransactions")
    public void testCriarTransacaoInvalida(Transacao transacao) {
        assertThrows(TransacaoInvalidaException.class,
                () -> transacaoService.criar(transacao));
    }

    private static Stream<Arguments> providesInvalidTransactions() {
        var transacaoLojias = new Transacao(null, 2L, 1L, new BigDecimal(1000), null);
        var transacaoSaldoInsuficiente = new Transacao(null, 1L, 2L, new BigDecimal(1001), null);
        var transacaoPagadorIgualRecebedor = new Transacao(null, 1L, 1L, new BigDecimal(1000), null);
        var transacaoRecebedorNaoExiste = new Transacao(null, 1L, 11L, new BigDecimal(1000), null);
        var transacaoPagadorNaoExiste = new Transacao(null, 11L, 1L, new BigDecimal(1000), null);

        return Stream.of(
                Arguments.of(transacaoLojias),
                Arguments.of(transacaoSaldoInsuficiente),
                Arguments.of(transacaoPagadorIgualRecebedor),
                Arguments.of(transacaoRecebedorNaoExiste),
                Arguments.of(transacaoPagadorNaoExiste));
    }
}
