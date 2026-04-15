package com.project.orcamentofly.command.produto;

import com.project.orcamentofly.command.Command;
import com.project.orcamentofly.model.Produto;
import com.project.orcamentofly.model.factory.ProdutoFactory;
import com.project.orcamentofly.service.ProdutoService;

/**
 * Comando concreto para inserir um produto usando Factory Pattern
 */
public class InserirProdutoCommand implements Command {
    private final ProdutoService service;
    private final String nome;
    private final String descricao;
    private final double valorUnitario;
    private final int estoque;
    private Produto produtoInserido;

    public InserirProdutoCommand(ProdutoService service, String nome, String descricao, double valorUnitario, int estoque) {
        this.service = service;
        this.nome = nome;
        this.descricao = descricao;
        this.valorUnitario = valorUnitario;
        this.estoque = estoque;
    }

    @Override
    public void executar() {
        // Usar Factory Pattern para criar o produto
        Produto produto = ProdutoFactory.criarProduto(nome, descricao, valorUnitario, estoque);
        service.inserir(produto);
        this.produtoInserido = produto;
    }

    @Override
    public void desfazer() {
        // Implementar lógica de desfazer
        if (produtoInserido != null && produtoInserido.getId() > 0) {
            service.deletar(produtoInserido);
        }
    }
}

