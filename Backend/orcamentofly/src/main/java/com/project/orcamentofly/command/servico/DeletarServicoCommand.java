package com.project.orcamentofly.command.servico;

import com.project.orcamentofly.command.Command;
import com.project.orcamentofly.service.ServicoService;

public class DeletarServicoCommand implements Command {

    private final ServicoService service;
    private final int id;

    public DeletarServicoCommand(ServicoService service, int id) {
        this.service = service;
        this.id = id;
    }

    @Override
    public void executar() {
        service.deletar(id);
    }
}
