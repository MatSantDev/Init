package com.project.orcamentofly.command;

import java.util.Stack;

public class CommandInvoker {
    private final Stack<Command> historico;

    public CommandInvoker() {
        this.historico = new Stack<>();
    }

    public void executar(Command comando) {
        if (comando != null) {
            comando.executar();
            historico.push(comando);
        }
    }
}


