package com.project.orcamentofly.model.dto;

public class MicroServicoDTO {

    private String tipo; // URGENTE, NOTURNO, GARANTIA

    public MicroServicoDTO() {}

    public MicroServicoDTO(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}

