package clinic.business;

public class Consulta {

    private Integer id;
    private String data;
    private float custo;
    private String obs;
    private String estado;
    private String motivo;
    private Integer utenteID;

    public Consulta(){
        this.id=0;
        this.data="";
        this.custo=2;
        this.obs="";
        this.estado="";
        this.utenteID=0;
        this.motivo = "";
    }

    public Consulta(Integer id, String data){
        this.id=null;
        this.data=data;
        this.custo=1;
        this.obs="";
        this.estado="";
        this.utenteID=id;
        this.motivo = "";
    }

    public Consulta(Integer id, String data, float custo, String obs, String estado, String motivo, Integer utente) {
        this.id = id;
        this.data = data;
        this.custo = custo;
        this.obs=obs;
        this.estado=estado;
        this.utenteID = utente;
        this.motivo = motivo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public float getCusto() {
        return custo;
    }

    public void setCusto(float custo) {
        this.custo = custo;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public Integer getUtente() {
        return utenteID;
    }

    public void setUtente(Integer utente) {
        this.utenteID = utente;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("\nConsulta ").append(id);
        sb.append("| data:'").append(data).append('\'');
        sb.append(", custo:").append(custo);
        sb.append(", utente id:").append(utenteID);
        return sb.toString();
    }
}
