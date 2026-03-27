package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Orcamento {

    private Integer id;
    private String cliente;
    private LocalDate dataOrcamento;
    private String observacao;
    private Double valorTotal;

    private List<OrcamentoItem> itens;

    public Orcamento() {
        this.itens = new ArrayList<>();
        this.valorTotal = 0.0;
    }

    public Orcamento(String cliente, LocalDate dataOrcamento, String observacao, Double valorTotal) {
        this.cliente = cliente;
        this.dataOrcamento = dataOrcamento;
        this.observacao = observacao;
        this.valorTotal = valorTotal;
    }

    public Orcamento(Integer id, String cliente, LocalDate dataOrcamento, String observacao, Double valorTotal) {
        this.id = id;
        this.cliente = cliente;
        this.dataOrcamento = dataOrcamento;
        this.observacao = observacao;
        this.valorTotal = valorTotal;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
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

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public List<OrcamentoItem> getItens() {
        return itens;
    }

    public void adicionarItem(OrcamentoItem item){
        this.itens.add(item);
        calcularTotal();
    }

    public void removerItem(OrcamentoItem item){
        this.itens.remove(item);
        calcularTotal();
    }

    public void calcularTotal(){
        Double total = 0.0;
        for (OrcamentoItem item : itens){
            total += item.getSubtotal();

        }
        this.valorTotal = total;
    }

}
