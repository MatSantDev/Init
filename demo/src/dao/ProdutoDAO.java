package dao;

import Exception.DBException;
import model.Produto;
import util.ConnectionFactory;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProdutoDAO {

    public Produto buscarPorId(Integer id){
        String sql = "SELECT * FROM produto WHERE produto.id = ?";
        Produto produto = null;
        ResultSet rs;

        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql)){
            statement.setInt(1, id);

            rs = statement.executeQuery();

            while (rs.next()){
                produto = new Produto();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setValorUnitario(rs.getDouble("valorUnitario"));
                produto.setEstoque(rs.getInt("estoque"));
            }
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }

        return produto;
    }

    public List<Produto> listarTodos(Integer id){
        String sql = "SELECT * FROM produto";
        List<Produto> itens = new ArrayList<>();
        ResultSet rs;

        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql)){

            rs = statement.executeQuery();

            while (rs.next()){
                Produto produto = new Produto();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setValorUnitario(rs.getDouble("valorUnitario"));
                produto.setEstoque(rs.getInt("estoque"));
                itens.add(produto);
            }
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }

        return itens;
    }


    public void inserir(Produto produto){
        String sql = "INSERT INTO produto (nome, descricao, valorUnitario, estoque) VALUES (?, ?, ?, ?)";

        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql)){
                statement.setString(1, produto.getNome());
                statement.setString(2, produto.getDescricao());
                statement.setDouble(3, produto.getValorUnitario());
                statement.setInt(4, produto.getEstoque());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
    }


    public void atualizar(Produto produto) throws SQLException {
        String sql = "UPDATE produto SET nome = ?, descricao = ?, valor_unitario = ?, estoque = ? WHERE id_produto = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(5, produto.getId());
            statement.setString(1, produto.getNome());
            statement.setString(2, produto.getDescricao());
            statement.setDouble(3, produto.getValorUnitario());
            statement.setInt(4, produto.getEstoque());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
    }

    public void excluir(Integer id) {
        String sql = "DELETE FROM produto WHERE produto.id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
    }


}
