package com.project.orcamentofly.model.dto;

public class MicroProdutoDTO {

    private String tipo;

    public MicroProdutoDTO() {}

    public MicroProdutoDTO(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
