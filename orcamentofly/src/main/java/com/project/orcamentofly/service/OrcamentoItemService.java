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

    public List<OrcamentoItem> consultarTodosByOrcamento(Orcamento orcamento) throws SQLException, ClassNotFoundException {
        try (Connection conn = FabricaConexao.getConexao()) {
            return dao.consultarTodosByOrcamento(orcamento, conn);
        }
    }

    public List<OrcamentoItem> consultarTodosByOrcamentoId(int orcamentoId) throws SQLException, ClassNotFoundException {
        try (Connection conn = FabricaConexao.getConexao()) {
            return dao.consultarTodosByOrcamentoId(orcamentoId, conn);
        }
    }

    public OrcamentoItem consultarById(int id) throws SQLException, ClassNotFoundException {
        try (Connection conn = FabricaConexao.getConexao()) {
            return dao.consultarById(id, conn);
        }
    }

    public void inserir(OrcamentoItem item) throws SQLException, ClassNotFoundException {
        validarOrcamentoItem(item);
        item.calcularSubtotal();
        try (Connection conn = FabricaConexao.getConexao()) {
            conn.setAutoCommit(false);
            try {
                dao.inserir(item, conn);
                ProdutoDAO produtoDAO = new ProdutoDAO();
                produtoDAO.atualizarEstoque(item.getProduto().getId(), -item.getQuantidade(), conn);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    public void atualizar(OrcamentoItem item) throws SQLException, ClassNotFoundException {
        validarOrcamentoItem(item);
        item.calcularSubtotal();
        try (Connection conn = FabricaConexao.getConexao()) {
            dao.atualizar(item, conn);
        }
    }

    public void deletar(int id, int orcamentoId) throws SQLException, ClassNotFoundException {
        try (Connection conn = FabricaConexao.getConexao()) {
            dao.deletar(id, orcamentoId, conn);
        }
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
