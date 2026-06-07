package com.project.orcamentofly.model.decorator;

import com.project.orcamentofly.model.interfaces.MicroProdutoInterface;

public class MicroProdutoDecorator implements MicroProdutoInterface {

    private MicroProdutoInterface microProduto;

    public MicroProdutoDecorator() {}

    public MicroProdutoDecorator(MicroProdutoInterface microProduto) {
        this.microProduto = microProduto;
    }

    @Override
    public double getValor() {
        return microProduto.getValor();
    }
}
