package com.project.orcamentofly.service;

import com.project.orcamentofly.dao.ServicoDAO;
import com.project.orcamentofly.model.Servico;

import java.sql.SQLException;
import java.util.List;

public class ServicoService {

    private ServicoDAO dao;

    public ServicoService() {
        dao = new ServicoDAO();
    }

    public List<Servico> consultarTodos() {
        return dao.consultarTodos();
    }

    public Servico consultarById(int id) {
        return dao.consultarById(id);
    }

    public void inserir(Servico servico) {
        dao.inserir(servico);
    }

    public void atualizar(Servico servico) {
        dao.atualizar(servico);
    }

    public void deletar(Servico servico) {
        dao.deletar(servico);
    }
}
