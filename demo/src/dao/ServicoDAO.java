package dao;

import Exception.DBException;
import model.Servico;
import util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServicoDAO {

    public void inserir(Servico servico) throws SQLException {
        String sql = "INSERT INTO servico (nome, descricao, valorUnitario) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, servico.getNome());
            statement.setString(2, servico.getDescricao());
            statement.setDouble(3, servico.getValorUnitario());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
    }

    public void atualizar(Servico servico) throws SQLException {
        String sql = "UPDATE servico SET nome = ?, descricao = ?, valor_unitario = ? WHERE id_servico = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, servico.getNome());
            statement.setString(2, servico.getDescricao());
            statement.setDouble(3, servico.getValorUnitario());
            statement.setInt(4, servico.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
    }

    public void excluir(Integer id) throws SQLException {
        String sql = "DELETE FROM servico WHERE id_servico = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
    }

    public Servico buscarPorId(Integer id) throws SQLException {
        String sql = "SELECT * FROM servico WHERE id_servico = ?";
        Servico servico = null;
        ResultSet rs = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, id);

            if (rs.next()) {
                servico = new Servico();
                servico.setId(rs.getInt("id"));
                servico.setNome(rs.getString("nome"));
                servico.setDescricao(rs.getString("descricao"));
                servico.setValorUnitario(rs.getDouble("valorUnitario"));
            }

        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }

        return servico;
    }

    public List<Servico> listarTodos() throws SQLException {
        String sql = "SELECT * FROM servico";
        List<Servico> itens = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                Servico servico = new Servico();
                servico.setId(rs.getInt("id"));
                servico.setNome(rs.getString("nome"));
                servico.setDescricao(rs.getString("descricao"));
                servico.setValorUnitario(rs.getDouble("valorUnitario"));
                itens.add(servico);
            }
        }
        return itens;
    }
}