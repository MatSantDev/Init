package com.project.orcamentofly.dao;

import com.project.orcamentofly.exception.BdException;
import com.project.orcamentofly.model.Cliente;
import com.project.orcamentofly.model.Orcamento;
import com.project.orcamentofly.model.OrcamentoItem;
import com.project.orcamentofly.model.Produto;
import com.project.orcamentofly.model.builder.ClienteBuilder;
import com.project.orcamentofly.model.enums.StatusOrcamento;
import com.project.orcamentofly.model.enums.TipoOrcamentoItem;
import com.project.orcamentofly.util.FabricaConexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrcamentoDAO implements GenericDAO<Orcamento>{

    private OrcamentoItemDAO orcamentoItemDAO;
    private ProdutoDAO produtoDAO;

    public OrcamentoDAO(){
        this.orcamentoItemDAO = new OrcamentoItemDAO();
        this.produtoDAO = new ProdutoDAO();
    }

    @Override
    public List<Orcamento> consultarTodos() {
        try(Connection conn = getConnection()){

            String sql = "select * from orcamentos";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            List<Orcamento> list = new ArrayList<>();
            Orcamento orcamento = new Orcamento();

            while (rs.next()){
                orcamento = getar(rs);
                list.add(orcamento);
            }

            return list;
        } catch (SQLException | ClassNotFoundException e) {
            throw new BdException(e.getMessage());
        }
    }

    @Override
    public Orcamento consultarById(Orcamento obj) {
        try(Connection conn = getConnection()){
            String sql = "select * from orcamentos where id = ?";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, obj.getId());

            ResultSet rs = statement.executeQuery();

            Orcamento orcamento = new Orcamento();

            if(rs.next()){
                orcamento = getar(rs);
            }

            return orcamento;
        } catch (SQLException | ClassNotFoundException e) {
            throw new BdException(e.getMessage());
        }
    }

    @Override
    public void inserir(Orcamento obj) {
        try(Connection conn = getConnection()){

            String sql = "insert into orcamentos (cliente_id, dataOrcamento, observacao, valorTotal, status) VALUE (?, ?, ?, ?, ?)";

            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setInt(1, obj.getCliente().getId());
            statement.setDate(2, Date.valueOf(obj.getDataOrcamento()));
            statement.setString(3, obj.getObservacao());
            statement.setDouble(4, obj.getValorTotal());
            statement.setString(5, obj.getStatus().toString());

            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            if(rs.next()){
                int orcamentoId = rs.getInt(1);
                obj.setId(orcamentoId);
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new BdException(e.getMessage());
        }
    }

    @Override
    public void deletar(Orcamento obj) {
        try(Connection conn = getConnection()){
            conn.setAutoCommit(false);
            
            List<OrcamentoItem> itens = orcamentoItemDAO.consultarTodosByOrcamentoId(obj);
            for (OrcamentoItem item : itens) {
                if (item.getTipoOrcamentoItem() == TipoOrcamentoItem.PRODUTO) {
                    produtoDAO.atualizarEstoque(item);
                }
            }

            String sqlitem = "delete from orcamento_item where orcamento_id = ?";
            PreparedStatement statementItem = conn.prepareStatement(sqlitem);
            statementItem.setInt(1, obj.getId());
            statementItem.executeUpdate();

            String sqlOrcamento = "delete from orcamentos where id = ?";
            PreparedStatement statementOrcamento = conn.prepareStatement(sqlOrcamento);
            statementOrcamento.setInt(1, obj.getId());
            statementOrcamento.executeUpdate();

            conn.commit();
        } catch (SQLException | ClassNotFoundException e) {
            throw new BdException(e.getMessage());
        }
    }

    @Override
    public void atualizar(Orcamento obj) {
        try(Connection conn = getConnection()){

            String sql = "update orcamentos set cliente_id = ?, dataOrcamento = ?, observacao = ?, valorTotal = ?, status = ? where id = ?";

            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setInt(1, obj.getCliente().getId());
            statement.setDate(2, Date.valueOf(obj.getDataOrcamento()));
            statement.setString(3, obj.getObservacao());
            statement.setDouble(4, obj.getValorTotal());
            statement.setString(5, obj.getStatus().toString());
            statement.setInt(6, obj.getId());

            statement.execute();

        } catch (SQLException | ClassNotFoundException e) {
            throw new BdException(e.getMessage());
        }
    }

    private Orcamento getar(ResultSet rs) throws SQLException {
        Orcamento orcamento = new Orcamento();
        orcamento.setId(rs.getInt("id"));
        orcamento.setDataOrcamento(rs.getDate("dataOrcamento").toLocalDate());
        orcamento.setObservacao(rs.getString("observacao"));
        orcamento.setValorTotal(rs.getDouble("valorTotal"));
        orcamento.setStatus(StatusOrcamento.valueOf(rs.getString("status")));

        int clienteId = rs.getInt("cliente_id");
        if (!rs.wasNull()) {
            ClienteDAO clienteDAO = new ClienteDAO();
            Cliente cliente = new ClienteBuilder().comId(clienteId).constroi();
            cliente = clienteDAO.consultarById(cliente);
            orcamento.setCliente(cliente);
        }

        return orcamento;
    }

    public void atualizarValorTotal(Orcamento obj) {
        try (Connection conn = getConnection()) {
            String sql = "UPDATE orcamentos SET valorTotal = " +
                         "(SELECT COALESCE(SUM(subtotal), 0) FROM orcamento_item WHERE orcamento_id = ?) " +
                         "WHERE id = ?";
            
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, obj.getId());
            statement.setInt(2, obj.getId());
            
            statement.executeUpdate();
            
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Erro ao atualizar o valor total do orçamento", e);
        }
    }

    @Override
    public Connection getConnection() throws SQLException, ClassNotFoundException {
        return FabricaConexao.getConexao();
    }
}
