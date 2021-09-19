package clinic.data;

import clinic.business.Clinica;
import clinic.business.Consulta;
import clinic.business.Utente;

import java.sql.*;
import java.util.Collection;
import java.util.HashSet;

public class TesteDao implements DAO {


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
                            "', atividade_fisica='"+a.getAtividade_fisica()+"', morada='" + a.getMorada() + "'"+
                            "WHERE id=" + key);
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

}
