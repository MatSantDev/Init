package com.project.orcamentofly.dao;

import com.project.orcamentofly.model.Orcamento;
import com.project.orcamentofly.model.OrcamentoItem;
import com.project.orcamentofly.model.enums.TipoOrcamentoItem;
import com.project.orcamentofly.util.FabricaConexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrcamentoDAO {

    private OrcamentoItemDAO orcamentoItemDAO;
    private ProdutoDAO produtoDAO;

    public OrcamentoDAO(){
        this.orcamentoItemDAO = new OrcamentoItemDAO();
        this.produtoDAO = new ProdutoDAO();
    }

    public List<Orcamento> consultarTodos() throws SQLException, ClassNotFoundException {

        Connection conn = FabricaConexao.getConexao();

        String sql = "select * from orcamentos";
        PreparedStatement statement = conn.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();

        List<Orcamento> list = new ArrayList<>();
        Orcamento orcamento = new Orcamento();

        while (rs.next()){
            orcamento = getar(rs);
            list.add(orcamento);
        }

        conn.close();
        return list;
    }

    public Orcamento consultarById(int id) throws ClassNotFoundException, SQLException {

        Connection conn = FabricaConexao.getConexao();
        String sql = "select * from orcamentos where id = ?";

        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, id);

        ResultSet rs = statement.executeQuery();

        Orcamento orcamento = new Orcamento();

        if(rs.next()){
            orcamento = getar(rs);
        }

        conn.close();
        return orcamento;
    }

    public Orcamento inserir(Orcamento orcamento) throws SQLException, ClassNotFoundException {

        Connection conn = FabricaConexao.getConexao();

        String sql = "insert into orcamentos (cliente, dataOrcamento, observacao, valorTotal) VALUE (?, ?, ?, ?)";

        PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        statement.setString(1, orcamento.getCliente());
        statement.setDate(2, Date.valueOf(orcamento.getDataOrcamento()));
        statement.setString(3, orcamento.getObservacao());
        statement.setDouble(4, orcamento.getValorTotal());

        statement.executeUpdate();

        ResultSet rs = statement.getGeneratedKeys();
        if(rs.next()){
            int orcamentoId = rs.getInt(1);
            orcamento.setId(orcamentoId);
        }

        conn.close();
        return orcamento;
    }

    public void deletar(Orcamento orcamento) throws SQLException, ClassNotFoundException {

        Connection conn = null;

        try {
            conn = FabricaConexao.getConexao();
            conn.setAutoCommit(false);

            List<OrcamentoItem> itens = orcamentoItemDAO.consultarTodosByOrcamentoId(orcamento.getId(), conn);

            for (OrcamentoItem item : itens) {
                if (item.getTipoOrcamentoItem() == TipoOrcamentoItem.PRODUTO) {
                    produtoDAO.atualizarEstoque(item.getProduto().getId(), item.getQuantidade(), conn);
                }
            }

            String sqlitem = "delete from orcamento_item where orcamento_id = ?";
            PreparedStatement statementItem = conn.prepareStatement(sqlitem);
            statementItem.setInt(1, orcamento.getId());
            statementItem.executeUpdate();

            String sqlOrcamento = "delete from orcamentos where id = ?";
            PreparedStatement statementOrcamento = conn.prepareStatement(sqlOrcamento);
            statementOrcamento.setInt(1, orcamento.getId());
            statementOrcamento.executeUpdate();

            conn.commit();
        } catch (SQLException | ClassNotFoundException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    public void atualizar(Orcamento orcamento) throws SQLException, ClassNotFoundException {

        Connection conn = FabricaConexao.getConexao();

        String sql = "update orcamentos set cliente = ?, dataOrcamento = ?, observacao = ?, valorTotal = ? where id = ?";

        PreparedStatement statement = conn.prepareStatement(sql);

        statement.setString(1, orcamento.getCliente());
        statement.setDate(2, Date.valueOf(orcamento.getDataOrcamento()));
        statement.setString(3, orcamento.getObservacao());
        statement.setDouble(4, orcamento.getValorTotal());
        statement.setInt(5, orcamento.getId());

        statement.execute();

        conn.close();
    }

    private Orcamento getar(ResultSet rs) throws SQLException {
        Orcamento orcamento = new Orcamento();
        orcamento.setId(rs.getInt("id"));
        orcamento.setCliente(rs.getString("cliente"));
        orcamento.setDataOrcamento(rs.getDate("dataOrcamento").toLocalDate());
        orcamento.setObservacao(rs.getString("observacao"));
        orcamento.setValorTotal(rs.getDouble("valorTotal"));
        return orcamento;
    }
}
