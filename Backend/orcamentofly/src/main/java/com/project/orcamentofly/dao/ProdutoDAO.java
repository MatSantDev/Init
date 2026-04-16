package com.project.orcamentofly.dao;

import com.project.orcamentofly.exception.BdException;
import com.project.orcamentofly.model.OrcamentoItem;
import com.project.orcamentofly.model.Produto;
import com.project.orcamentofly.util.FabricaConexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO implements GenericDAO<Produto> {

    @Override
    public List<Produto> consultarTodos() {
        try(Connection conn = getConnection()){

            String sql = "select * from produtos";
            PreparedStatement statement = conn.prepareStatement(sql);

            ResultSet rs = statement.executeQuery();

            List<Produto> list = new ArrayList<Produto>();

            while(rs.next()){
                Produto produto = getar(rs);
                list.add(produto);
            }
            return list;

        } catch (SQLException | ClassNotFoundException e) {
            throw new BdException(e.getMessage());
        }
    }

    @Override
    public Produto consultarById(Produto obj) {
        try(Connection conn = getConnection()){

            String sql = "select * from produtos where id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, obj.getId());

            ResultSet rs = statement.executeQuery();

            Produto produto = new Produto();

            if(rs.next()){
                produto = getar(rs);
            }

            return produto;
        } catch (SQLException | ClassNotFoundException e) {
            throw new BdException(e.getMessage());
        }
    }

    @Override
    public void inserir(Produto obj) {
        try(Connection conn = getConnection()){

            String sql = "insert into produtos (nome, descricao, valorUnitario, estoque) VALUE (?, ?, ?, ?)";

            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setString(1, obj.getNome());
            statement.setString(2, obj.getDescricao());
            statement.setDouble(3, obj.getValorUnitario());
            statement.setInt(4, obj.getEstoque());

            statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new BdException(e.getMessage());
        }
    }

    @Override
    public void deletar(Produto obj) {
        try(Connection conn = getConnection()){

            String sql = "delete from produtos where id = ?";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, obj.getId());
            statement.execute();

        } catch (SQLException | ClassNotFoundException e) {
            throw new BdException(e.getMessage());
        }
    }

    @Override
    public void atualizar(Produto obj) {
        try(Connection conn = getConnection()){

            String sql = "update produtos set nome = ?, descricao = ?, valorUnitario = ?, estoque = ? where id = ?";

            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setString(1, obj.getNome());
            statement.setString(2, obj.getDescricao());
            statement.setDouble(3, obj.getValorUnitario());
            statement.setInt(4, obj.getEstoque());
            statement.setInt(5, obj.getId());

            statement.execute();

        } catch (SQLException | ClassNotFoundException e) {
            throw new BdException(e.getMessage());
        }
    }

    private Produto getar(ResultSet rs) throws SQLException {
        Produto produto = new Produto();

        produto.setId(rs.getInt("id"));
        produto.setNome(rs.getString("nome"));
        produto.setDescricao(rs.getString("descricao"));
        produto.setValorUnitario(rs.getDouble("valorUnitario"));
        produto.setEstoque(rs.getInt("estoque"));

        return produto;
    }

    public void atualizarEstoque(OrcamentoItem item) {
        Produto produto = new Produto();
        produto.setId(item.getProduto().getId());
        produto.setEstoque(item.getQuantidade());
        atualizarEstoque(produto);
    }

    public void atualizarEstoque(Produto produto) {
        try(Connection conn = getConnection()){
            String sql = "UPDATE produtos SET estoque = estoque + ? WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, produto.getEstoque());
            statement.setInt(2, produto.getId());
            statement.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            throw new BdException(e.getMessage());
        }
    }

    @Override
    public Connection getConnection() throws SQLException, ClassNotFoundException {
        return FabricaConexao.getConexao();
    }
}
