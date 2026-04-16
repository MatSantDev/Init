package com.project.orcamentofly.model.factory;

import com.project.orcamentofly.model.Produto;

public class ProdutoFactory {

    public static Produto criarProduto(String nome, String descricao, double valorUnitario, int estoque) {
        return new Produto(nome, descricao, valorUnitario, estoque);
    }

    public static Produto criarProduto(int id, String nome, String descricao, double valorUnitario, int estoque) {
        Produto produto = criarProduto(nome, descricao, valorUnitario, estoque);
        produto.setId(id);
        return produto;
    }
}



