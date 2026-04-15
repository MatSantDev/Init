package com.project.orcamentofly.model.factory;

import com.project.orcamentofly.model.Servico;

/**
 * Factory Pattern - Fábrica para criar instâncias de Serviço
 * Responsável pela criação centralizada de objetos Serviço
 */
public class ServicoFactory {

    public static Servico criarServico(String nome, String descricao, double valorUnitario) {
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("Nome do serviço não pode ser vazio");
        }
        if (valorUnitario <= 0) {
            throw new IllegalArgumentException("Valor unitário deve ser maior que zero");
        }
        return new Servico(nome, descricao, valorUnitario);
    }

    public static Servico criarServico(int id, String nome, String descricao, double valorUnitario) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID do serviço deve ser maior que zero");
        }
        Servico servico = criarServico(nome, descricao, valorUnitario);
        servico.setId(id);
        return servico;
    }
}

