package com.project.orcamentofly.dao;

import com.project.orcamentofly.model.Produto;
import com.project.orcamentofly.util.FabricaConexao;

import java.util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProdutoDAO {

    public List<Produto> consultarTodos() throws ClassNotFoundException, SQLException {
        Connection con = FabricaConexao.getConexao();
        String SQL = "select * from produtos";
        PreparedStatement comando = con.prepareStatement(SQL);
        ResultSet rs = comando.executeQuery();
        List<Produto> lprod = new ArrayList<Produto>();
        int cont = 0;
        while(rs.next()){
            Produto prod = new Produto();
            prod.setId(rs.getInt("id"));
            prod.setDescricao(rs.getString("descricao"));
            prod.setPreco(rs.getDouble("preco"));
            lprod.add(prod);
        }
        con.close();
        return lprod;
    }
}
