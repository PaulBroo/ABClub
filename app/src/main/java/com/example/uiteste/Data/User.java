package com.example.uiteste.Data;

public class User {

    String Primeiro_Nome, Ultimo_Nome, escalao, role, email;
    int dia, mes, ano;

    public User() {

    }

    public User(String pnome, String unome, String escalao, String role, String email, int dia, int mes, int ano) {
        Primeiro_Nome = pnome;
        Ultimo_Nome = unome;
        this.escalao = escalao;
        this.role = role;
        this.email = email;
        this.dia = dia;
        this.mes = mes;
        this.ano = ano;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPrimeiro_Nome() {
        return Primeiro_Nome;
    }

    public void setPrimeiro_Nome(String pnome) {
        Primeiro_Nome = pnome;
    }

    public String getUltimo_Nome() {
        return Ultimo_Nome;
    }

    public void setUltimo_Nome(String unome) {
        Ultimo_Nome = unome;
    }

    public String getEscalao() {
        return escalao;
    }

    public void setEscalao(String escalao) {
        this.escalao = escalao;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }
}
