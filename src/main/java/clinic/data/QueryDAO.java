package clinic.data;

import clinic.Helpers.Pair;
import clinic.Helpers.UtenteConsultaClinica;
import clinic.business.Clinica;
import clinic.business.Consulta;
import clinic.business.Documento;
import clinic.business.Utente;
import javafx.scene.image.Image;

import java.io.*;
import java.sql.*;
import java.util.Collection;
import java.util.HashSet;

public class QueryDAO implements DAO {

    private final UtentesDAOHelp utentes;
    private final ConsultasDAOHelp consultas;

    public QueryDAO() {
        this.utentes = new UtentesDAOHelp();
        this.consultas = new ConsultasDAOHelp();
    }

    // UTENTES

    public Collection<UtenteConsultaClinica> getUtentes() {
        return utentes.getUtentes();
    }

    public Utente updateUtente(Integer key, Utente a) {
        return utentes.updateUtente(key, a);
    }

    public Collection<Utente> getUtentesFilter(String nome, String telemovel, String nascimento, String morada, String idClinica) {
        return utentes.getUtentesFilter(nome, telemovel, nascimento, morada, idClinica);
    }

    // CONSULTAS

    public Collection<Consulta> getConsulta(int id) {
       return consultas.getConsulta(id);
    }

    public Consulta updateConsultaEstado(Integer key) {
       return consultas.updateConsultaEstado(key);
    }

    public Consulta updateConsulta(Consulta c) {
        return consultas.updateConsulta(c);
    }

    public Consulta updateConsultaCalendario(Consulta c) {
        return consultas.updateConsultaCalendario(c);
    }

    public Collection<Consulta> getConsultaFilter(String estado, String data, int idUtente) {
        return consultas.getConsultaFilter(estado,data,idUtente);
    }

    public float getMoneyMes(String mes, String ano) { return consultas.getMoneyMes(mes, ano); }

    public Collection<UtenteConsultaClinica> getConsultasUC() {
        UtenteConsultaClinica a;
        Collection <UtenteConsultaClinica> col = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery(
                     "SELECT U.id as idUtente, CS.id as idConsulta, U.nome as nomeUtente,dataa, C.nome as nomeClinica, idade, motivo, custo, telemovel, estado, nascimento " +
                             "FROM utentes AS U "+
                             "INNER JOIN clinicas AS C "+
                             "ON U.fk_clinica_id = C.id " +
                             "INNER JOIN consultas AS CS " +
                             "ON U.id = CS.fk_utente_id;")) {
            while (rs.next()) {  // A chave existe na tabela

                a = new UtenteConsultaClinica(rs.getInt("idUtente"), rs.getInt("idConsulta"),
                        rs.getString("nomeUtente"), rs.getString("nomeClinica"),
                        rs.getString("dataa"), rs.getString("motivo"), rs.getFloat("custo"),
                        rs.getInt("telemovel"), rs.getString("nascimento"),  rs.getInt("idade"), rs.getString("estado"));
                col.add(a);
            }

        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return col;
    }

    public Collection<UtenteConsultaClinica> getConsultasUCFilter(String estado, String telemovel, String nomeClinica, String nomeUtente, String data) {
        UtenteConsultaClinica a;
        Collection <UtenteConsultaClinica> col = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery(
                     "SELECT U.id as idUtente, CS.id as idConsulta, U.nome as nomeUtente,dataa, C.nome as nomeClinica, idade, motivo, custo, telemovel, estado, nascimento " +
                             "FROM utentes AS U "+
                             "INNER JOIN clinicas AS C "+
                             "ON U.fk_clinica_id = C.id " +
                             "INNER JOIN consultas AS CS " +
                             "ON U.id = CS.fk_utente_id " +
                             "where estado like '%" + estado +"%'"+
                             " AND telemovel like '%" + telemovel +"%'"+
                             " AND nomeClinica like '%" + nomeClinica +"%'"+
                             " AND nomeClinica like '%" + nomeClinica +"%'"+
                             " AND dataa like '%" + data +"%'"+
                             " AND nomeUtente like '%" + nomeUtente +"%';")) {
            while (rs.next()) {  // A chave existe na tabela

                a = new UtenteConsultaClinica(rs.getInt("idUtente"), rs.getInt("idConsulta"),
                        rs.getString("nomeUtente"), rs.getString("nomeClinica"),
                        rs.getString("dataa"), rs.getString("motivo"), rs.getFloat("custo"),
                        rs.getInt("telemovel"), rs.getString("nascimento"),  rs.getInt("idade"), rs.getString("estado"));
                col.add(a);
            }

        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return col;
    }


