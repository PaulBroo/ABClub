package com.example.uiteste.Data;

import java.util.Date;

public class Evento {
    String tipo, pavilhao;
    String hora;

    public Evento(String tipo, String pavilhao, String hora) {
        this.tipo = tipo;
        this.pavilhao = pavilhao;
        this.hora = hora;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getPavilhao() {
        return pavilhao;
    }

    public void setPavilhao(String pavilhao) {
        this.pavilhao = pavilhao;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
