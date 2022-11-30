package com.example.autenticacao.view.models;

import java.util.UUID;

public class Aluno {

    private String uid;
    private String RA;
    private String Nome;
    private String Endereco;

    public Aluno() {
        this.uid = UUID.randomUUID().toString();
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return RA
                + " - " + Nome
                + " - " + Endereco;
    }
}