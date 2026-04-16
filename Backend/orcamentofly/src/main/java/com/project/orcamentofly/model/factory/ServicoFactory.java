package com.project.orcamentofly.model.factory;

import com.project.orcamentofly.model.Servico;

public class ServicoFactory {

    public static Servico criarServico(String nome, String descricao, double valorUnitario) {
        return new Servico(nome, descricao, valorUnitario);
    }

    public static Servico criarServico(int id, String nome, String descricao, double valorUnitario) {
        Servico servico = criarServico(nome, descricao, valorUnitario);
        servico.setId(id);
        return servico;
    }
}

