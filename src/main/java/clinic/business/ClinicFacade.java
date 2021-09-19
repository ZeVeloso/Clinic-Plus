package clinic.business;

import clinic.data.ClinicaDAO;
import clinic.data.ConsultaDAO;
import clinic.data.TesteDao;
import clinic.data.UtenteDAO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

public class ClinicFacade {

    private final Map<Integer, Utente> utentes;
    private final Map<Integer, Consulta> consultas;
    private final Map<Integer, Clinica> clinicas;
    private TesteDao auxDAO;

    public ClinicFacade() {
        this.utentes = UtenteDAO.getInstance();
        this.consultas = ConsultaDAO.getInstance();
        this.clinicas = ClinicaDAO.getInstance();
        this.auxDAO = new TesteDao();
    }

    public void adicionaUtente(Utente u) {
        this.utentes.put(u.getId(), u);
    }

    public void editarUtente(Utente u) {
        this.utentes.put(u.getId(), u);
    }

    public void adicionaConsulta(Consulta u) {
        this.consultas.put(u.getId(), u);
    }

    public Collection<Utente> getUtentes() {
        return this.utentes.values().stream().sorted(byIdUtente).collect(Collectors.toList());
    }

    public Utente getUtente(int id) {
        return this.utentes.get(id);
    }

    public void removeUtente(int id) {
        this.utentes.remove(id);
    }

    public void updateUtente(Utente u){
        this.auxDAO.updateUtente(u.getId(),u);
    }

    public Collection<Consulta> getConsultas() {
        return this.consultas.values().stream().sorted(bydata).collect(Collectors.toList());
    }

    public void editarConsulta(Consulta c) {
        this.consultas.put(c.getId(), c);
    }

    public void removeConsulta(int id) {
        this.consultas.remove(id);
    }

    public Consulta getConsulta(int id) {
        return this.consultas.get(id);
    }

    public void updateConsultaEstado(Integer a){
        this.auxDAO.updateConsultaEstado(a);
    }

    public void updateConsulta(Consulta a){
        this.auxDAO.updateConsulta(a);
    }

    public Collection<Consulta> getConsultasUtente(int idUtente) {
        return this.auxDAO.getConsulta(idUtente).stream().sorted(bydata).collect(Collectors.toList());
    }

    public void adicionaClinica(Clinica c){
        this.clinicas.put(c.getId(),c);
    }

    public Collection<Clinica> getClinicas() {
        return this.clinicas.values();
    }

    public Clinica getClinicaDeUtente(int id){
        return this.auxDAO.getClinica(id);
    }

    public Collection<Utente> getUtentesClinica(int id) {
        return this.auxDAO.getUtentesClinica(id);
    }

    Comparator<Utente> byIdUtente = Comparator.comparing(Utente::getId);
    Comparator<Consulta> bydata = Comparator.comparing(Consulta::getData);

    Comparator<Consulta> dateComparator = (o1, o2) -> {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date o1Date = null;
        Date o2Date = null;
        try {
            o1Date = formatter.parse(o1.getData());
            o2Date = formatter.parse(o2.getData());
        } catch (ParseException pe) {
            pe.printStackTrace();
        }

        return o1Date.compareTo(o2Date);

    };

}