    public Collection <Pair<Consulta, Utente>> getTudo() {
        Pair<Consulta, Utente> a;
        Collection <Pair<Consulta, Utente>> col = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery(
                     " SELECT * FROM consultas AS C "+
                         "INNER JOIN utentes AS U "+
                         "ON C.fk_utente_id = U.id")) {
            while (rs.next()) {  // A chave existe na tabela
                a = new Pair<>(new Consulta(rs.getInt("id"), rs.getString("dataa"), rs.getFloat("custo"),
                        rs.getString("obs"), rs.getString("estado"), rs.getString("motivo"), rs.getInt("fk_utente_id")),
                        new Utente(rs.getInt("id"), rs.getString("nascimento"), rs.getInt("telemovel"),
                                rs.getInt("idade"), rs.getString("profissao"), rs.getString("historico_familiar"),
                                rs.getString("historico_pessoal"), rs.getString("nome"),
                                rs.getString("atividade_fisica"), rs.getString("morada"),
                                rs.getInt("fk_clinica_id")));
                col.add(a);
            }

        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return col;
    }

    public Collection<Utente> getUtentesClinica(int id) {
        Collection<Utente> res1 = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM utentes WHERE fk_clinica_id=" + id)) {
            while (rs.next()) {  // A chave existe na tabela

                Utente u = new Utente(rs.getInt("id"), rs.getString("nascimento"), rs.getInt("telemovel"),
                        rs.getInt("idade"), rs.getString("profissao"),rs.getString("historico_familiar"),
                        rs.getString("historico_pessoal"),rs.getString("nome"),
                        rs.getString("atividade_fisica"), rs.getString("morada"),
                        rs.getInt("fk_clinica_id"));
                res1.add(u);
            }

        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res1;

    }

    public Clinica getClinica(int id) {
        Clinica c = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM clinicas WHERE id=" + id)) {
            while (rs.next()) {  // A chave existe na tabela

                c = new Clinica(rs.getInt("id"), rs.getString("nome"));
            }

        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return c;

    }



    public Collection<UtenteConsultaClinica> getUtentesConsultaClinicaFilter(String nome, String telemovel, String nascimento, String morada) {
        Collection<UtenteConsultaClinica> res1 = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery(
                     "SELECT *, U.id as idUtente, U.nome as nomeUtente, C.nome as nomeClinica FROM utentes AS U" +
                             " INNER JOIN clinicas AS C ON U.fk_clinica_id = C.id " +
                             "where U.nome like '%" + nome +"%'"+
                             " AND telemovel like '%" + telemovel +"%'"+
                             " AND nascimento like '%" + nascimento +"%'"+
                             " AND morada like '%" + morada +"%';")) {
            while (rs.next()) {  // A chave existe na tabela

                UtenteConsultaClinica u = new UtenteConsultaClinica(rs.getInt("idUtente"), rs.getString("nomeUtente"),
                        rs.getString("nomeClinica"), rs.getInt("telemovel"),
                        rs.getString("morada"), rs.getString("nascimento"),rs.getInt("idade"));
                res1.add(u);
            }

        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res1;

    }

    public Collection<Clinica> getClinicaFilter(String nome) {
        Collection<Clinica> res1 = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery(
                     "SELECT * FROM clinicas where nome like '%" + nome +"%';")) {
            while (rs.next()) {  // A chave existe na tabela

                Clinica u = new Clinica(rs.getInt("id"), rs.getString("nome"));
                res1.add(u);
            }

        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res1;

    }




    public Documento putDoc(Documento a, String path) {
        Documento res = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("PRAGMA foreign_keys = ON");
            PreparedStatement pstmt = conn.prepareStatement("INSERT OR REPLACE INTO documentos (doc, fk_utente_id, nome, type) " +
                    "VALUES (?,?,?,?)");
            byte[] f = readFile(path);
            //pstmt.setInt(1,a.getId());
            pstmt.setBytes(1, f);
            pstmt.setInt(2,a.getIdUtente());
            pstmt.setString(3, a.getNome());
            pstmt.setString(4, a.getType());
            pstmt.execute();
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }

    private byte[] readFile(String file) {
        ByteArrayOutputStream bos = null;
        try {
            File f = new File(file);
            FileInputStream fis = new FileInputStream(f);
            byte[] buffer = new byte[1024];
            bos = new ByteArrayOutputStream();
            for (int len; (len = fis.read(buffer)) != -1;) {
                bos.write(buffer, 0, len);
            }
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (IOException e2) {
            System.err.println(e2.getMessage());
        }
        return bos != null ? bos.toByteArray() : null;
    }

    public Image getDoc(int key) {
        Image image=null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery(
                     " select * from documentos WHERE id=" + key)) {
            while (rs.next()) {
                InputStream input = rs.getBinaryStream("doc");
                String type = rs.getString("type");
                image = new Image(new BufferedInputStream(input));
                byte[] buffer = new byte[1024];
            }
            //image = new Image(file.getAbsolutePath());

        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return image;
    }

    public Collection<Documento> getDocumentos(int id) {
        Documento a;
        Collection <Documento> col = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery(
                     " SELECT * FROM documentos WHERE fk_utente_id=" + id)) {
            while (rs.next()) {  // A chave existe na tabela

                a = new Documento(rs.getInt("id"), rs.getString("nome"), rs.getInt("fk_utente_id"));
                col.add(a);
            }

        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return col;
    }

}
