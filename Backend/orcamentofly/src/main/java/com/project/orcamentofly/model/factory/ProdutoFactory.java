package com.project.orcamentofly.model.factory;

import com.project.orcamentofly.model.Produto;

/**
 * Factory Pattern - Fábrica para criar instâncias de Produto
 * Responsável pela criação centralizada de objetos Produto
 */
public class ProdutoFactory {

    public static Produto criarProduto(String nome, String descricao, double valorUnitario, int estoque) {
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("Nome do produto não pode ser vazio");
        }
        if (valorUnitario <= 0) {
            throw new IllegalArgumentException("Valor unitário deve ser maior que zero");
        }
        if (estoque < 0) {
            throw new IllegalArgumentException("Estoque não pode ser negativo");
        }
        return new Produto(nome, descricao, valorUnitario, estoque);
    }

    @SuppressWarnings("unused")
    public static Produto criarProduto(int id, String nome, String descricao, double valorUnitario, int estoque) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID do produto deve ser maior que zero");
        }
        Produto produto = criarProduto(nome, descricao, valorUnitario, estoque);
        produto.setId(id);
        return produto;
    }
}



