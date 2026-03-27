package com.project.orcamentofly.service;

import com.project.orcamentofly.dao.ProdutoDAO;
import com.project.orcamentofly.model.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
