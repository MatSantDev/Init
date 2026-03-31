package com.project.orcamentofly.dao;

import com.project.orcamentofly.exception.BdException;
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
    public Produto consultarById(int id) {
        try(Connection conn = getConnection()){

            String sql = "select * from produtos where id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);

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
    public void inserir(Produto produto) {
        try(Connection conn = getConnection()){

            String sql = "insert into produtos (nome, descricao, valorUnitario, estoque) VALUE (?, ?, ?, ?)";

            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setString(1, produto.getNome());
            statement.setString(2, produto.getDescricao());
            statement.setDouble(3, produto.getValorUnitario());
            statement.setInt(4, produto.getEstoque());

            statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new BdException(e.getMessage());
        }
    }

    @Override
    public void deletar(Produto produto) {
        try(Connection conn = getConnection()){

            String sql = "delete from produtos where id = ?";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, produto.getId());
            statement.execute();

        } catch (SQLException | ClassNotFoundException e) {
            throw new BdException(e.getMessage());
        }
    }

    @Override
    public void atualizar(Produto produto) {
        try(Connection conn = getConnection()){

            String sql = "update produtos set nome = ?, descricao = ?, valorUnitario = ?, estoque = ? where id = ?";

            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setString(1, produto.getNome());
            statement.setString(2, produto.getDescricao());
            statement.setDouble(3, produto.getValorUnitario());
            statement.setInt(4, produto.getEstoque());
            statement.setInt(5, produto.getId());

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

    public void atualizarEstoque(int produtoId, int quantidade) {
        try(Connection conn = getConnection()){
            String sql = "UPDATE produtos SET estoque = estoque + ? WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, quantidade);
            statement.setInt(2, produtoId);
            statement.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            throw new BdException(e.getMessage());
        }

    }

    public void diminuirEstoque(int produtoId, int quantidade, Connection conn) throws SQLException {
        String sql = "UPDATE produtos SET estoque = estoque + ? WHERE id = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, quantidade);
        statement.setInt(2, produtoId);
        statement.executeUpdate();
    }

    @Override
    public Connection getConnection() throws SQLException, ClassNotFoundException {
        return FabricaConexao.getConexao();
    }
}
