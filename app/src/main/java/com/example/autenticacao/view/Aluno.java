package com.example.autenticacao.view;

import java.util.UUID;

public class Aluno {
    private String Id;
    private String RA;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    private String Nome;
    private String Endereco;

    public Aluno(String RA, String nome, String endereco) {
        this.RA = RA;
        Nome = nome;
        Endereco = endereco;
        this.Id = UUID.randomUUID().toString();
    }

    public String getRA() {
        return RA;
    }

    public void setRA(String RA) {
        this.RA = RA;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getEndereco() {
        return Endereco;
    }

    public void setEndereco(String endereco) {
        Endereco = endereco;
    }

    @Override
    public String toString() {
        return RA
                + " - " + Nome
                + " - " + Endereco;
    }

    public Aluno() {
    }
}