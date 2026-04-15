package com.project.orcamentofly.model.factory;

import com.project.orcamentofly.model.Cliente;
import com.project.orcamentofly.model.Orcamento;
import com.project.orcamentofly.model.enums.StatusOrcamento;
import java.time.LocalDate;

/**
 * Factory Pattern - Fábrica para criar instâncias de Orçamento
 * Responsável pela criação centralizada de objetos Orçamento
 */
public class OrcamentoFactory {

    public static Orcamento criarOrcamento(Cliente cliente, LocalDate dataOrcamento, String observacao, double valorTotal) {
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não pode ser nulo");
        }
        if (dataOrcamento == null) {
            throw new IllegalArgumentException("Data do orçamento não pode ser nula");
        }
        if (valorTotal <= 0) {
            throw new IllegalArgumentException("Valor total deve ser maior que zero");
        }
        return new Orcamento(cliente, dataOrcamento, observacao, valorTotal);
    }

    public static Orcamento criarOrcamento(int id, Cliente cliente, LocalDate dataOrcamento, String observacao, double valorTotal, StatusOrcamento status) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID do orçamento deve ser maior que zero");
        }
        Orcamento orcamento = criarOrcamento(cliente, dataOrcamento, observacao, valorTotal);
        orcamento.setId(id);
        orcamento.setStatus(status);
        return orcamento;
    }

    public static Orcamento criarOrcamentoPendente(Cliente cliente, LocalDate dataOrcamento, String observacao, double valorTotal) {
        Orcamento orcamento = criarOrcamento(cliente, dataOrcamento, observacao, valorTotal);
        orcamento.setStatus(StatusOrcamento.PENDENTE);
        return orcamento;
    }
}

