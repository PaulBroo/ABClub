package com.example.uiteste.Data;

public class Jogador {

    String Nome, escalao, equipa, id;
    Boolean convocado;

    public Jogador(String Nome, String escalao, String equipa, String id) {
        this.Nome = Nome;
        this.escalao = escalao;
        this.equipa = equipa;
        this.id = id;
        convocado = false;
    }

    public Boolean IsConvocado() {
        return convocado;
    }

    public void setConvocado(Boolean convocado) {
        this.convocado = convocado;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getEscalao() {
        return escalao;
    }

    public void setEscalao(String escalao) {
        this.escalao = escalao;
    }

    public String getEquipa() {
        return equipa;
    }

    public void setEquipa(String equipa) {
        this.equipa = equipa;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String toString() {return Nome + " || " + escalao;}

}

