package backend;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Paulo
 */

public class Vacina implements Serializable{
    private String nome;
    private String codigo;
    private String fabricante;
    private int doseindividual;
    private int idadeMin;
    private int idadeMax;
    private int numTomas;
    private ArrayList<String> efeitosSecundarios;
    private int MinEntreTomas;
    private int MaxEntreTomas;

    public Vacina(String nome, String codigo, String fabricante) {
        this.nome = nome;
        this.codigo = codigo;
        this.fabricante = fabricante;
        doseindividual = 1;
        idadeMin = 0;
        idadeMax = 200;
        numTomas = 2;
        efeitosSecundarios = new ArrayList();
        MinEntreTomas = 0;
        MaxEntreTomas = 99999;
    }

    public void setNome (String nome) {
        this.nome = nome;        
}
    public String getNome () {
        return nome;
    }
    
    public void setCodigo (String codigo) {
        this.codigo = codigo;
    }
    
    public String getCodigo () {
        return codigo;
    }
    
    public void setFabricante(String fab) {
        fabricante = fab;
    }
    
    public String getFabricante () {
        return fabricante;
    }
    
    public void setDoseInd(int dose) {
        doseindividual = dose;
    }
    
    public int getDoseInd() {
        return doseindividual;
    }
    
    public void setIdadeMinima(int idade) {
        idadeMin = idade;
    }
    
    public int getIdadeMinima() {
        return idadeMin;
    }
    
    public void setIdadeMaxima(int idade) {
        idadeMax = idade;
    }
    
    public int getIdadeMaxima() {
        return idadeMax;
    }
    
    public void setNumTomas(int num) {
        numTomas = num;
    }
    
    public int getNumTomas() {
        return numTomas;
    }
    
    public void setEfeitosSecundarios(ArrayList efeitos) {
        efeitosSecundarios = efeitos;
    }
    
    public ArrayList getEfeitosSecundarios () {
        return efeitosSecundarios;
    }
    
    public void setMinentreTomas(int max) {
        MinEntreTomas = max;
    }
    
    public int getMinentreTomas () {
        return MinEntreTomas;
    }
    
    public void setMaxentreTomas (int min) {
        MaxEntreTomas = min;
    }
    
    public int getMaxentreTomas() {
        return MaxEntreTomas;
    }
    
    

}
