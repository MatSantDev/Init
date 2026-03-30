package com.project.orcamentofly.service;

import com.project.orcamentofly.dao.OrcamentoDAO;
import com.project.orcamentofly.model.Orcamento;

import java.sql.SQLException;
import java.util.List;

public class OrcamentoService {

    private OrcamentoDAO dao;

    public OrcamentoService() {
        dao = new OrcamentoDAO();
    }

    public List<Orcamento> consultarTodos() throws SQLException, ClassNotFoundException {
        return dao.consultarTodos();
    }

    public Orcamento consultarById(int id) throws SQLException, ClassNotFoundException {
        return dao.consultarById(id);
    }

    public void inserir(Orcamento orcamento) throws SQLException, ClassNotFoundException {
        dao.inserir(orcamento);
    }

    public void atualizar(Orcamento orcamento) throws SQLException, ClassNotFoundException {
        dao.atualizar(orcamento);
    }

    public void deletar(Orcamento orcamento) throws SQLException, ClassNotFoundException {
        dao.deletar(orcamento);
    }
}
