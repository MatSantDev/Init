package com.project.orcamentofly.command;

/**
 * Command Pattern - Interface que define o contrato para todos os comandos
 * Responsável por encapsular requisições como objetos
 */
public interface Command {
    void executar();
    void desfazer();
}

