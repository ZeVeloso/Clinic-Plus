package clinic.business;

import java.sql.Blob;

public class Documento {

    private Integer id;
    private Blob file;
    private String nome;
    private Integer idUtente;

    public Documento(Integer id, Blob file, Integer idUtente) {
        this.id = id;
        this.file = file;
        this.idUtente = idUtente;
    }

    public Documento(Integer id, String nome, Integer idUtente) {
        this.id = id;
        this.nome = nome;
        this.idUtente = idUtente;
    }

    public Documento(Integer id, Blob file, String nome, Integer idUtente) {
        this.id = id;
        this.file = file;
        this.nome = nome;
        this.idUtente = idUtente;
    }

    public Documento(Integer id, int idUtente) {
        this.id = id;
        this.idUtente = idUtente;
    }


    public Integer getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(Integer idUtente) {
        this.idUtente = idUtente;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Blob getFile() {
        return file;
    }

    public void setFile(Blob file) {
        this.file = file;
    }



    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
