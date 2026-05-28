package com.project.orcamentofly.model.decorator;

import com.project.orcamentofly.model.interfaces.MicroServicoInterface;

public abstract class MicroServicoDecorator implements MicroServicoInterface {

    private MicroServicoInterface microServico;

    public MicroServicoDecorator(MicroServicoInterface microServico) {
        this.microServico = microServico;
    }

    @Override
    public Double getValor() {
        return microServico.getValor();
    }
}
