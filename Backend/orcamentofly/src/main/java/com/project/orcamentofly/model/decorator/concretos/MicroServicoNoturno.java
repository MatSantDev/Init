package com.project.orcamentofly.model.decorator.concretos;

import com.project.orcamentofly.model.decorator.MicroServicoDecorator;
import com.project.orcamentofly.model.interfaces.MicroServicoInterface;

public class MicroServicoNoturno extends MicroServicoDecorator {

    public MicroServicoNoturno(MicroServicoInterface microServico) {
        super(microServico);
    }

    @Override
    public Double getValor() {
        return super.getValor() * 1.10;
    }
}
