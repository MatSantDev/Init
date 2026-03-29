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

    public List<Servico> consultarTodos() throws SQLException, ClassNotFoundException {
        return dao.consultarTodos();
    }

    public Servico consultarById(int id) throws SQLException, ClassNotFoundException {
        return dao.consultarById(id);
    }

    public void inserir(Servico servico) throws SQLException, ClassNotFoundException {
        dao.inserir(servico);
    }

    public void atualizar(Servico servico) throws SQLException, ClassNotFoundException {
        dao.atualizar(servico);
    }

    public void deletar(Servico servico) throws SQLException, ClassNotFoundException {
        dao.deletar(servico);
    }
}
