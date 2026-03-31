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

    public List<Orcamento> consultarTodos() {
        return dao.consultarTodos();
    }

    public Orcamento consultarById(int id) {
        return dao.consultarById(id);
    }

    public void inserir(Orcamento orcamento) {
        dao.inserir(orcamento);
    }

    public void atualizar(Orcamento orcamento) {
        dao.atualizar(orcamento);
    }

    public void deletar(Orcamento orcamento) {
        dao.deletar(orcamento);
    }
}
