package com.project.orcamentofly.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public interface GenericDAO<T> {

    void inserir(T obj);
    void atualizar(T obj);
    void deletar(T obj);
    T consultarById(int id);
    List<T> consultarTodos();

    Connection getConnection() throws SQLException, ClassNotFoundException;
}