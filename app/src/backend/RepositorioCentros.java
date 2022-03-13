package backend;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class RepositorioCentros implements Serializable{
    private HashMap<String, CentroVacinacao> Centros;

    public RepositorioCentros() {
    this.Centros = new HashMap<>();
    
}
    
    public class CentroDuplicadoException extends Exception {
        public CentroDuplicadoException() { }
        public CentroDuplicadoException(String message) {
            super(message);
        }        
    }
    
    public void addCentro(CentroVacinacao centro) throws CentroDuplicadoException {
        if (centro == null) {
            throw new NullPointerException("O valor 'CentroVacinacao' nao pode ser nulo");
        }

        if(!Centros.containsKey(centro.getCodigo())) {
            Centros.put(centro.getCodigo(), centro);
        }
        else {
            throw new CentroDuplicadoException(String.format("O centro '%s' já está adicionado", centro.getCodigo()));
        }
    }    
    
    public void removerCentro(String codigo) {
        if (Centros.containsKey(codigo)){
            Centros.remove(codigo);
        }
    }
    
    public boolean existe(String codigo) {
        return Centros.containsKey(codigo);
    }
    
    public CentroVacinacao FiltrarPorLocalidade (String localidade) {
        for (Map.Entry loop : Centros.entrySet()) {
            CentroVacinacao centro = (CentroVacinacao) loop.getValue();
          if (centro.getLocalidade() == localidade) {
              return (CentroVacinacao) loop.getValue();
          }
        }
        return null;   
    }
    
    public int size(){
        return Centros.size();
    }
    
    public HashMap getCentros() {
        return Centros;
    }
    
    public ArrayList getTodos() {
        return new ArrayList<>(Centros.values());
    }
    
    public CentroVacinacao getCentro(String codigo) {
        return Centros.get(codigo);
    }
    
    public ArrayList getLocalidadeTodos () {
        ArrayList localidades = new ArrayList<String>();
        for (Map.Entry loop : Centros.entrySet()) {
            CentroVacinacao centro = (CentroVacinacao) loop.getValue(); {
            localidades.add(centro.getLocalidade());
            }
        }
        return localidades; 
    }
   
    public int getNumeroDeVacinacoes() {
        int NumeroDeVacinasDadas = 0;
        for (Map.Entry loop : Centros.entrySet()) {
            CentroVacinacao centro = (CentroVacinacao) loop.getValue();
        NumeroDeVacinasDadas += centro.getNumeroDeVacinasDadas();
        }
        return NumeroDeVacinasDadas;
    }
    
    public ArrayList getEfeitosSecundariosTodos() {
        Set<String> EfeitosSecundarios = new LinkedHashSet();
        for (Map.Entry loop : Centros.entrySet()) {
            CentroVacinacao centro = (CentroVacinacao) loop.getValue();
            EfeitosSecundarios.addAll(centro.getEfeitosSecundarios());
        }
        return new ArrayList<String>(EfeitosSecundarios);
    }
}




