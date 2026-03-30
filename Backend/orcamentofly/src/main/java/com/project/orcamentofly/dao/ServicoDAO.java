package com.project.orcamentofly.dao;

import com.project.orcamentofly.model.Servico;
import com.project.orcamentofly.util.FabricaConexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServicoDAO {

    public List<Servico> consultarTodos() throws ClassNotFoundException, SQLException {

        Connection conn = FabricaConexao.getConexao();

        String sql = "select * from servicos";
        PreparedStatement statement = conn.prepareStatement(sql);

        ResultSet rs = statement.executeQuery();

        List<Servico> list = new ArrayList<>();

        while(rs.next()){
            Servico servico = getar(rs);
            list.add(servico);
        }

        conn.close();
        return list;
    }

    public Servico consultarById(int id) throws ClassNotFoundException, SQLException {

        Connection conn = FabricaConexao.getConexao();
        String sql = "select * from servicos where id = ?";

        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, id);

        ResultSet rs = statement.executeQuery();

        Servico servico = new Servico();

        if(rs.next()){
            servico = getar(rs);
        }

        conn.close();
        return servico;
    }

    public void inserir(Servico servico) throws SQLException, ClassNotFoundException {

        Connection conn = FabricaConexao.getConexao();

        String sql = "insert into servicos (nome, descricao, valorUnitario) VALUE (?, ?, ?)";

        PreparedStatement statement = conn.prepareStatement(sql);

        statement.setString(1, servico.getNome());
        statement.setString(2, servico.getDescricao());
        statement.setDouble(3, servico.getValorUnitario());

        statement.executeUpdate();

        conn.close();
    }

    public void deletar(Servico servico) throws SQLException, ClassNotFoundException {

        Connection conn = FabricaConexao.getConexao();

        String sql = "delete from servicos where id = ?";

        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, servico.getId());
        statement.execute();

        conn.close();
    }

    public void atualizar(Servico servico) throws SQLException, ClassNotFoundException {

        Connection conn = FabricaConexao.getConexao();

        String sql = "update servicos set nome = ?, descricao = ?, valorUnitario = ? where id = ?";

        PreparedStatement statement = conn.prepareStatement(sql);

        statement.setString(1, servico.getNome());
        statement.setString(2, servico.getDescricao());
        statement.setDouble(3, servico.getValorUnitario());
        statement.setInt(4, servico.getId());

        statement.execute();

        conn.close();
    }

    private Servico getar(ResultSet rs) throws SQLException {
        Servico servico = new Servico();

        servico.setId(rs.getInt("id"));
        servico.setNome(rs.getString("nome"));
        servico.setDescricao(rs.getString("descricao"));
        servico.setValorUnitario(rs.getDouble("valorUnitario"));

        return servico;
    }
}
