package backend; 

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Stock implements Serializable {
    HashMap<Vacina, Double> stock;

    public Stock() {
        this.stock = new HashMap<>();
    }
 
     public class StockIndisponivelException extends Exception {
        public StockIndisponivelException() { }
        public StockIndisponivelException(String message) {
            super(message);
        }
    }
    
    public void adicionar(Vacina vacina, double quantidade) {
        if (stock.containsKey(vacina)) {
            stock.put(vacina, stock.get(vacina) + quantidade );
        }
            else {
                stock.put(vacina, quantidade);
            }
        }
    
    public void remover(Vacina vacina, double quantidade) throws StockIndisponivelException {
        if (!(stock.containsKey(vacina))) {
            System.err.print("Não existe registo de stock desta vacina");
        }
        else {
            if (quantidade > stock.get(vacina)) {
                throw new StockIndisponivelException("Quantidade a remover maior do que a disponivel");
            }
            else {
                stock.put(vacina, stock.get(vacina) - quantidade);
            }
        } 
    }

    public double getStockDisponivel (Vacina vacina) {
        return stock.get(vacina);
    }
    
    public int size() {
        return stock.size();
    }
    
    public HashMap getHashMap () {
        return stock;
    }
    
    public ArrayList getVacinas() {
        return new ArrayList<>(stock.keySet());
    }
    
     public ArrayList getNomeVacinas () {
        ArrayList nomes = new ArrayList<String>();
        for (Map.Entry loop : stock.entrySet()) {
            Vacina vacina = (Vacina) loop.getKey(); {
            nomes.add(vacina.getNome());
            }
        }
        return nomes;   
    }
}
    
// arraylist com keys
// e ir buscar essas keys 
