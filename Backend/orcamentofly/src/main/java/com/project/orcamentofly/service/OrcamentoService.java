package com.project.orcamentofly.service;

import com.project.orcamentofly.dao.OrcamentoDAO;
import com.project.orcamentofly.exception.BadRequestException;
import com.project.orcamentofly.exception.ResourceNotFoundException;
import com.project.orcamentofly.model.Orcamento;
import com.project.orcamentofly.model.enums.StatusOrcamento;
import com.project.orcamentofly.model.factory.OrcamentoFactory;

import java.util.List;

public class OrcamentoService {

    private final OrcamentoDAO dao;

    public OrcamentoService() {
        this.dao = new OrcamentoDAO();
    }

    public List<Orcamento> consultarTodos() {
        return dao.consultarTodos();
    }

    public Orcamento consultarById(int id) {
        Orcamento orcamento = new Orcamento();
        orcamento.setId(id);

        Orcamento orcamentoEncontrado = dao.consultarById(orcamento);
        if (orcamentoEncontrado == null || orcamentoEncontrado.getId() <= 0) {
            return null;
        }

        return orcamentoEncontrado;
    }

    public void inserir(Orcamento orcamento) {
        validarOrcamento(orcamento, false);

        Orcamento novoOrcamento = OrcamentoFactory.criarOrcamentoPendente(
                orcamento.getCliente(),
                orcamento.getDataOrcamento(),
                orcamento.getObservacao(),
                orcamento.getValorTotal()
        );
        dao.inserir(novoOrcamento);
    }

    public void atualizar(Orcamento orcamento) {
        validarOrcamento(orcamento, true);

        Orcamento existente = consultarById(orcamento.getId());
        if (existente == null) {
            throw new ResourceNotFoundException("Orçamento com ID " + orcamento.getId() + " não encontrado");
        }

        StatusOrcamento status = orcamento.getStatus() != null ? orcamento.getStatus() : existente.getStatus();
        Orcamento orcamentoAtualizado = OrcamentoFactory.criarOrcamento(
                orcamento.getId(),
                orcamento.getCliente(),
                orcamento.getDataOrcamento(),
                orcamento.getObservacao(),
                orcamento.getValorTotal(),
                status
        );
        dao.atualizar(orcamentoAtualizado);
    }

    public void deletar(int id) {
        if (id <= 0) {
            throw new BadRequestException("ID do orçamento inválido");
        }

        Orcamento orcamento = consultarById(id);
        if (orcamento == null) {
            throw new ResourceNotFoundException("Orçamento com ID " + id + " não encontrado");
        }

        dao.deletar(orcamento);
    }

    public void atualizarValorTotal(int id) {
        Orcamento orcamento = consultarById(id);
        if (orcamento == null) {
            throw new ResourceNotFoundException("Orçamento com ID " + id + " não encontrado");
        }

        dao.atualizarValorTotal(orcamento);
    }

    private void validarOrcamento(Orcamento orcamento, boolean validarId) {
        if (orcamento == null) {
            throw new BadRequestException("Orçamento é obrigatório");
        }
        if (validarId && orcamento.getId() <= 0) {
            throw new BadRequestException("ID do orçamento inválido");
        }
        if (orcamento.getCliente() == null || orcamento.getCliente().getId() <= 0) {
            throw new BadRequestException("Cliente do orçamento é obrigatório");
        }
        if (orcamento.getDataOrcamento() == null) {
            throw new BadRequestException("Data do orçamento é obrigatória");
        }
        if (orcamento.getValorTotal() < 0) {
            throw new BadRequestException("Valor total do orçamento não pode ser negativo");
        }
    }
}
