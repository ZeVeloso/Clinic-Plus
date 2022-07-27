package clinic.business;

import clinic.Helpers.Pair;
import clinic.Helpers.UtenteConsultaClinica;
import clinic.data.*;
import javafx.scene.image.Image;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;

public class ClinicFacade {

    private final Map<Integer, Utente> utentes;
    private final Map<Integer, Consulta> consultas;
    private final Map<Integer, Clinica> clinicas;
    private final Map<Integer, Documento> documentos;
    private final ConfigDAO configs;
    private final QueryDAO auxDAO;

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

    public int getLastUtente(){ return this.auxDAO.getLastUtente(); }

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
        return this.auxDAO.getConsulta(idUtente).stream().sorted(bydata.reversed()).collect(Collectors.toList());
    }
    public Collection<Consulta> getConsultaFilter(String estado, String data, int idUtente) {
        return auxDAO.getConsultaFilter(estado,data, idUtente).stream().sorted(bydata.reversed()).collect(Collectors.toList());
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
        return ConfigDAO.getConfigNome(nome);
    }

    public Collection <Pair<Consulta, Utente>> getTudo(){
        return auxDAO.getTudo();
    }

    public Collection<Utente> getUtentesFilter(String nome, String telemovel, String nascimento, String morada, String idClinica){
        return this.auxDAO.getUtentesFilter(nome, telemovel, nascimento, morada, idClinica).stream().sorted(byIdUtente).collect(Collectors.toList());
    }

    public Collection<UtenteConsultaClinica> getUtentesConsultaClinicaFilter(String nome, String telemovel, String nascimento, String morada){
        return this.auxDAO.getUtentesConsultaClinicaFilter(nome, telemovel, nascimento, morada).stream().sorted(byIdUtente2).collect(Collectors.toList());
    }

    public Collection<Clinica> getClinicaFilter(String nome){
        return this.auxDAO.getClinicaFilter(nome).stream().sorted(byIdClinica).collect(Collectors.toList());
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

    public Collection<UtenteConsultaClinica> getConsultasUC(){
        return this.auxDAO.getConsultasUC().stream().sorted(bydata2.reversed()).collect(Collectors.toList());
    }

    public Collection<UtenteConsultaClinica> getConsultasUCFilter(String estado, String telemovel, String nomeClinica, String nomeUtente, String data){
        return this.auxDAO.getConsultasUCFilter(estado, telemovel, nomeClinica, nomeUtente, data).stream().sorted(bydata2.reversed()).collect(Collectors.toList());
    }

    public void removeDoc(int id){
        this.documentos.remove(id);
    }
    public float getMoneyMes(String mes, String ano){ return this.auxDAO.getMoneyMes(mes, ano); }
    final Comparator<Utente> byIdUtente = Comparator.comparing(Utente::getId);
    final Comparator<UtenteConsultaClinica> byIdUtente2 = Comparator.comparing(UtenteConsultaClinica::getIdUtente).reversed();


    final Comparator<Clinica> byIdClinica = Comparator.comparing(Clinica::getId);

    final Comparator<Consulta> bydata = Comparator.comparing(o -> LocalDateTime.parse(o.getData(),DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
    final Comparator<UtenteConsultaClinica> bydata2 = Comparator.comparing(o -> LocalDateTime.parse(o.getDataConsulta(), DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));

}
