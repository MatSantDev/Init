package com.project.orcamentofly.command.orcamento;

import com.project.orcamentofly.command.Command;
import com.project.orcamentofly.service.OrcamentoService;

public class DeletarOrcamentoCommand implements Command {

    private final OrcamentoService service;
    private final int id;

    public DeletarOrcamentoCommand(OrcamentoService service, int id) {
        this.service = service;
        this.id = id;
    }

    @Override
    public void executar() {
        service.deletar(id);
    }
}
