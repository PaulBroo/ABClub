package backend;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Paulo
 */
public class Sistema implements Serializable {
    private ListaUtilizadores utilizadores;
    private RepositorioCentros centros;
    private RepositorioVacinas vacinas;
    //private final List<RegistoAcesso> listaEntradas;
    private Utilizador utilizadorLigado;

    public Sistema() {
        utilizadores = new ListaUtilizadores();  
        centros = new RepositorioCentros();
        vacinas = new RepositorioVacinas();
    }                       

    public ListaUtilizadores getListaUtilizadores() {
        return utilizadores;
    }
    
    public boolean autenticarUtilizador(String username, String password) {        
        if (utilizadores.existe(username)) {
            try{
                Utilizador u = utilizadores.getUtilizador(username);                
                if (u.getpassword().equals(password)){
                    utilizadorLigado = u;
                    return true;
                }                
            }catch (ListaUtilizadores.UtilizadorNaoExistenteException e) {}                        
        }        
        return false;        
    }
    
    public Utilizador getUtilizadorLigado() {
        return utilizadorLigado;
    }
    
    public RepositorioVacinas getRepositorioVacinas() {
        return vacinas;
    }
    
    public RepositorioCentros getRepositorioCentros() {
        return centros;
    }
    
    public LocalDate ConverterDateParaLocalDate(Date dateparaconverter) {
    return dateparaconverter.toInstant()
      .atZone(ZoneId.systemDefault())
      .toLocalDate();
}
    
    public void inicializar() throws ListaUtilizadores.UtilizadorDuplicadoException, ListaUtilizadores.UtilizadorNaoExistenteException, RepositorioDoencas.DoencaDuplicadaException, RepositorioCentros.CentroDuplicadoException, RepositorioVacinas.VacinaDuplicadaException, CentroVacinacao.UtenteDuplicadoException {
        // Adicionar utilizadores
        utilizadores.addUser(new Administrador("admin", "admin", "Administrador"));
        utilizadores.addUser(new Gestor("user1", "1234", "Gestor"));
        utilizadores.addUser(new Utente("user2", "1234", "Utente"));
        utilizadores.addUser(new Utente("user501", "1234", "Alexandrina"));
        utilizadores.addUser(new Utente("user502", "1234", "Hipólito"));
        utilizadores.addUser(new Utente("user503", "1234", "Alverinda Laurinda"));
        utilizadores.addUser(new Utente("user504", "1234", "Eva Claudina"));
        utilizadores.addUser(new Utente("user505", "1234", "Maria dos Prazeres"));
        
        
        Utente u5 = (Utente) utilizadores.getUtilizador("user2");
        
        // Adiconar doenças
        u5.getDoencas().addDoenca("tuberculose");
        u5.getDoencas().addDoenca("pneumonia");
        u5.getDoencas().addDoenca("diabetes");
        u5.setlocalidade("Gondifelos");
        u5.setDataNascimento(LocalDate.of(2000, 2, 2));
      
        // Adicionar Centros
        centros.addCentro(new CentroVacinacao((Gestor) utilizadores.getUtilizador("user1"), "123" , "Gondifelos"));
        CentroVacinacao c1 = centros.getCentro("123");
        c1.getAgenda().setMaxAgendamentosDia(5);
        c1.setMorada("Rua da Boa Hora");
        c1.setNumPostos(2);  
        
        //Atribuir coisas ao gestor        
        Gestor g5 = (Gestor) utilizadores.getUtilizador("user1");
        g5.setCentro(c1);
        
        // Adicionar Vacinas
        vacinas.addVacina(new Vacina("Astrazeneca", "104", "AstraZeneca"));
        vacinas.addVacina(new Vacina("Pfizer", "942", "Pfizer, inc"));
        vacinas.addVacina(new Vacina("Moderna", "1273", "Moderna, NIAID"));
        
        // Adicionar Stocks ao Centro
        c1.getStock().adicionar(vacinas.getVacina("104"), 540);
        c1.getStock().adicionar(vacinas.getVacina("942"), 1040);
        c1.getStock().adicionar(vacinas.getVacina("1273"), 23000);
        
        // Adicionar Utentes ao Centro
        c1.AddUtente((Utente) utilizadores.getUtilizador("user501"));
        c1.AddUtente((Utente) utilizadores.getUtilizador("user502"));
        c1.AddUtente((Utente) utilizadores.getUtilizador("user503"));
        c1.AddUtente((Utente) utilizadores.getUtilizador("user504"));
        c1.AddUtente((Utente) utilizadores.getUtilizador("user505"));
      
        // Adicionar Centro ao Utente
        Utente u501 = (Utente) utilizadores.getUtilizador("user501");
         u501.setCentro(c1);
         u501.setDataNascimento(LocalDate.of(2000, 1, 1));
        Utente u502 = (Utente) utilizadores.getUtilizador("user502");
         u502.setCentro(c1);
         u502.setDataNascimento(LocalDate.of(2000, 1, 1));
        Utente u503 = (Utente) utilizadores.getUtilizador("user503");
         u503.setCentro(c1);
         u503.setDataNascimento(LocalDate.of(2000, 1, 1));
        Utente u504 = (Utente) utilizadores.getUtilizador("user504");
         u504.setCentro(c1);
         u504.setDataNascimento(LocalDate.of(2000, 1, 1));
        Utente u505 = (Utente) utilizadores.getUtilizador("user505");
         u505.setCentro(c1);
         u505.setDataNascimento(LocalDate.of(2000, 1, 1));
    }
    
    public void terminar() {
        System.exit(0);
    }
    
    
}