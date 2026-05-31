package com.project.orcamentofly.model;

import com.project.orcamentofly.model.interfaces.MicroServicoInterface;

public class MicroServico implements MicroServicoInterface {

    public String nome;
    public Double valor;

    public MicroServico() {}

    public MicroServico(String nome, Double valor) {
        this.nome = nome;
        this.valor = valor;
    }

    @Override
    public double getValor() {
        return this.valor;
    }

    @Override
    public String toString() {
        return "MicroServico{" +
                "nome='" + nome + '\'' +
                ", valor=" + valor +
                '}';
    }
}
