package com.project.orcamentofly.dao;

import com.project.orcamentofly.model.Orcamento;
import com.project.orcamentofly.model.OrcamentoItem;
import com.project.orcamentofly.model.Produto;
import com.project.orcamentofly.model.Servico;
import com.project.orcamentofly.model.enums.TipoOrcamentoItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrcamentoItemDAO {

    public List<OrcamentoItem> consultarTodosByOrcamento(Orcamento orcamento, Connection conn) throws SQLException, ClassNotFoundException {

        String sql = "select oi.*, p.nome as produto_nome, s.nome as servico_nome from orcamento_item oi " +
                "LEFT JOIN produtos p ON oi.produto_id = p.id " +
                "LEFT JOIN servicos s ON oi.servico_id = s.id " +
                "WHERE oi.orcamento_id = ?";

        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, orcamento.getId());
        ResultSet rs = statement.executeQuery();

        List<OrcamentoItem> list = new ArrayList<>();
        while (rs.next()){
            list.add(getar(rs));
        }

        return list;
    }

    public List<OrcamentoItem> consultarTodosByOrcamentoId(int orcamentoId, Connection conn) throws SQLException, ClassNotFoundException {

        String sql = "select oi.*, p.nome as produto_nome, s.nome as servico_nome from orcamento_item oi " +
                "LEFT JOIN produtos p ON oi.produto_id = p.id " +
                "LEFT JOIN servicos s ON oi.servico_id = s.id " +
                "WHERE oi.orcamento_id = ?";

        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, orcamentoId);

        ResultSet rs = statement.executeQuery();

        List<OrcamentoItem> list = new ArrayList<>();
        while (rs.next()){
            list.add(getar(rs));
        }

        return list;
    }

    public OrcamentoItem consultarById(int id, Connection conn) throws SQLException, ClassNotFoundException {

        String sql = "SELECT oi.*, p.nome as produto_nome, s.nome as servico_nome " +
                "FROM orcamento_item oi " +
                "LEFT JOIN produtos p ON oi.produto_id = p.id " +
                "LEFT JOIN servicos s ON oi.servico_id = s.id " +
                "WHERE oi.id = ?";

        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet rs = statement.executeQuery();
        OrcamentoItem item = new OrcamentoItem();

        if(rs.next()){
            item = getar(rs);
        }

        return item;
    }

    public OrcamentoItem inserir(OrcamentoItem item, Connection conn) throws SQLException, ClassNotFoundException {

        String sql = "INSERT INTO orcamento_item (orcamento_id, descricao, tipoOrcamentoItem, quantidade, valorUnitario, subtotal, produto_id, servico_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = conn.prepareStatement(sql);

        statement.setInt(1, item.getOrcamento().getId());
        statement.setString(2, item.getDescricao());
        statement.setString(3, item.getTipoOrcamentoItem().name());
        statement.setInt(4, item.getQuantidade());
        statement.setDouble(5, item.getValorUnitario());
        statement.setDouble(6, item.getSubtotal());

        if (item.getProduto() != null) {
            statement.setInt(7, item.getProduto().getId());
        } else {
            statement.setNull(7, Types.INTEGER);
        }

        if (item.getServico() != null) {
            statement.setInt(8, item.getServico().getId());
        } else {
            statement.setNull(8, Types.INTEGER);
        }

        statement.executeUpdate();

        ResultSet rs = statement.getGeneratedKeys();
        if (rs.next()){
            item.setId(rs.getInt(1));
        }

        conn.close();
        return item;
    }

    public void atualizar(OrcamentoItem item, Connection conn) throws SQLException, ClassNotFoundException {

        String sql = "UPDATE orcamento_item SET descricao = ?, tipoOrcamentoItem = ?, quantidade = ?, valorUnitario = ?, subtotal = ?, produto_id = ?, servico_id = ? " +
                "WHERE id = ? AND orcamento_id = ?";

        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, item.getDescricao());
        statement.setString(2, item.getTipoOrcamentoItem().name());
        statement.setInt(3, item.getQuantidade());
        statement.setDouble(4, item.getValorUnitario());
        statement.setDouble(5, item.getSubtotal());

        if (item.getProduto() != null) {
            statement.setInt(6, item.getProduto().getId());
        } else {
            statement.setNull(6, Types.INTEGER);
        }

        if (item.getServico() != null) {
            statement.setInt(7, item.getServico().getId());
        } else {
            statement.setNull(7, Types.INTEGER);
        }

        statement.setInt(8, item.getId());
        statement.setInt(9, item.getOrcamento().getId());

        statement.executeUpdate();
        conn.close();
    }

    public void deletar(int id, int orcamentoId, Connection conn) throws SQLException, ClassNotFoundException {

        String sql = "DELETE FROM orcamento_item WHERE id = ? AND orcamento_id = ?";

        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, id);
        statement.setInt(2, orcamentoId);

        statement.executeUpdate();
        conn.close();
    }


    private OrcamentoItem getar(ResultSet rs) throws SQLException {
        OrcamentoItem item = new OrcamentoItem();

        item.setId(rs.getInt("id"));
        item.setDescricao(rs.getString("descricao"));
        item.setTipoOrcamentoItem(TipoOrcamentoItem.valueOf(rs.getString("tipoOrcamentoItem")));
        item.setQuantidade(rs.getInt("quantidade"));
        item.setValorUnitario(rs.getDouble("valorUnitario"));
        item.setSubtotal(rs.getDouble("subtotal"));

        Orcamento orcamento = new Orcamento();
        orcamento.setId(rs.getInt("orcamento_id"));
        item.setOrcamento(orcamento);

        int produtoId = rs.getInt("produto_id");

        if (!rs.wasNull()) {
            Produto produto = new Produto();
            produto.setId(produtoId);
            produto.setNome(rs.getString("produto_nome"));
            item.setProduto(produto);
        }

        int servicoId = rs.getInt("servico_id");
        if (!rs.wasNull()) {
            Servico servico = new Servico();
            servico.setId(servicoId);
            servico.setNome(rs.getString("servico_nome"));
            item.setServico(servico);
        }

        return item;
    }
}
