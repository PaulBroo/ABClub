package backend;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RepositorioVacinas implements Serializable {
    HashMap<String, Vacina> listaVacinas;

    public RepositorioVacinas() {
    listaVacinas = new HashMap();
}

    public class VacinaDuplicadaException extends Exception {
        public VacinaDuplicadaException() { }
        public VacinaDuplicadaException(String message) {
            super(message);
        }        
    }
    
    public void addVacina(Vacina vacina) throws VacinaDuplicadaException {
        if (vacina == null) {
            throw new NullPointerException("O valor 'CentroVacinacao' nao pode ser nulo");
        }

        if(!listaVacinas.containsKey(vacina.getCodigo())) {
            listaVacinas.put(vacina.getCodigo(), vacina);
        }
        else {
            throw new VacinaDuplicadaException(String.format("O centro '%s' já está adicionado", vacina.getCodigo()));
        }
    }    
    
    public void removerVacina (String codigo) {
        if (listaVacinas.containsKey(codigo)) {
            listaVacinas.remove(codigo);
        }
    }
    public boolean existe(String codigo) {
        return listaVacinas.containsKey(codigo);
    }
    
    public int size(){
        return listaVacinas.size();
    }
    
    public HashMap getVacinas() {
        return listaVacinas;
    }
    
    public ArrayList getTodos() {
        return new ArrayList<>(listaVacinas.values());
    }
    
    public Vacina getVacina(String codigo) {
        return listaVacinas.get(codigo);
    }
    
    public Vacina FiltrarPorNome (String nome) {
        for (Map.Entry loop : listaVacinas.entrySet()) {
            Vacina vacina = (Vacina) loop.getValue();
          if (vacina.getNome() == nome) {
              return (Vacina) loop.getValue();
          }
        }
        return null;   
    }
    
}




