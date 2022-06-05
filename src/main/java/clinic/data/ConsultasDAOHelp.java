package clinic.data;

import clinic.business.Consulta;

import java.sql.*;
import java.util.Collection;
import java.util.HashSet;

public class ConsultasDAOHelp {

    public float getMoneyMes(String mes, String ano) {
        float res = 0;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery(
                     "SELECT SUM(custo) as custo from consultas WHERE dataa like '%" +mes + ano +"%'")) {
            while (rs.next()) {  // A chave existe na tabela
                  res = rs.getFloat("custo");
            }

        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;

    }

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

    public Collection<Consulta> getConsultaFilter(String estado, String data, int idUtente) {
        Collection<Consulta> res1 = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery(
                     "SELECT * FROM consultas " +
                             "where estado like '%" + estado +"%'"+
                             " AND fk_utente_id = " + idUtente +
                             " AND dataa like '%" + data +"%';")) {
            while (rs.next()) {  // A chave existe na tabela
                Consulta u = new Consulta(rs.getInt("id"), rs.getString("dataa"),
                        rs.getFloat("custo"), rs.getString("obs"),
                        rs.getString("estado"), rs.getString("motivo"),rs.getInt("fk_utente_id"));
                res1.add(u);
            }

        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res1;

    }

}
