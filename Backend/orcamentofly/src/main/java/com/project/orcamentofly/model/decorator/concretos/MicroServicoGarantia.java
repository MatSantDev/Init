package com.project.orcamentofly.model.decorator.concretos;

import com.project.orcamentofly.model.decorator.MicroServicoDecorator;
import com.project.orcamentofly.model.interfaces.MicroServicoInterface;

public class MicroServicoGarantia extends MicroServicoDecorator {

    public MicroServicoGarantia(MicroServicoInterface microServico) {
        super(microServico);
    }

    @Override
    public double getValor() {
        return super.getValor() * 1.10;
    }
}
