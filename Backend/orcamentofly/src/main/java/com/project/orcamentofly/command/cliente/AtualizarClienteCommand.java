package com.project.orcamentofly.command.cliente;

import com.project.orcamentofly.command.Command;
import com.project.orcamentofly.model.Cliente;
import com.project.orcamentofly.service.ClienteService;

public class AtualizarClienteCommand implements Command {

    private final ClienteService service;
    private final Cliente cliente;

    public AtualizarClienteCommand(ClienteService service, Cliente cliente) {
        this.service = service;
        this.cliente = cliente;
    }

    @Override
    public void executar() {
        service.atualizar(cliente);
    }

}

