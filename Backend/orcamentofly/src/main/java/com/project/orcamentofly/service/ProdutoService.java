package com.project.orcamentofly.service;

import com.project.orcamentofly.dao.ProdutoDAO;
import com.project.orcamentofly.model.Produto;

import java.sql.SQLException;
import java.util.List;

public class ProdutoService {

    private ProdutoDAO dao;

    public ProdutoService() {
        dao = new ProdutoDAO();
    }

    public List<Produto> consultarTodos() throws SQLException, ClassNotFoundException {
        return dao.consultarTodos();
    }

    public Produto consultarById(int id) throws SQLException, ClassNotFoundException {
        return dao.consultarById(id);
    }

    public void inserir(Produto produto) throws SQLException, ClassNotFoundException {
        dao.inserir(produto);
    }

    public void atualizar(Produto produto) throws SQLException, ClassNotFoundException {
        dao.atualizar(produto);
    }

    public void deletar(Produto produto) throws SQLException, ClassNotFoundException {
        dao.deletar(produto);
    }
}
