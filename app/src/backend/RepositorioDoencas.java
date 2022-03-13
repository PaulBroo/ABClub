package backend;

import java.io.Serializable;
import java.util.ArrayList;

public class RepositorioDoencas implements Serializable {
    private ArrayList<String> lista;
    
     public RepositorioDoencas() {
        this.lista = new ArrayList<>();        
    }

    
    public class DoencaDuplicadaException extends Exception {
        public DoencaDuplicadaException() { }
        public DoencaDuplicadaException(String message) {
            super(message);
        }
    }
    
    public class DoencaNaoExistenteException extends Exception {
        public DoencaNaoExistenteException() { }
        public DoencaNaoExistenteException(String message) {
            super(message);
        }
    }

    public void addDoenca(String doenca) throws DoencaDuplicadaException {
        if (doenca == null) {
            throw new NullPointerException("O valor 'Doenca' nao pode ser nulo");
        }
        
        if (lista.contains(doenca)) {
            throw new DoencaDuplicadaException(String.format("A doenca '%s' já está adicionada", doenca));
        }
        
        else {
            lista.add(doenca);
        }
    }
    
    public void removeDoenca(String doenca) throws DoencaNaoExistenteException {
        if (!lista.contains(doenca)) {
            throw new DoencaNaoExistenteException(String.format("A doenca '%s' nao existe", doenca));
        }
        else {
           lista.remove(doenca);
        }
    }
    
    public boolean existe(String doenca) {
        return lista.contains(doenca);
    }
    
    public ArrayList getDoencas() {
        return lista;
    }
    
    public int size() {
        return lista.size();
    }
}




