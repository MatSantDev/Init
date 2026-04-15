package com.project.orcamentofly.command;

import java.util.Stack;

/**
 * Invocador do Command Pattern
 * Responsável por executar comandos e manter histórico de desfazer
 */
public class CommandInvoker {
    private final Stack<Command> historico;

    public CommandInvoker() {
        this.historico = new Stack<>();
    }

    /**
     * Executa um comando e armazena no histórico
     */
    public void executar(Command comando) {
        if (comando != null) {
            comando.executar();
            historico.push(comando);
        }
    }

    /**
     * Desfaz o último comando executado
     */
    public void desfazer() {
        if (!historico.isEmpty()) {
            Command comando = historico.pop();
            comando.desfazer();
        }
    }

    /**
     * Desfaz os últimos N comandos
     */
    @SuppressWarnings("unused")
    public void desfazerN(int quantidade) {
        for (int i = 0; i < quantidade && !historico.isEmpty(); i++) {
            desfazer();
        }
    }

    /**
     * Limpa o histórico
     */
    @SuppressWarnings("unused")
    public void limparHistorico() {
        historico.clear();
    }

    /**
     * Retorna o tamanho do histórico
     */
    @SuppressWarnings("unused")
    public int getTamanhoHistorico() {
        return historico.size();
    }
}


