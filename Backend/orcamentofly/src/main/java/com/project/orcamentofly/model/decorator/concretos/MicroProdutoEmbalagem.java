package com.project.orcamentofly.model.decorator.concretos;

import com.project.orcamentofly.model.decorator.MicroProdutoDecorator;
import com.project.orcamentofly.model.interfaces.MicroProdutoInterface;

public class MicroProdutoEmbalagem extends MicroProdutoDecorator {

    public MicroProdutoEmbalagem(MicroProdutoInterface microProduto) {
        super(microProduto);
    }

    @Override
    public double getValor() {
        return super.getValor() + 25;
    }
}
