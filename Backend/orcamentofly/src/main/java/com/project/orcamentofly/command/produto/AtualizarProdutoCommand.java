package com.project.orcamentofly.command.produto;

import com.project.orcamentofly.command.Command;
import com.project.orcamentofly.model.Produto;
import com.project.orcamentofly.service.ProdutoService;

public class AtualizarProdutoCommand implements Command {

    private final ProdutoService service;
    private final Produto produto;

    public AtualizarProdutoCommand(ProdutoService service, Produto produto) {
        this.service = service;
        this.produto = produto;
    }

    @Override
    public void executar() {
        service.atualizar(produto);
    }
}
