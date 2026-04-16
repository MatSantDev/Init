package com.project.orcamentofly.model.factory;

import com.project.orcamentofly.model.Cliente;
import com.project.orcamentofly.model.Orcamento;
import com.project.orcamentofly.model.enums.StatusOrcamento;
import java.time.LocalDate;

public class OrcamentoFactory {

    public static Orcamento criarOrcamento(Cliente cliente, LocalDate dataOrcamento, String observacao, double valorTotal) {
        return new Orcamento(cliente, dataOrcamento, observacao, valorTotal);
    }

    public static Orcamento criarOrcamento(int id, Cliente cliente, LocalDate dataOrcamento, String observacao, double valorTotal, StatusOrcamento status) {
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

