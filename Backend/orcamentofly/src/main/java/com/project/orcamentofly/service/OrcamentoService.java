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
        Orcamento orcamento = new Orcamento();
        orcamento.setId(id);
        return dao.consultarById(orcamento);
    }

    public void inserir(Orcamento orcamento) {
        dao.inserir(orcamento);
    }

    public void atualizar(Orcamento orcamento) {
        dao.atualizar(orcamento);
    }

    public void deletar(int id) {
        Orcamento orcamento = new Orcamento();
        orcamento.setId(id);
        dao.deletar(orcamento);
    }
}
