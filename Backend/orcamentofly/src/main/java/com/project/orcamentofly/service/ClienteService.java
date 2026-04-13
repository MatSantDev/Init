package com.project.orcamentofly.service;

import com.project.orcamentofly.dao.ClienteDAO;
import com.project.orcamentofly.model.Cliente;

import java.util.List;

public class ClienteService {

    private ClienteDAO dao;

    public ClienteService() {
        dao = new ClienteDAO();
    }

    public List<Cliente> consultarTodos() {
        return dao.consultarTodos();
    }

    public Cliente consultarById(int id) {
        return dao.consultarById(id);
    }

    public void inserir(Cliente cliente) {
        dao.inserir(cliente);
    }

    public void atualizar(Cliente cliente) {
        dao.atualizar(cliente);
    }

    public void deletar(Cliente cliente) {
        dao.deletar(cliente);
    }
}
