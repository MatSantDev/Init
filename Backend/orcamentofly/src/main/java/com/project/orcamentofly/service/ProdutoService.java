package com.project.orcamentofly.service;

import java.sql.SQLException;
import java.util.List;

import com.project.orcamentofly.dao.ProdutoDAO;
import com.project.orcamentofly.model.Produto;

public class ProdutoService {

    private ProdutoDAO dao;

    public ProdutoService() {
        dao = new ProdutoDAO();
    }

    public List<Produto> consultarTodos() throws SQLException, ClassNotFoundException {
        return dao.consultarTodos();
    }

    public Produto consultarPrimeiro() throws SQLException, ClassNotFoundException {
        return dao.consultarPrimeiro();
    }
}
