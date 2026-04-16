package com.project.orcamentofly.command.servico;

import com.project.orcamentofly.command.Command;
import com.project.orcamentofly.model.Servico;
import com.project.orcamentofly.service.ServicoService;

public class AtualizarServicoCommand implements Command {

    private final ServicoService service;
    private final Servico servico;

    public AtualizarServicoCommand(ServicoService service, Servico servico) {
        this.service = service;
        this.servico = servico;
    }

    @Override
    public void executar() {
        service.atualizar(servico);
    }
}
