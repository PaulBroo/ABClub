package com.example.uiteste.Data;

import java.io.Serializable;

public class Utilizador implements Serializable {
    private String username;
    private String password;
    private String nome;

    public Utilizador(String username, String password, String nome) {
        this.username = username;
        this.password = password;
        this.nome = nome;
    }

    public String getusername() {
        return username;
    }

    public String getpassword() {
        return password;
    }

    public String getnome() {
        return nome;
    }

    public void setusername(String username) {
        this.username = username;
    }

    public void setpassword(String password) {
        this.password = password;
    }

    public void setnome(String nome) {
        this.nome = nome;
    }
}

