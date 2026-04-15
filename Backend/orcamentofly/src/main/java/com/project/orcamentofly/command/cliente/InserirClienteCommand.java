package com.project.orcamentofly.command.cliente;

import com.project.orcamentofly.command.Command;
import com.project.orcamentofly.model.Cliente;
import com.project.orcamentofly.service.ClienteService;

/**
 * Comando concreto para inserir um cliente
 */
public class InserirClienteCommand implements Command {
    private final ClienteService service;
    private final Cliente cliente;

    public InserirClienteCommand(ClienteService service, Cliente cliente) {
        this.service = service;
        this.cliente = cliente;
    }

    @Override
    public void executar() {
        service.inserir(cliente);
    }

    @Override
    public void desfazer() {
        // Implementar lógica de desfazer (deletar o cliente inserido)
        if (cliente.getId() > 0) {
            service.deletar(cliente.getId());
        }
    }
}

