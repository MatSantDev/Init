package com.project.orcamentofly.model.decorator.concretos;

import com.project.orcamentofly.model.decorator.MicroProdutoDecorator;
import com.project.orcamentofly.model.interfaces.MicroProdutoInterface;

public class MicroProdutoCustomizacao extends MicroProdutoDecorator {

    public MicroProdutoCustomizacao(MicroProdutoInterface microProduto) {
        super(microProduto);
    }

    @Override
    public double getValor() {
        return super.getValor() + 75;
    }
}
