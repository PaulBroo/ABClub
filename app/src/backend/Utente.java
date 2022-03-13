package backend;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import backend.RepositorioCentros;
import java.io.Serializable;
import java.time.Period;
import java.util.HashMap;

public class Utente extends Utilizador implements Serializable {
    
    private String n_sns;
    private String morada;
    private String localidade;
    private LocalDate data_nascimento;
    private String telefone;
    private String email;
    private RepositorioDoencas doencas;
    private Vacina tipoVacina;
    private String efeitosObservados;
    private CentroVacinacao centroAtribuido;
    private boolean PrimeiraDoseTomada;
    private boolean TomouTodasDoses;
    private int NumTomasTomadas;
    //Necessário para converter String para data de nascimento

    public Utente(String username, String password, String nome) {
        super(username, password, nome);
        doencas = new RepositorioDoencas();
        centroAtribuido = null;
        NumTomasTomadas = 0;
        tipoVacina = null;
        
    }


public void setSNS(String SNS){
    this.n_sns = SNS;
}

public void setMorada(String morada) {
    this.morada = morada;
}

public void setlocalidade (String localidade) {
    this.localidade = localidade;
}

public void setDataNascimento (LocalDate DataNascimento) {
    this.data_nascimento = DataNascimento; 
}

public void setTelefone (String telefone) {
    this.telefone = telefone;
}

public void setemail (String email) {
    this.email = email;
}

public String getSNS() {
    return n_sns;
}

public String getMorada() {
    return morada;
}

public String getLocalidade() {
    return localidade;
}

public LocalDate getDataNascimento() {
    return data_nascimento;
}

public String getTelefone() {
    return morada;
}

public String getemail() {
    return email;
}

public String getEfeitosObservados() {
    return efeitosObservados;
}

public void setEfeitosObservados(String efeitosObservados) {
    this.efeitosObservados = efeitosObservados;
}

public void setTipoVacina (Vacina vacina) {
    tipoVacina = vacina;
}

public Vacina getTipoVacina () {
    return tipoVacina;
}

public void setCentro (CentroVacinacao centro) {
    this.centroAtribuido = centro;
}

public CentroVacinacao getCentro () {
    return centroAtribuido;
}

public RepositorioDoencas getDoencas() {
    return doencas;
}

public boolean TomouPrimeiraDose() {
    return PrimeiraDoseTomada;
}

public boolean TomouTodasDoses() {
    return TomouTodasDoses;
}

public int getTomasTomadas() {
    return NumTomasTomadas;
}

public void DoseTomada() {
    NumTomasTomadas += 1;
    if (!PrimeiraDoseTomada) {
        PrimeiraDoseTomada = true;
    }
    if (tipoVacina.getNumTomas() == NumTomasTomadas) {
        TomouTodasDoses = true;
    }
}

public int getIdade() {
    Period diferenca = Period.between(data_nascimento, LocalDate.now());
    return diferenca.getYears();
}

}
