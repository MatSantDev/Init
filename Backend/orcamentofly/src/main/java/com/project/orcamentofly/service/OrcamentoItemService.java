package com.project.orcamentofly.service;

import com.project.orcamentofly.dao.OrcamentoItemDAO;
import com.project.orcamentofly.dao.ProdutoDAO;
import com.project.orcamentofly.model.Orcamento;
import com.project.orcamentofly.model.OrcamentoItem;
import com.project.orcamentofly.model.enums.TipoOrcamentoItem;
import com.project.orcamentofly.util.FabricaConexao;

import java.sql.Connection;
import java.util.*;
import java.sql.SQLException;

public class OrcamentoItemService {

    private OrcamentoItemDAO dao;

    public OrcamentoItemService() {
        this.dao = new OrcamentoItemDAO();
    }

    public List<OrcamentoItem> consultarTodosByOrcamento(Orcamento orcamento) {
        return dao.consultarTodosByOrcamento(orcamento);
    }

    public List<OrcamentoItem> consultarTodosByOrcamentoId(int orcamentoId) {
        return dao.consultarTodosByOrcamentoId(orcamentoId);
    }

    public OrcamentoItem consultarById(int id) {
        return dao.consultarById(id);
    }

    public void inserir(OrcamentoItem item) {
        validarOrcamentoItem(item);
        item.calcularSubtotal();
        dao.inserir(item);
        ProdutoDAO produtoDAO = new ProdutoDAO();
        produtoDAO.atualizarEstoque(item.getProduto().getId(), -item.getQuantidade());
    }

    public void atualizar(OrcamentoItem item) {
        validarOrcamentoItem(item);
        item.calcularSubtotal();
        dao.atualizar(item);
    }

    public void deletar(OrcamentoItem item) {
        dao.deletar(item);
    }

    private void validarOrcamentoItem(OrcamentoItem item) {
        if (item.getQuantidade() <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }
        if (item.getValorUnitario() < 0) {
            throw new IllegalArgumentException("O valor unitário deve ser maior ou igual a zero.");
        }
        if (item.getTipoOrcamentoItem() == TipoOrcamentoItem.PRODUTO) {
            if (item.getProduto() == null || item.getProduto().getId() <= 0) {
                throw new IllegalArgumentException("Para itens do tipo PRODUTO, um produto válido deve ser informado.");
            }
            item.setServico(null);
        } else if (item.getTipoOrcamentoItem() == TipoOrcamentoItem.SERVICO) {
            if (item.getServico() == null || item.getServico().getId() <= 0) {
                throw new IllegalArgumentException("Para itens do tipo SERVICO, um serviço válido deve ser informado.");
            }
            item.setProduto(null);
        }
    }
}
