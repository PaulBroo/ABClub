package backend;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class CentroVacinacao implements Serializable {
    private String codigo;
    private String morada;
    private String localidade;
    private int numpostos;
    private Gestor GestorAtribuido;
    private ArrayList<Utente> UtentesAtribuidos;
    private Set EfeitosSecundarios;
    private Stock stock;
    private Agenda agenda;
    private int NumeroDeVacinasDadas;
    
    public CentroVacinacao (Gestor gestor, String codigo, String localidade) {
    stock = new Stock();
    UtentesAtribuidos = new ArrayList();
    EfeitosSecundarios = new LinkedHashSet();
    GestorAtribuido = gestor;
    numpostos = 1;
    NumeroDeVacinasDadas = 0;
    agenda = new Agenda();
    this.codigo = codigo;
    this.localidade = localidade;
}
    
    
    public class UtenteDuplicadoException extends Exception {
        public UtenteDuplicadoException() { }
        public UtenteDuplicadoException(String message) {
            super(message);
        }        
    }
public void removerGestor() {
    GestorAtribuido = null;
}
    
public void setGestor(Gestor gestor) {
    if (!(GestorAtribuido == null)) {
        GestorAtribuido.setCentro(null);
    }
    
    if (!(gestor.getCentro() == null)) {
        gestor.getCentro().removerGestor();
    }
    GestorAtribuido = gestor;
    gestor.setCentro(this);
}

public void setCodigo(String codigo) {
    this.codigo = codigo;
}

public void setMorada (String morada) {
    this.morada = morada;
}

public void setLocalidade (String localidade) {
    this.localidade = localidade;
}

public void setNumPostos (int num) {
    this.numpostos = num;
}


public String getCodigo() {
    return codigo;
}

public String getMorada() {
    return morada;
}

public String getLocalidade() {
    return localidade;
}

public int getNumPostos() {
    return numpostos;
}

public Gestor getGestor() {
    return GestorAtribuido;
}

public void AddUtente(Utente utente) throws UtenteDuplicadoException {
    if (!(UtentesAtribuidos.contains(utente))) {
       UtentesAtribuidos.add(utente); 
    }
    else {
        throw new UtenteDuplicadoException(String.format("O utente '%s' já está adicionado", utente.getnome()));
    }
}

public ArrayList getListaUtentes () {
    return UtentesAtribuidos;
}

public int sizeListaUtentes () {
    return UtentesAtribuidos.size();
}

public Stock getStock() {
    return stock;
}

public Agenda getAgenda() {
    return agenda;
}

public void addEfeitosSecundarios(String efeito) {
    EfeitosSecundarios.add(efeito);
}

public ArrayList getEfeitosSecundarios() {
    return new ArrayList(EfeitosSecundarios);
}
public void AdicionarVacinacaoEstatistica() {
        NumeroDeVacinasDadas += 1;
    }

public int getNumeroDeVacinasDadas() {
    return NumeroDeVacinasDadas;
}

public int getNUtentesComPrimeiraDose() {
    int NumeroUtentes = 0;
    for (Utente utente : UtentesAtribuidos) {
        if (utente.TomouPrimeiraDose()) {
            NumeroUtentes ++;   
        }    
    }
    return NumeroUtentes;
}

public int getNUtentesComTodasDoses() {
    int NumeroUtentes = 0;
    for (Utente utente : UtentesAtribuidos) {
        if (utente.TomouTodasDoses())
            NumeroUtentes ++;     
    }
    return NumeroUtentes;
}

public int getNUtentesAtribuidos() {
    return UtentesAtribuidos.size();
}
}



