package clinic.business;

import clinic.Helpers.Pair;
import clinic.Helpers.UtenteConsultaClinica;
import clinic.data.*;
import clinic.view.Helpers.UtenteClinicaHelper;
import javafx.scene.image.Image;

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
    private final Map<Integer, Documento> documentos;
    private ConfigDAO configs;
    private QueryDAO auxDAO;

    public ClinicFacade() {
        this.utentes = UtenteDAO.getInstance();
        this.consultas = ConsultaDAO.getInstance();
        this.clinicas = ClinicaDAO.getInstance();
        this.documentos = DocumentoDao.getInstance();
        this.auxDAO = new QueryDAO();
        this.configs = new ConfigDAO();
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

    public Collection<UtenteConsultaClinica> getUtentesClinica(){
        return this.auxDAO.getUtentes().stream().sorted(byIdUtente2).collect(Collectors.toList());
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
    public void updateConsultaCalendario(Consulta a){
        this.auxDAO.updateConsultaCalendario(a);
    }
    public Collection<Consulta> getConsultasUtente(int idUtente) {
        return this.auxDAO.getConsulta(idUtente).stream().sorted(bydata).collect(Collectors.toList());
    }

    public void adicionaClinica(Clinica c){
        this.clinicas.put(c.getId(),c);
    }

    public Collection<Clinica> getClinicas() {
        return this.clinicas.values().stream().sorted(byIdClinica).collect(Collectors.toList());
    }

    public Clinica getClinicaDeUtente(int id){
        return this.auxDAO.getClinica(id);
    }

    public void removeClinica(int id){
        this.clinicas.remove(id);
    }

    public Collection<Utente> getUtentesClinica(int id) {
        return this.auxDAO.getUtentesClinica(id).stream().sorted(byIdUtente).collect(Collectors.toList());
    }

    public String getConfigNome(String nome){
        return configs.getConfigNome(nome);
    }

    public Collection <Pair<Consulta, Utente>> getTudo(){
        return auxDAO.getTudo();
    }

    Comparator<Utente> byIdUtente = Comparator.comparing(Utente::getId);
    Comparator<UtenteConsultaClinica> byIdUtente2 = Comparator.comparing(UtenteConsultaClinica::getIdUtente);
    Comparator<Consulta> bydata = Comparator.comparing(Consulta::getData);
    Comparator<Clinica> byIdClinica = Comparator.comparing(Clinica::getId);

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

    public Collection<Utente> getUtentesFilter(String nome, String telemovel, String nascimento, String morada){
        return this.auxDAO.getUtentesFilter(nome, telemovel, nascimento, morada);
    }

    public Collection<UtenteConsultaClinica> getUtentesConsultaClinicaFilter(String nome, String telemovel, String nascimento, String morada){
        return this.auxDAO.getUtentesConsultaClinicaFilter(nome, telemovel, nascimento, morada);
    }

    public Collection<Clinica> getClinicaFilter(String nome){
        return this.auxDAO.getClinicaFilter(nome);
    }

    public Image getDoc(int a){
        return this.auxDAO.getDoc(a);
    }

    public Collection<Documento> getDocumentos(int idUtente){
        return this.auxDAO.getDocumentos(idUtente);
    }

    public void putDoc(Documento d, String path){
        this.auxDAO.putDoc(d,path);
    }

}
