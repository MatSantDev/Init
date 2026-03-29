package com.project.orcamentofly.model;

import com.project.orcamentofly.model.enums.TipoOrcamentoItem;

public class OrcamentoItem {

    private int id;
    private TipoOrcamentoItem tipoOrcamentoItem;
    private int quantidade;
    private double valorUnitario;
    private double subtotal;

    // Orcamento orcamento;
    private Produto produto;
    private Servico servico;

    public OrcamentoItem() {
    }

    public OrcamentoItem(int id, TipoOrcamentoItem tipoOrcamentoItem, int quantidade, double valorUnitario, double subtotal, Produto produto, Servico servico) {
        this.id = id;
        this.tipoOrcamentoItem = tipoOrcamentoItem;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.subtotal = subtotal;
        this.produto = produto;
        this.servico = servico;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TipoOrcamentoItem getTipoOrcamentoItem() {
        return tipoOrcamentoItem;
    }

    public void setTipoOrcamentoItem(TipoOrcamentoItem tipoOrcamentoItem) {
        this.tipoOrcamentoItem = tipoOrcamentoItem;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
        calcularSubtotal();
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
        calcularSubtotal();
    }

    public double getSubtotal() {
        return subtotal;
    }

    private void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public void calcularSubtotal(){
        setSubtotal(quantidade * valorUnitario);
    }

    public void definirProduto(Produto produto, int quantidade){
        this.tipoOrcamentoItem = TipoOrcamentoItem.PRODUTO;
        this.produto = produto;
        this.servico = null;
        this.quantidade = quantidade;
        this.valorUnitario = produto.getValorUnitario();
        calcularSubtotal();
    }

    public void definirServico(Servico servico, int quantidade){
        this.tipoOrcamentoItem = TipoOrcamentoItem.SERVICO;
        this.servico = servico;
        this.produto = null;
        this.quantidade = quantidade;
        this.valorUnitario = servico.getValorUnitario();
        calcularSubtotal();
    }
}
