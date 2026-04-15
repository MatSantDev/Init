package com.project.orcamentofly.dao;

import com.project.orcamentofly.exception.BdException;
import com.project.orcamentofly.model.Servico;
import com.project.orcamentofly.util.FabricaConexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServicoDAO implements GenericDAO<Servico>{

    @Override
    public List<Servico> consultarTodos() {
        try(Connection conn = getConnection()){
            String sql = "select * from servicos";
            PreparedStatement statement = conn.prepareStatement(sql);

            ResultSet rs = statement.executeQuery();

            List<Servico> list = new ArrayList<>();

            while(rs.next()){
                Servico servico = getar(rs);
                list.add(servico);
            }

            return list;
        } catch (SQLException | ClassNotFoundException e) {
            throw new BdException(e.getMessage());
        }
    }

    @Override
    public Servico consultarById(Servico obj) {
        try(Connection conn = getConnection()){
            String sql = "select * from servicos where id = ?";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, obj.getId());

            ResultSet rs = statement.executeQuery();

            Servico servico = new Servico();

            if(rs.next()){
                servico = getar(rs);
            }

            return servico;
        } catch (SQLException | ClassNotFoundException e) {
            throw new BdException(e.getMessage());
        }
    }

    @Override
    public void inserir(Servico obj) {
        try(Connection conn = getConnection()){

            String sql = "insert into servicos (nome, descricao, valorUnitario) VALUE (?, ?, ?)";

            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setString(1, obj.getNome());
            statement.setString(2, obj.getDescricao());
            statement.setDouble(3, obj.getValorUnitario());

            statement.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            throw new BdException(e.getMessage());
        }
    }

    @Override
    public void deletar(Servico obj) {
        try(Connection conn = getConnection()){

            String sql = "delete from servicos where id = ?";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, obj.getId());
            statement.execute();

        } catch (SQLException | ClassNotFoundException e) {
            throw new BdException(e.getMessage());
        }
    }

    @Override
    public void atualizar(Servico obj) {
        try(Connection conn = getConnection()){
            String sql = "update servicos set nome = ?, descricao = ?, valorUnitario = ? where id = ?";

            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setString(1, obj.getNome());
            statement.setString(2, obj.getDescricao());
            statement.setDouble(3, obj.getValorUnitario());
            statement.setInt(4, obj.getId());

            statement.execute();
        } catch (SQLException | ClassNotFoundException e) {
            throw new BdException(e.getMessage());
        }
    }

    private Servico getar(ResultSet rs) throws SQLException {
        Servico servico = new Servico();

        servico.setId(rs.getInt("id"));
        servico.setNome(rs.getString("nome"));
        servico.setDescricao(rs.getString("descricao"));
        servico.setValorUnitario(rs.getDouble("valorUnitario"));

        return servico;
    }

    @Override
    public Connection getConnection() throws SQLException, ClassNotFoundException {
        return FabricaConexao.getConexao();
    }
}
