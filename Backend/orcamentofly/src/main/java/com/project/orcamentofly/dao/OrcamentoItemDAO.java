package com.project.orcamentofly.dao;

import com.project.orcamentofly.exception.BdException;
import com.project.orcamentofly.model.Orcamento;
import com.project.orcamentofly.model.OrcamentoItem;
import com.project.orcamentofly.model.Produto;
import com.project.orcamentofly.model.Servico;
import com.project.orcamentofly.model.enums.TipoOrcamentoItem;
import com.project.orcamentofly.util.FabricaConexao;
import com.project.orcamentofly.model.dto.MicroServicoDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrcamentoItemDAO implements GenericDAO<OrcamentoItem>{

    public List<OrcamentoItem> consultarTodosByOrcamento(Orcamento obj) {
        try(Connection conn = getConnection()){
            String sql = "select oi.*, p.nome as produto_nome, s.nome as servico_nome from orcamento_item oi " +
                    "LEFT JOIN produtos p ON oi.produto_id = p.id " +
                    "LEFT JOIN servicos s ON oi.servico_id = s.id " +
                    "WHERE oi.orcamento_id = ?";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, obj.getId());
            ResultSet rs = statement.executeQuery();

            List<OrcamentoItem> list = new ArrayList<>();
            while (rs.next()){
                OrcamentoItem item = getar(rs);
                item.setMicroServicos(carregarMicroServicos(conn, item.getId()));
                list.add(item);
            }

            return list;
        } catch (SQLException | ClassNotFoundException e) {
            throw new BdException(e.getMessage());
        }
    }

    public List<OrcamentoItem> consultarTodosByOrcamentoId(Orcamento obj) {
        try(Connection conn = getConnection()){
            String sql = "select oi.*, p.nome as produto_nome, s.nome as servico_nome from orcamento_item oi " +
                    "LEFT JOIN produtos p ON oi.produto_id = p.id " +
                    "LEFT JOIN servicos s ON oi.servico_id = s.id " +
                    "WHERE oi.orcamento_id = ?";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, obj.getId());

            ResultSet rs = statement.executeQuery();

            List<OrcamentoItem> list = new ArrayList<>();
            while (rs.next()){
                OrcamentoItem item = getar(rs);
                item.setMicroServicos(carregarMicroServicos(conn, item.getId()));
                list.add(item);
            }

            return list;
        } catch (SQLException | ClassNotFoundException e) {
            throw new BdException(e.getMessage());
        }
    }

    @Override
    public OrcamentoItem consultarById(OrcamentoItem obj) {
        try(Connection conn = getConnection()){
            String sql = "SELECT oi.*, p.nome as produto_nome, s.nome as servico_nome " +
                    "FROM orcamento_item oi " +
                    "LEFT JOIN produtos p ON oi.produto_id = p.id " +
                    "LEFT JOIN servicos s ON oi.servico_id = s.id " +
                    "WHERE oi.id = ?";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, obj.getId());
            ResultSet rs = statement.executeQuery();
            OrcamentoItem item = new OrcamentoItem();

            if(rs.next()){
                item = getar(rs);
                item.setMicroServicos(carregarMicroServicos(conn, item.getId()));
            }

            return item;
        } catch (SQLException | ClassNotFoundException e) {
            throw new BdException(e.getMessage());
        }
    }

    @Override
    public List<OrcamentoItem> consultarTodos() {
        try(Connection conn = getConnection()){
            String sql = "select * from orcamento_item";
            PreparedStatement statement = conn.prepareStatement(sql);

            ResultSet rs = statement.executeQuery();

            List<OrcamentoItem> list = new ArrayList<>();

            while(rs.next()){
                OrcamentoItem item = getar(rs);
                item.setMicroServicos(carregarMicroServicos(conn, item.getId()));
                list.add(item);
            }

            return list;
        } catch (SQLException | ClassNotFoundException e) {
            throw new BdException(e.getMessage());
        }
    }

    @Override
    public void inserir(OrcamentoItem obj) {
        try(Connection conn = getConnection()){
            String sql = "INSERT INTO orcamento_item (orcamento_id, descricao, tipoOrcamentoItem, quantidade, valorUnitario, subtotal, produto_id, servico_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setInt(1, obj.getOrcamento().getId());
            statement.setString(2, obj.getDescricao());
            statement.setString(3, obj.getTipoOrcamentoItem().name());
            statement.setInt(4, obj.getQuantidade());
            statement.setDouble(5, obj.getValorUnitario());
            statement.setDouble(6, obj.getSubtotal());

            if (obj.getProduto() != null) {
                statement.setInt(7, obj.getProduto().getId());
            } else {
                statement.setNull(7, Types.INTEGER);
            }

            if (obj.getServico() != null) {
                statement.setInt(8, obj.getServico().getId());
            } else {
                statement.setNull(8, Types.INTEGER);
            }

            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()){
                obj.setId(rs.getInt(1));
            }

            inserirMicroServicosAssociados(conn, obj.getId(), obj.getMicroServicos());

        } catch (SQLException | ClassNotFoundException e) {
            throw new BdException(e.getMessage());
        }
    }

    @Override
    public void atualizar(OrcamentoItem obj) {
        try(Connection conn = getConnection()){

            String sql = "UPDATE orcamento_item SET descricao = ?, tipoOrcamentoItem = ?, quantidade = ?, valorUnitario = ?, subtotal = ?, produto_id = ?, servico_id = ? " +
                    "WHERE id = ? AND orcamento_id = ?";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, obj.getDescricao());
            statement.setString(2, obj.getTipoOrcamentoItem().name());
            statement.setInt(3, obj.getQuantidade());
            statement.setDouble(4, obj.getValorUnitario());
            statement.setDouble(5, obj.getSubtotal());

            if (obj.getProduto() != null) {
                statement.setInt(6, obj.getProduto().getId());
            } else {
                statement.setNull(6, Types.INTEGER);
            }

            if (obj.getServico() != null) {
                statement.setInt(7, obj.getServico().getId());
            } else {
                statement.setNull(7, Types.INTEGER);
            }

            statement.setInt(8, obj.getId());
            statement.setInt(9, obj.getOrcamento().getId());

            statement.executeUpdate();

            // atualizar microservicos associados: simplifica removendo os antigos e inserindo os novos
            // primeiro remover
            String deleteSql = "DELETE FROM orcamento_item_microservico WHERE orcamento_item_id = ?";
            try (PreparedStatement delStmt = conn.prepareStatement(deleteSql)){
                delStmt.setInt(1, obj.getId());
                delStmt.executeUpdate();
            }
            // inserir novos
            inserirMicroServicosAssociados(conn, obj.getId(), obj.getMicroServicos());

        } catch (SQLException | ClassNotFoundException e) {
            throw new BdException(e.getMessage());
        }
    }

    @Override
    public void deletar(OrcamentoItem obj) {
        try(Connection conn = getConnection()){
            String sql = "DELETE FROM orcamento_item WHERE id = ?";

            // remover microservicos associados antes de deletar o item
            String deleteSql = "DELETE FROM orcamento_item_microservico WHERE orcamento_item_id = ?";
            try (PreparedStatement delStmt = conn.prepareStatement(deleteSql)){
                delStmt.setInt(1, obj.getId());
                delStmt.executeUpdate();
            }

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, obj.getId());

            statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new BdException(e.getMessage());
        }
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

    private void inserirMicroServicosAssociados(Connection conn, int orcamentoItemId, List<MicroServicoDTO> microServicos) throws SQLException {
        if (microServicos == null || microServicos.isEmpty()) return;
        String insertSql = "INSERT INTO orcamento_item_microservico (orcamento_item_id, tipo) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(insertSql)){
            for (MicroServicoDTO dto : microServicos){
                if (dto == null || dto.getTipo() == null) continue;
                stmt.setInt(1, orcamentoItemId);
                stmt.setString(2, dto.getTipo().toUpperCase().trim());
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    private List<MicroServicoDTO> carregarMicroServicos(Connection conn, int orcamentoItemId) throws SQLException {
        String sql = "SELECT tipo FROM orcamento_item_microservico WHERE orcamento_item_id = ? ORDER BY id";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, orcamentoItemId);
            ResultSet rs = stmt.executeQuery();
            List<MicroServicoDTO> list = new ArrayList<>();
            while (rs.next()){
                MicroServicoDTO dto = new MicroServicoDTO();
                dto.setTipo(rs.getString("tipo"));
                list.add(dto);
            }
            return list;
        }
    }

    @Override
    public Connection getConnection() throws SQLException, ClassNotFoundException {
        return FabricaConexao.getConexao();
    }
}
