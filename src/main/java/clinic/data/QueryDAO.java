package clinic.data;

import clinic.Helpers.Pair;
import clinic.Helpers.UtenteConsultaClinica;
import clinic.MainFX;
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


    public Collection<Consulta> getConsulta(int id) {
        Collection<Consulta> res1 = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM consultas WHERE fk_utente_id=" + id)) {
            while (rs.next()) {  // A chave existe na tabela

                Consulta a = new Consulta(rs.getInt("id"), rs.getString("dataa"), rs.getFloat("custo"),
                        rs.getString("obs"), rs.getString("estado"), rs.getString("motivo") ,rs.getInt("fk_utente_id"));
                res1.add(a);
            }

        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res1;

    }

    public Utente updateUtente(Integer key, Utente a) {
        Utente res = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate(
                    "UPDATE utentes SET nascimento='" + a.getNascimento() +"', telemovel= " + a.getTelemovel() + ","+
                            "idade=" + a.getIdade() + ", profissao = '" + a.getProfissao() + "', historico_familiar='"+a.getHistorico_familiar()+
                            "', historico_pessoal='"+a.getHistorico_pessoal() + "', nome='"+ a.getNome() +
                            "', atividade_fisica='"+a.getAtividade_fisica()+"', morada='" + a.getMorada() + "', fk_clinica_id=" + a.getClinicaID() +
                            " WHERE id=" + key);
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }

    public Consulta updateConsultaEstado(Integer key) {
        Consulta res = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate(
                    "UPDATE consultas SET estado='Efetuado' WHERE id=" + key);

        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }

    public Consulta updateConsulta(Consulta c) {
        Consulta res = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate(
                    "UPDATE consultas SET dataa='" + c.getData() + "', custo= "+c.getCusto() + ", obs='"
                            + c.getObs() + "', estado='" + c.getEstado() + "', motivo= '" + c.getMotivo() + "' WHERE id=" + c.getId());

        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }

    public Consulta updateConsultaCalendario(Consulta c) {
        Consulta res = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate(
                    "UPDATE consultas SET dataa='" + c.getData() + "' WHERE id=" + c.getId());

        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
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

    public Collection<Utente> getUtentesFilter(String nome, String telemovel, String nascimento, String morada) {
        Collection<Utente> res1 = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery(
                     "SELECT * FROM utentes where nome like '%" + nome +"%'"+
                     "AND telemovel like '%" + telemovel +"%'"+
                     "AND nascimento like '%" + nascimento +"%'"+
                     "AND morada like '%" + morada +"%';")) {
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


    public Collection<UtenteConsultaClinica> getUtentes() {
        UtenteConsultaClinica a;
        Collection <UtenteConsultaClinica> col = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery(
                     " SELECT *, U.id as idUtente, U.nome as nomeUtente, C.nome as nomeClinica FROM utentes AS U "+
                             "INNER JOIN clinicas AS C "+
                             "ON U.fk_clinica_id = C.id")) {
            while (rs.next()) {  // A chave existe na tabela

                a = new UtenteConsultaClinica(rs.getInt("idUtente"), rs.getString("nomeUtente"),
                        rs.getString("nomeClinica"), rs.getInt("telemovel"),
                        rs.getString("morada"), rs.getString("nascimento"),rs.getInt("idade") );
                col.add(a);
            }

        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return col;
    }

    public Documento putDoc(Documento a, String path) {
        Documento res = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("PRAGMA foreign_keys = ON");
            PreparedStatement pstmt = conn.prepareStatement("INSERT OR REPLACE INTO documentos (doc, fk_utente_id, nome) " +
                    "VALUES (?,?,?)");
            byte[] f = readFile(path);
            //pstmt.setInt(1,a.getId());
            pstmt.setBytes(1, f);
            pstmt.setInt(2,a.getIdUtente());
            pstmt.setString(3, a.getNome());
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
            File file = new File(MainFX.class.getResource("tempImg.png").getFile());
            FileOutputStream fos =  new FileOutputStream(file);
            while (rs.next()) {
                InputStream input = rs.getBinaryStream("doc");
                byte[] buffer = new byte[1024];
                while (input.read(buffer) > 0) {
                    fos.write(buffer);
                }
            }
            //image = new Image(file.getAbsolutePath());

        } catch (SQLException | IOException e) {
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
