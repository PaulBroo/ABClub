package com.example.uiteste.Data;

public class Aviso {

    String id, Titulo, Descricao;
    Boolean selecionado;

    public Aviso(){

    }

    public Aviso(String id, String titulo, String descricao){
        this.id = id;
        Titulo = titulo;
        Descricao = descricao;
        selecionado = false;
    }

    public Boolean getSelecionado() { return selecionado; }

    public void setSelecionado(Boolean selecionado) { this.selecionado = selecionado; }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getTitulo() { return Titulo; }

    public void setTitulo(String titulo) { Titulo = titulo; }

    public String getDescricao() { return Descricao; }

    public void setDescricao(String descricao) { Descricao = descricao; }

    public String toString(){
        return "Título: " + Titulo + "\n" + "Descrição: " + Descricao;
    }
}
