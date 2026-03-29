package com.project.orcamentofly.dao;

import com.project.orcamentofly.model.Produto;
import com.project.orcamentofly.util.FabricaConexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    public List<Produto> consultarTodos() throws ClassNotFoundException, SQLException {

        Connection conn = FabricaConexao.getConexao();

        String sql = "select * from produtos";
        PreparedStatement statement = conn.prepareStatement(sql);

        ResultSet rs = statement.executeQuery();

        List<Produto> list = new ArrayList<Produto>();

        while(rs.next()){
            Produto produto = getar(rs);
            list.add(produto);
        }

        conn.close();
        return list;
    }

    public Produto consultarById(int id) throws ClassNotFoundException, SQLException {

        Connection conn = FabricaConexao.getConexao();
        String sql = "select * from produtos where id = ?";

        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, id);

        ResultSet rs = statement.executeQuery();

        Produto produto = new Produto();

        if(rs.next()){
            produto = getar(rs);
        }

        conn.close();
        return produto;
    }

    public void inserir(Produto produto) throws SQLException, ClassNotFoundException {

        Connection conn = FabricaConexao.getConexao();

        String sql = "insert into produtos (nome, descricao, valorUnitario, estoque) VALUE (?, ?, ?, ?)";

        PreparedStatement statement = conn.prepareStatement(sql);

        statement.setString(1, produto.getNome());
        statement.setString(2, produto.getDescricao());
        statement.setDouble(3, produto.getValorUnitario());
        statement.setInt(4, produto.getEstoque());

        statement.executeUpdate();

        conn.close();
    }

    public void deletar(Produto produto) throws SQLException, ClassNotFoundException {

        Connection conn = FabricaConexao.getConexao();

        String sql = "delete from produtos where id = ?";

        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, produto.getId());
        statement.execute();

        conn.close();
    }

    public void atualizar(Produto produto) throws SQLException, ClassNotFoundException {

        Connection conn = FabricaConexao.getConexao();

        String sql = "update produtos set nome = ?, descricao = ?, valorUnitario = ?, estoque = ? where id = ?";

        PreparedStatement statement = conn.prepareStatement(sql);

        statement.setString(1, produto.getNome());
        statement.setString(2, produto.getDescricao());
        statement.setDouble(3, produto.getValorUnitario());
        statement.setInt(4, produto.getEstoque());
        statement.setInt(5, produto.getId());

        statement.execute();

        conn.close();
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
}
