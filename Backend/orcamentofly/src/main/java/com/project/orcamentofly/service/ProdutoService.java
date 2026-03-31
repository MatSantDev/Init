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

    public List<Produto> consultarTodos() {
        return dao.consultarTodos();
    }

    public Produto consultarById(int id) {
        return dao.consultarById(id);
    }

    public void inserir(Produto produto) {
        dao.inserir(produto);
    }

    public void atualizar(Produto produto) {
        dao.atualizar(produto);
    }

    public void deletar(Produto produto) {
        dao.deletar(produto);
    }
}
