package com.project.orcamentofly.command.produto;

import com.project.orcamentofly.command.Command;
import com.project.orcamentofly.service.ProdutoService;

public class DeletarProdutoCommand implements Command {

    private final ProdutoService service;
    private final int id;

    public DeletarProdutoCommand(ProdutoService service, int id) {
        this.service = service;
        this.id = id;
    }

    @Override
    public void executar() {
        service.deletar(id);
    }
}
