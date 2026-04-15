package com.project.orcamentofly.command.cliente;

import com.project.orcamentofly.command.Command;
import com.project.orcamentofly.model.Cliente;
import com.project.orcamentofly.service.ClienteService;

/**
 * Comando concreto para atualizar um cliente
 */
public class AtualizarClienteCommand implements Command {
    private final ClienteService service;
    private final Cliente cliente;
    private final Cliente clienteAnterior;

    public AtualizarClienteCommand(ClienteService service, Cliente cliente, Cliente clienteAnterior) {
        this.service = service;
        this.cliente = cliente;
        this.clienteAnterior = clienteAnterior;
    }

    @Override
    public void executar() {
        service.atualizar(cliente);
    }

    @Override
    public void desfazer() {
        // Restaurar o estado anterior do cliente
        if (clienteAnterior != null) {
            service.atualizar(clienteAnterior);
        }
    }
}

