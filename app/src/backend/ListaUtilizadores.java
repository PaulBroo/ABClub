package backend;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListaUtilizadores implements Serializable {

   HashMap<String, Utilizador> lista;
   
   public class UtilizadorDuplicadoException extends Exception {
        public UtilizadorDuplicadoException() { }
        public UtilizadorDuplicadoException(String message) {
            super(message);
        }        
    }
   
   public class UtilizadorNaoExistenteException extends Exception {
        public UtilizadorNaoExistenteException() { }
        public UtilizadorNaoExistenteException(String message) {
            super(message);
        }        
    }
   
   public ListaUtilizadores () {
    this.lista = new HashMap<>();
}

   public void addUser (Utilizador utilizador) throws UtilizadorDuplicadoException {
       if (utilizador == null) {
           throw new NullPointerException("O valor utilizador não pode ser nulo");
       }
       
       if(!lista.containsKey(utilizador.getusername())) {
           lista.put(utilizador.getusername(), utilizador);
       }
       else {
           throw new UtilizadorDuplicadoException(String.format("O utilizador '%s' já existe na coleção", utilizador.getusername()));
       }
   }
   
  public Utilizador getUtilizador(String username) throws UtilizadorNaoExistenteException {
        if (lista.containsKey(username)){
            return lista.get(username);
        }else{
            throw new UtilizadorNaoExistenteException("O utilizador '%s' já existe na lista");
        } 
  }
  
  public HashMap getHashmap () {
      return lista;
  }
  
  public boolean existe(String username) {
      return lista.containsKey(username);
  }
  
   public int size() {
        return lista.size();
    }
   
   public ArrayList<Utilizador> todos() {
        return new ArrayList<>(lista.values());
    }
   
   public void removerUtilizador(String username) {
       if (existe(username)) {
           lista.remove(username);
       }
   }
   
   public ArrayList getGestores() {
       ArrayList<Gestor> listaG = new ArrayList();
        for (Utilizador user : lista.values()){
          if(user instanceof Gestor) {
              listaG.add((Gestor) user);
          }
        }
        return listaG;
   }
           
}
