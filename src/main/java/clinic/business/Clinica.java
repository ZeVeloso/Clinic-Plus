package clinic.business;

public class Clinica {

    private Integer id;
    private String nome;

    public Clinica(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Clinica ").append(id);
        sb.append(" | ").append(nome);
        return sb.toString();
    }

}
