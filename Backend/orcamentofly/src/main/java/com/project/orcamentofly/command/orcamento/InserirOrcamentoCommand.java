package com.project.orcamentofly.command.orcamento;

import com.project.orcamentofly.command.Command;
import com.project.orcamentofly.model.Orcamento;
import com.project.orcamentofly.service.OrcamentoService;

public class InserirOrcamentoCommand implements Command {

    private final OrcamentoService service;
    private final Orcamento orcamento;

    public InserirOrcamentoCommand(OrcamentoService service, Orcamento orcamento) {
        this.service = service;
        this.orcamento = orcamento;
    }

    @Override
    public void executar() {
        service.inserir(orcamento);
    }
}
