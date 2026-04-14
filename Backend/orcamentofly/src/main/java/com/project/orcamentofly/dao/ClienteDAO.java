package com.project.orcamentofly.dao;

import com.project.orcamentofly.exception.BdException;
import com.project.orcamentofly.model.Cliente;
import com.project.orcamentofly.model.builder.ClienteBuilder;
import com.project.orcamentofly.util.FabricaConexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO implements GenericDAO<Cliente>{

    @Override
    public void inserir(Cliente obj) {
        try(Connection conn = getConnection()){

            String sql = "insert into clientes (nome, email, telefone, cpf, cep, endereco, sexo, dataNascimento, criadoEm) VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, obj.getNome());
            statement.setString(2, obj.getEmail());
            statement.setString(3, obj.getTelefone());
            statement.setString(4, obj.getCpf());
            statement.setString(5, obj.getCep());
            statement.setString(6, obj.getEndereco());
            statement.setString(7, obj.getSexo());
            statement.setDate(8, Date.valueOf(obj.getDataNascimento()));
            statement.setDate(9, Date.valueOf(obj.getCriadoEm()));

            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            if(rs.next()){
                int objId = rs.getInt(1);
                obj.setId(objId);
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new BdException(e.getMessage());
        }
    }

    @Override
    public void atualizar(Cliente obj) {
        try (Connection conn = getConnection()) {

            String sql = "UPDATE clientes SET nome=?, email=?, telefone=?, cpf=?, cep=?, endereco=?, sexo=?, dataNascimento=? WHERE id=?";

            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setString(1, obj.getNome());
            statement.setString(2, obj.getEmail());
            statement.setString(3, obj.getTelefone());
            statement.setString(4, obj.getCpf());
            statement.setString(5, obj.getCep());
            statement.setString(6, obj.getEndereco());
            statement.setString(7, obj.getSexo());
            statement.setDate(8, Date.valueOf(obj.getDataNascimento()));
            statement.setInt(9, obj.getId());

            statement.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            throw new BdException(e.getMessage());
        }
    }

    @Override
    public void deletar(Cliente obj) {
        try (Connection conn = getConnection()) {

            String sql = "DELETE FROM clientes WHERE id=?";

            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setInt(1, obj.getId());

            statement.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            throw new BdException(e.getMessage());
        }
    }

    @Override
    public Cliente consultarById(int id) {
        try (Connection conn = getConnection()) {

            String sql = "SELECT * FROM clientes WHERE id = ?";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return getCliente(rs);
            }

            return null;

        } catch (SQLException | ClassNotFoundException e) {
            throw new BdException(e.getMessage());
        }
    }

    @Override
    public List<Cliente> consultarTodos() {
        try(Connection conn = getConnection()){
            String sql = "select * from clientes";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            List<Cliente> list = new ArrayList<>();

            while (rs.next()){
                list.add(getar(rs));
            }

            return list;

        } catch (SQLException | ClassNotFoundException e) {
            throw new BdException(e.getMessage());
        }
    }

    private Cliente getar(ResultSet rs) throws SQLException {
        return getCliente(rs);
    }

    private Cliente getCliente(ResultSet rs) throws SQLException {
        return new ClienteBuilder()
                .comId(rs.getInt("id"))
                .comNome(rs.getString("nome"))
                .comEmail(rs.getString("email"))
                .comTelefone(rs.getString("telefone"))
                .comCpf(rs.getString("cpf"))
                .comCep(rs.getString("cep"))
                .comEndereco(rs.getString("endereco"))
                .comSexo(rs.getString("sexo"))
                .comDataNascimento(rs.getDate("dataNascimento").toLocalDate())
                .comCriadoEm(rs.getDate("criadoEm").toLocalDate())
                .constroi();
    }

    @Override
    public Connection getConnection() throws SQLException, ClassNotFoundException {
        return FabricaConexao.getConexao();
    }
}
