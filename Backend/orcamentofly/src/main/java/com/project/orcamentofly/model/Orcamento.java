package com.project.orcamentofly.model;

import com.project.orcamentofly.model.enums.StatusOrcamento;
import com.project.orcamentofly.service.ClienteService;

import java.util.*;
import java.time.LocalDate;

public class Orcamento {

    private int id;
    private LocalDate dataOrcamento;
    private String observacao;
    private double valorTotal;
    private StatusOrcamento status;

    private List<OrcamentoItem> itens = new ArrayList<>();
    private Cliente cliente;

    public Orcamento() {}

    public Orcamento(int id, Cliente cliente, LocalDate dataOrcamento, String observacao, double valorTotal, StatusOrcamento status) {
        this.id = id;
        this.cliente = cliente;
        this.dataOrcamento = dataOrcamento;
        this.observacao = observacao;
        this.valorTotal = valorTotal;
        this.status = status;
    }

    public Orcamento(Cliente cliente, LocalDate dataOrcamento, String observacao, double valorTotal) {
        this.cliente = cliente;
        this.dataOrcamento = dataOrcamento;
        this.observacao = observacao;
        this.valorTotal = valorTotal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDate getDataOrcamento() {
        return dataOrcamento;
    }

    public void setDataOrcamento(LocalDate dataOrcamento) {
        this.dataOrcamento = dataOrcamento;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public List<OrcamentoItem> getItens() {
        return itens;
    }

    public void setItens(List<OrcamentoItem> itens) {
        this.itens = itens;
    }

    public void addItem(OrcamentoItem item){
        this.itens.add(item);
    }

    public boolean removeItem(OrcamentoItem item){
        return this.itens.remove(item);
    }

    public StatusOrcamento getStatus() {
        return status;
    }

    public void setStatus(StatusOrcamento status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Orcamento orcamento = (Orcamento) o;
        return id == orcamento.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Orcamento{" +
                "id=" + id +
                ", cliente='" + cliente + '\'' +
                ", dataOrcamento=" + dataOrcamento +
                ", observacao='" + observacao + '\'' +
                ", valorTotal=" + valorTotal +
                ", itens=" + itens +
                '}';
    }
}
