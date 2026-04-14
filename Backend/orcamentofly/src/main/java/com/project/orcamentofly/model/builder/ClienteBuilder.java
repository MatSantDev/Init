package com.project.orcamentofly.model.builder;

import com.project.orcamentofly.model.Cliente;

import java.time.LocalDate;

public class ClienteBuilder {

    private int id;
    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    private String cep;
    private String endereco;
    private String sexo;
    private LocalDate dataNascimento;
    private LocalDate criadoEm;

    public ClienteBuilder comId(int id) {
        this.id = id;
        return this;
    }

    public ClienteBuilder comNome(String nome) {
        this.nome = nome;
        return this;
    }

    public ClienteBuilder comEmail(String email) {
        this.email = email;
        return this;
    }

    public ClienteBuilder comTelefone(String telefone) {
        this.telefone = telefone;
        return this;
    }

    public ClienteBuilder comCpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    public ClienteBuilder comCep(String cep) {
        this.cep = cep;
        return this;
    }

    public ClienteBuilder comEndereco(String endereco) {
        this.endereco = endereco;
        return this;
    }

    public ClienteBuilder comSexo(String sexo) {
        if(sexo.equals("M") || sexo.equals("F")) {
            this.sexo = sexo;
        }
        return this;
    }

    public ClienteBuilder comDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
        return this;
    }

    public ClienteBuilder comCriadoEm(LocalDate criadoEm) {
        this.criadoEm = criadoEm != null ? criadoEm : LocalDate.now();
        return this;
    }

    public Cliente constroi() {
        return new Cliente(id, nome, email, telefone, cpf, cep, endereco, sexo, dataNascimento, criadoEm);
    }
}
