package clinic.Helpers;

public class UtenteConsultaClinica {

    private Integer idUtente;
    private Integer idConsulta;
    private Integer idClinica;
    private String nomeUtente;
    private String nomeClinica;
    private String dataConsulta;
    private String motivoConsulta;
    private float custoConsulta;
    private String estadoConsulta;
    private int telemovelUtente;
    private String moradaUtente;
    private String dataNascimentoUtente;
    private int idadeUtente;

    public UtenteConsultaClinica(Integer idUtente, Integer idConsulta, Integer idClinica, String nomeUtente,
                                 String nomeClinica, String dataConsulta, String motivoConsulta, float custoConsulta,
                                 int telemovelUtente, String moradaUtente, String dataNascimentoUtente, int idadeUtente) {
        this.idUtente = idUtente;
        this.idConsulta = idConsulta;
        this.idClinica = idClinica;
        this.nomeUtente = nomeUtente;
        this.nomeClinica = nomeClinica;
        this.dataConsulta = dataConsulta;
        this.motivoConsulta = motivoConsulta;
        this.custoConsulta = custoConsulta;
        this.telemovelUtente = telemovelUtente;
        this.moradaUtente = moradaUtente;
        this.dataNascimentoUtente = dataNascimentoUtente;
        this.idadeUtente = idadeUtente;
    }

    public UtenteConsultaClinica(Integer idUtente, String nomeUtente, String nomeClinica, int telemovelUtente,
                                 String moradaUtente, String dataNascimentoUtente, int idadeUtente) {
        this.idUtente = idUtente;
        this.idConsulta = null;
        this.idClinica = null;
        this.nomeUtente = nomeUtente;
        this.nomeClinica = nomeClinica;
        this.dataConsulta = "";
        this.motivoConsulta = "";
        this.custoConsulta = 0 ;
        this.telemovelUtente = telemovelUtente;
        this.moradaUtente = moradaUtente;
        this.dataNascimentoUtente = dataNascimentoUtente;
        this.idadeUtente = idadeUtente;
    }


    public UtenteConsultaClinica(Integer idUtente, Integer idConsulta, String nomeUtente,
                                 String nomeClinica, String dataConsulta, String motivoConsulta, float custoConsulta,
                                 int telemovelUtente, String dataNascimentoUtente, int idadeUtente, String estadoConsulta) {
        this.idUtente = idUtente;
        this.idConsulta = idConsulta;
        this.idClinica = null;
        this.nomeUtente = nomeUtente;
        this.nomeClinica = nomeClinica;
        this.dataConsulta = dataConsulta;
        this.motivoConsulta = motivoConsulta;
        this.custoConsulta = custoConsulta;
        this.telemovelUtente = telemovelUtente;
        this.moradaUtente = "";
        this.dataNascimentoUtente = dataNascimentoUtente;
        this.idadeUtente = idadeUtente;
        this.estadoConsulta = estadoConsulta;
    }

    public String getEstadoConsulta() {
        return estadoConsulta;
    }

    public void setEstadoConsulta(String estadoConsulta) {
        this.estadoConsulta = estadoConsulta;
    }

    public Integer getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(Integer idUtente) {
        this.idUtente = idUtente;
    }

    public Integer getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(Integer idConsulta) {
        this.idConsulta = idConsulta;
    }

    public Integer getIdClinica() {
        return idClinica;
    }

    public void setIdClinica(Integer idClinica) {
        this.idClinica = idClinica;
    }

    public String getNomeUtente() {
        return nomeUtente;
    }

    public void setNomeUtente(String nomeUtente) {
        this.nomeUtente = nomeUtente;
    }

    public String getNomeClinica() {
        return nomeClinica;
    }

    public void setNomeClinica(String nomeClinica) {
        this.nomeClinica = nomeClinica;
    }

    public String getDataConsulta() {
        return dataConsulta;
    }

    public void setDataConsulta(String dataConsulta) {
        this.dataConsulta = dataConsulta;
    }

    public String getMotivoConsulta() {
        return motivoConsulta;
    }

    public void setMotivoConsulta(String motivoConsulta) {
        this.motivoConsulta = motivoConsulta;
    }

    public float getCustoConsulta() {
        return custoConsulta;
    }

    public void setCustoConsulta(float custoConsulta) {
        this.custoConsulta = custoConsulta;
    }

    public int getTelemovelUtente() {
        return telemovelUtente;
    }

    public void setTelemovelUtente(int telemovelUtente) {
        this.telemovelUtente = telemovelUtente;
    }

    public String getMoradaUtente() {
        return moradaUtente;
    }

    public void setMoradaUtente(String moradaUtente) {
        this.moradaUtente = moradaUtente;
    }

    public String getDataNascimentoUtente() {
        return dataNascimentoUtente;
    }

    public void setDataNascimentoUtente(String dataNascimentoUtente) {
        this.dataNascimentoUtente = dataNascimentoUtente;
    }

    public int getIdadeUtente() {
        return idadeUtente;
    }

    public void setIdadeUtente(int idadeUtente) {
        this.idadeUtente = idadeUtente;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UtenteConsultaClinica{");
        sb.append("idUtente=").append(idUtente);
        sb.append(", idConsulta=").append(idConsulta);
        sb.append(", idClinica=").append(idClinica);
        sb.append(", nomeUtente='").append(nomeUtente).append('\'');
        sb.append(", nomeClinica='").append(nomeClinica).append('\'');
        sb.append(", dataConsulta='").append(dataConsulta).append('\'');
        sb.append(", motivoConsulta='").append(motivoConsulta).append('\'');
        sb.append(", custoConsulta=").append(custoConsulta);
        sb.append(", telemovelUtente=").append(telemovelUtente);
        sb.append(", moradaUtente='").append(moradaUtente).append('\'');
        sb.append(", dataNascimentoUtente='").append(dataNascimentoUtente).append('\'');
        sb.append(", idadeUtente=").append(idadeUtente);
        sb.append('}');
        return sb.toString();
    }
}
