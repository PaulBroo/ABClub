/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import static java.util.Objects.isNull;

public class Agenda implements Serializable {

    private HashMap<LocalDate, ArrayList<Utente>> agendamentos;
    int maxAgendamentoDia;

    public Agenda() {
        this.agendamentos = new HashMap<>();
        this.maxAgendamentoDia = 10;
    }
   
    public HashMap<LocalDate, ArrayList<Utente>> getAgendamentos() {
        return agendamentos;
    }

    public int getMaxAgendamentosDia() {
        return maxAgendamentoDia;
    }

    public void setMaxAgendamentosDia(int Max) {
        this.maxAgendamentoDia = Max;
    }
    
    public ArrayList getAusentes() {
        ArrayList<Utente> listaAusentes = new ArrayList();
        for (LocalDate data : agendamentos.keySet()){
          if (data.isBefore(LocalDate.now()))
              listaAusentes.addAll(getAgendamentosDoDia(data));
        }
        return listaAusentes;
    }

    public void cancelar(LocalDate data, Utente utente) {
        ArrayList<Utente> listaUtilizadoresData = agendamentos.get(data);
        if((listaUtilizadoresData) == null || listaUtilizadoresData.isEmpty()) {
            return;
        }
        if(listaUtilizadoresData.contains(utente)) {
                listaUtilizadoresData.remove(utente);
                agendamentos.replace(data, listaUtilizadoresData);
        }
    }
    
    public void RemoverMarcacoesPassadas(Utente utente) {
        ArrayList<Utente> lista = agendamentos.get(getAgendamentoDoUtilizador(utente));
        lista.remove(utente);
        agendamentos.replace(getAgendamentoDoUtilizador(utente), lista);
    }

    public boolean disponivel(LocalDate data) {
        return (isNull(agendamentos.get(data)) || (agendamentos.get(data).size() < maxAgendamentoDia));
    }

    public void agendar(LocalDate data, Utente utente) {
            ArrayList<Utente> lista = new ArrayList<>();
            if (agendamentos.get(data) != null) {
                lista = agendamentos.get(data);
                if (lista.size() >= maxAgendamentoDia) {
                    return;
                }
            }
                lista.add(utente);
                agendamentos.put(data, lista);
        }

    public ArrayList getAgendamentosDoDia (LocalDate data) {
        return agendamentos.get(data);
    }
    
    public LocalDate getAgendamentoDoUtilizador(Utente utente) {
        for (LocalDate data : agendamentos.keySet()){
          for(Utente u : agendamentos.get(data)) {
              if(u == utente) {
                  return data;
                }
            }
        }
        return null;
    }
    
    public boolean TemAgendamento (Utente utente) {
        for (LocalDate data : agendamentos.keySet()){
          for(Utente u : agendamentos.get(data)) {
              if(u == utente) {
                  return true; 
              }
            }
        }
        return false;
    }
}