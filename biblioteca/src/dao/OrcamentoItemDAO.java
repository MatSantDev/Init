package dao;

import Exception.DBException;
import model.Orcamento;
import model.OrcamentoItem;
import model.Produto;
import model.Servico;
import model.enums.TipoOrcamentoItem;
import util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrcamentoItemDAO {

    public void inserir(OrcamentoItem item) {
        String sql = "INSERT INTO orcamentoItem " +
                "(orcamento_id, tipoOrcamentoItem, produto_id, servico_id, quantidade, valorUnitario, subtotal) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            if (item.getOrcamento() != null && item.getOrcamento().getId() != null) {
                statement.setInt(1, item.getOrcamento().getId());
            } else {
                throw new DBException("Orçamento do item não pode ser nulo.");
            }

            statement.setString(2, item.getTipoOrcamentoItem().name().toLowerCase());

            if (item.getProduto() != null && item.getProduto().getId() != null) {
                statement.setInt(3, item.getProduto().getId());
            } else {
                statement.setNull(3, Types.INTEGER);
            }

            if (item.getServico() != null && item.getServico().getId() != null) {
                statement.setInt(4, item.getServico().getId());
            } else {
                statement.setNull(4, Types.INTEGER);
            }

            statement.setInt(5, item.getQuantidade());
            statement.setDouble(6, item.getValorUnitario());
            statement.setDouble(7, item.getSubtotal());

            statement.executeUpdate();

            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    item.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
    }

    public List<OrcamentoItem> listarPorOrcamento(Integer idOrcamento) {
        String sql = "SELECT * FROM orcamentoItem WHERE orcamento_id = ?";
        List<OrcamentoItem> lista = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, idOrcamento);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    OrcamentoItem item = new OrcamentoItem();

                    item.setId(rs.getInt("id"));

                    Orcamento orcamento = new Orcamento();
                    orcamento.setId(rs.getInt("orcamento_id"));
                    item.setOrcamento(orcamento);

                    item.setTipoOrcamentoItem(
                            TipoOrcamentoItem.valueOf(rs.getString("tipoOrcamentoItem").toUpperCase())
                    );

                    Integer produtoId = (Integer) rs.getObject("produto_id");
                    if (produtoId != null) {
                        Produto produto = new Produto();
                        produto.setId(produtoId);
                        item.setProduto(produto);
                    }

                    Integer servicoId = (Integer) rs.getObject("servico_id");
                    if (servicoId != null) {
                        Servico servico = new Servico();
                        servico.setId(servicoId);
                        item.setServico(servico);
                    }

                    item.setQuantidade(rs.getInt("quantidade"));
                    item.setValorUnitario(rs.getDouble("valorUnitario"));
                    item.setSubtotal(rs.getDouble("subtotal"));

                    lista.add(item);
                }
            }

        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }

        return lista;
    }

    public void excluir(Integer id) {
        String sql = "DELETE FROM orcamentoItem WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
    }
}