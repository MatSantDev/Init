package com.project.orcamentofly.service;

import com.project.orcamentofly.dao.ClienteDAO;
import com.project.orcamentofly.model.Cliente;
import com.project.orcamentofly.model.builder.ClienteBuilder;

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
        Cliente cliente = new ClienteBuilder().comId(id).constroi();
        return dao.consultarById(cliente);
    }

    public void inserir(Cliente cliente) {
        dao.inserir(cliente);
    }

    public void atualizar(Cliente cliente) {
        dao.atualizar(cliente);
    }

    public void deletar(int id) {
        dao.deletar(new ClienteBuilder().comId(id).constroi());
    }
}
