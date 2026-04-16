package com.project.orcamentofly.service;

import com.project.orcamentofly.dao.ProdutoDAO;
import com.project.orcamentofly.exception.BadRequestException;
import com.project.orcamentofly.exception.ResourceNotFoundException;
import com.project.orcamentofly.model.OrcamentoItem;
import com.project.orcamentofly.model.Produto;
import com.project.orcamentofly.model.factory.ProdutoFactory;

import java.util.List;

public class ProdutoService {

    private final ProdutoDAO dao;

    public ProdutoService() {
        this.dao = new ProdutoDAO();
    }

    public List<Produto> consultarTodos() {
        return dao.consultarTodos();
    }

    public Produto consultarById(int id) {
        Produto produto = new Produto();
        produto.setId(id);

        Produto produtoEncontrado = dao.consultarById(produto);
        if (produtoEncontrado == null || produtoEncontrado.getId() <= 0) {
            return null;
        }

        return produtoEncontrado;
    }

    public void inserir(Produto produto) {
        validarProduto(produto, false);

        Produto novoProduto = ProdutoFactory.criarProduto(
                produto.getNome(),
                produto.getDescricao(),
                produto.getValorUnitario(),
                produto.getEstoque()
        );
        dao.inserir(novoProduto);
    }

    public void atualizar(Produto produto) {
        validarProduto(produto, true);

        if (consultarById(produto.getId()) == null) {
            throw new ResourceNotFoundException("Produto com ID " + produto.getId() + " não encontrado");
        }

        Produto produtoAtualizado = ProdutoFactory.criarProduto(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getValorUnitario(),
                produto.getEstoque()
        );
        dao.atualizar(produtoAtualizado);
    }

    public void deletar(int id) {
        if (id <= 0) {
            throw new BadRequestException("ID do produto inválido");
        }

        Produto produto = consultarById(id);
        if (produto == null) {
            throw new ResourceNotFoundException("Produto com ID " + id + " não encontrado");
        }

        dao.deletar(produto);
    }

    public void atualizarEstoque(OrcamentoItem item) {
        dao.atualizarEstoque(item);
    }

    public void aumentarEstoque(int produtoId, int quantidade) {
        validarMovimentoEstoque(produtoId, quantidade);
        dao.atualizarEstoque(criarMovimentoEstoque(produtoId, quantidade));
    }

    public void reduzirEstoque(int produtoId, int quantidade) {
        validarMovimentoEstoque(produtoId, quantidade);

        Produto produto = consultarById(produtoId);
        if (produto == null) {
            throw new ResourceNotFoundException("Produto com ID " + produtoId + " não encontrado");
        }
        if (quantidade > produto.getEstoque()) {
            throw new BadRequestException("O produto possui menor quantidade do requirida");
        }

        dao.atualizarEstoque(criarMovimentoEstoque(produtoId, -quantidade));
    }

    private void validarProduto(Produto produto, boolean validarId) {
        if (produto == null) {
            throw new BadRequestException("Produto é obrigatório");
        }
        if (validarId && produto.getId() <= 0) {
            throw new BadRequestException("ID do produto inválido");
        }
        if (produto.getNome() == null || produto.getNome().isBlank()) {
            throw new BadRequestException("Nome do produto é obrigatório");
        }
        if (produto.getValorUnitario() <= 0) {
            throw new BadRequestException("Valor unitário do produto deve ser maior que zero");
        }
        if (produto.getEstoque() < 0) {
            throw new BadRequestException("Estoque do produto não pode ser negativo");
        }
    }

    private void validarMovimentoEstoque(int produtoId, int quantidade) {
        if (produtoId <= 0) {
            throw new BadRequestException("ID do produto inválido");
        }
        if (quantidade <= 0) {
            throw new BadRequestException("Quantidade para movimentação de estoque inválida");
        }
    }

    private Produto criarMovimentoEstoque(int produtoId, int quantidade) {
        Produto produto = new Produto();
        produto.setId(produtoId);
        produto.setEstoque(quantidade);
        return produto;
    }
}
