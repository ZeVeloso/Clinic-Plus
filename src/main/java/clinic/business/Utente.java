package clinic.business;

public class Utente {

    private Integer id;
    private String nascimento;
    private int telemovel;
    private int idade;
    private String profissao;
    private String historico_familiar;
    private String historico_pessoal;
    private String nome;
    private String atividade_fisica;
    private String morada;
    private Integer clinicaID;


    public Utente(int id, String data, int telemovel, int idade, String profissao, String historico_familiar,
                  String historico_pessoal, String nome, String atividade_fisica, String morada, int clinicaID) {
        this.id = id;
        this.nascimento = data;
        this.telemovel = telemovel;
        this.idade = idade;
        this.profissao = profissao;
        this.historico_familiar = historico_familiar;
        this.historico_pessoal = historico_pessoal;
        this.nome = nome;
        this.atividade_fisica = atividade_fisica;
        this.morada=morada;
        this.clinicaID = clinicaID;
    }
    public Utente(Integer id) {
        this.id = id;
        this.nascimento = "";
        this.telemovel = 0;
        this.idade = 0;
        this.profissao = "";
        this.historico_familiar = "";
        this.historico_pessoal = "";
        this.nome = "";
        this.atividade_fisica = "";
        this.morada="";
    }
    public Utente(Integer id, String nome, int idade, Integer clinicaID) {
        this.id = id;
        this.nascimento = "nasc";
        this.telemovel = 0;
        this.idade = idade;
        this.profissao = "";
        this.historico_familiar = "";
        this.historico_pessoal = "";
        this.nome = nome;
        this.atividade_fisica = "";
        this.morada="";
        this.clinicaID=clinicaID;
    }
    public Utente(Integer id, String nome, int idade, int tel, String morada, String nasc, Integer clinicaID) {
        this.id = id;
        this.nascimento = nasc;
        this.telemovel = tel;
        this.idade = idade;
        this.profissao = "";
        this.historico_familiar = "";
        this.historico_pessoal = "";
        this.nome = nome;
        this.atividade_fisica = "";
        this.morada=morada;
        this.clinicaID=clinicaID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNascimento() {
        return nascimento;
    }

    public void setNascimento(String data) {
        this.nascimento = data;
    }

    public int getTelemovel() {
        return telemovel;
    }

    public void setTelemovel(int telemovel) {
        this.telemovel = telemovel;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public String getHistorico_familiar() {
        return historico_familiar;
    }

    public void setHistorico_familiar(String historico_familiar) {
        this.historico_familiar = historico_familiar;
    }

    public String getHistorico_pessoal() {
        return historico_pessoal;
    }

    public void setHistorico_pessoal(String historico_pessoal) {
        this.historico_pessoal = historico_pessoal;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAtividade_fisica() {
        return atividade_fisica;
    }

    public void setAtividade_fisica(String atividade_fisica) {
        this.atividade_fisica = atividade_fisica;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public Integer getClinicaID() {
        return clinicaID;
    }

    public void setClinicaID(Integer clinicaID) {
        this.clinicaID = clinicaID;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Utente ").append(id);
        sb.append(" | ").append(nome);

        return sb.toString();
    }
}
