package backend;

import java.io.Serializable;
import java.util.ArrayList;

public class Gestor extends Utilizador implements Serializable {
    private CentroVacinacao CentroVacAtribuido;
 
    
    public Gestor(String username, String password, String nome) {
        super(username, password, nome);
        
    }
  
    public CentroVacinacao getCentro () {
        return CentroVacAtribuido;
    }
    
    public void setCentro (CentroVacinacao centro) {
        CentroVacAtribuido = centro;
    }
    
    
}
