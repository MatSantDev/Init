package com.project.orcamentofly.command.orcamento;

import com.project.orcamentofly.command.Command;
import com.project.orcamentofly.model.Orcamento;
import com.project.orcamentofly.service.OrcamentoService;

public class AtualizarOrcamentoCommand implements Command {

    private final OrcamentoService service;
    private final Orcamento orcamento;

    public AtualizarOrcamentoCommand(OrcamentoService service, Orcamento orcamento) {
        this.service = service;
        this.orcamento = orcamento;
    }

    @Override
    public void executar() {
        service.atualizar(orcamento);
    }
}
