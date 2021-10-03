package clinic.data;

import clinic.Helpers.UtenteConsultaClinica;
import clinic.business.Utente;

import java.sql.*;
import java.util.Collection;
import java.util.HashSet;

public class UtentesDAOHelp {

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

    public Collection<Utente> getUtentesFilter(String nome, String telemovel, String nascimento, String morada, String idClinica) {
        Collection<Utente> res1 = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery(
                     "SELECT * FROM utentes where nome like '%" + nome +"%'"+
                             "AND telemovel like '%" + telemovel +"%'"+
                             "AND nascimento like '%" + nascimento +"%'"+
                             "AND fk_clinica_id like '%" + idClinica + "%'"+
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

}
