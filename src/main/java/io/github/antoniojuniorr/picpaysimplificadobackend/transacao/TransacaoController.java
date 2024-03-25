package io.github.antoniojuniorr.picpaysimplificadobackend.transacao;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("transacao")
public class TransacaoController {

    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @PostMapping
    public Transacao criarTransacao(@RequestBody Transacao transacao) {
        return transacaoService.criar(transacao);
    }

    @GetMapping
    public List<Transacao> listar() {
        return transacaoService.listar();
    }
}
