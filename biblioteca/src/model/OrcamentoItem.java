package model;

import model.enums.TipoOrcamentoItem;

public class OrcamentoItem {

    private Integer id;
    private TipoOrcamentoItem tipoOrcamentoItem;
    private Integer quantidade;
    private Double valorUnitario;
    private Double subtotal;

    private Orcamento orcamento;
    private Produto produto;
    private Servico servico;

    public OrcamentoItem() {
        this.valorUnitario = 0.0;
        this.subtotal = 0.0;
    }

    public OrcamentoItem(Orcamento orcamento, TipoOrcamentoItem tipoOrcamentoItem, Produto produto, Servico servico, Integer quantidade, Double valorUnitario, Double subtotal) {
        this.orcamento = orcamento;
        this.tipoOrcamentoItem = tipoOrcamentoItem;
        this.produto = produto;
        this.servico = servico;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.subtotal = subtotal;
    }

    public OrcamentoItem(Integer id, Orcamento orcamento, TipoOrcamentoItem tipoOrcamentoItem, Produto produto, Servico servico, Integer quantidade, Double valorUnitario, Double subtotal) {
        this.id = id;
        this.orcamento = orcamento;
        this.tipoOrcamentoItem = tipoOrcamentoItem;
        this.produto = produto;
        this.servico = servico;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.subtotal = subtotal;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Orcamento getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(Orcamento orcamento) {
        this.orcamento = orcamento;
    }

    public TipoOrcamentoItem getTipoOrcamentoItem() {
        return tipoOrcamentoItem;
    }

    public void setTipoOrcamentoItem(TipoOrcamentoItem tipoOrcamentoItem) {
        this.tipoOrcamentoItem = tipoOrcamentoItem;
    }

    public Produto getProduto() {
        return produto;
    }

    public Servico getServico() {
        return servico;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
        calcularSubtotal();
    }

    public Double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
        calcularSubtotal();
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
        calcularSubtotal();
    }

    public void calcularSubtotal(){
        if(valorUnitario != null){
            this.subtotal = valorUnitario * quantidade;
        }
    }

    public void definirProduto(Produto produto, int quantidade) {
        this.tipoOrcamentoItem = TipoOrcamentoItem.PRODUTO;
        this.produto = produto;
        this.servico = null;
        this.quantidade = quantidade;
        this.valorUnitario = produto.getValorUnitario();
        calcularSubtotal();
    }

    public void definirServico(Servico servico, int quantidade) {
        this.tipoOrcamentoItem = TipoOrcamentoItem.SERVICO;
        this.servico = servico;
        this.produto = null;
        this.quantidade = quantidade;
        this.valorUnitario = servico.getValorUnitario();
        calcularSubtotal();
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }
}
