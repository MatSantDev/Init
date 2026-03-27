package dao;

import model.Orcamento;
import util.ConnectionFactory;
import Exception.DBException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrcamentoDAO {

    public void inserir(Orcamento orcamento) {
        String sql = "INSERT INTO orcamento (cliente, dataOrcamento, observacao, valorTotal) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, orcamento.getCliente());
            statement.setDate(2, Date.valueOf(orcamento.getDataOrcamento()));
            statement.setString(3, orcamento.getObservacao());
            statement.setDouble(4, orcamento.getValorTotal());

            statement.executeUpdate();

            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    orcamento.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
    }


    public void atualizar(Orcamento orcamento) {
        String sql = "UPDATE orcamento SET cliente = ?, dataOrcamento = ?, observacao = ?, valorTotal = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, orcamento.getCliente());
            statement.setDate(2, Date.valueOf(orcamento.getDataOrcamento()));
            statement.setString(3, orcamento.getObservacao());
            statement.setDouble(4, orcamento.getValorTotal());
            statement.setInt(5, orcamento.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
    }


    public void excluir(Integer id) {
        String sql = "DELETE FROM orcamento WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
    }


    public Orcamento buscarPorId(Integer id) {
        String sql = "SELECT * FROM orcamento WHERE id = ?";
        Orcamento orcamento = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    orcamento = new Orcamento();
                    orcamento.setId(rs.getInt("id"));
                    orcamento.setCliente(rs.getString("cliente"));
                    orcamento.setDataOrcamento(rs.getDate("dataOrcamento").toLocalDate());
                    orcamento.setObservacao(rs.getString("observacao"));
                    orcamento.setValorTotal(rs.getDouble("valorTotal"));
                }
            }

        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }

        return orcamento;
    }


    public List<Orcamento> listarTodos() {
        String sql = "SELECT * FROM orcamento ORDER BY dataOrcamento DESC";
        List<Orcamento> lista = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                Orcamento orcamento = new Orcamento();
                orcamento.setId(rs.getInt("id"));
                orcamento.setCliente(rs.getString("cliente"));
                orcamento.setDataOrcamento(rs.getDate("dataOrcamento").toLocalDate());
                orcamento.setObservacao(rs.getString("observacao"));
                orcamento.setValorTotal(rs.getDouble("valorTotal"));

                lista.add(orcamento);
            }

        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }

        return lista;
    }
}