package clinic.data;

import java.sql.*;

public class ConfigDAO {

    public ConfigDAO(){
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("PRAGMA foreign_keys = ON");
            String sql = "CREATE TABLE IF NOT EXISTS config (" +
                    "  id INTEGER PRIMARY KEY,"+
                    "  nome VARCHAR(45) NULL,"+
                    "  config VARCHAR(128) NULL," +
                    "UNIQUE(nome))";
            String insertDefault = "INSERT OR IGNORE INTO config (id,nome, config) VALUES (1,'database', null);";
            stm.executeUpdate(sql);
            Statement stm1 = conn.createStatement();
            stm1.executeUpdate(insertDefault);
        } catch (SQLException e) {
            // Erro a criar tabela...
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public static String getConfigNome(Object key) {
        String res = "";
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM config WHERE nome="+key)) {
            if (rs.next()) {  // A chave existe na tabela
                res = rs.getString("config");
            }

        } catch (SQLException e) {
            // Database error!
            System.out.println("nao encontrou");
            //e.printStackTrace();
            return "ClinicDB7.db";
        }
        return res;
    }

    public static boolean put(String path) {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate(
                    "UPDATE config SET config='" + path + "' WHERE nome='database';");
            //"ON DUPLICATE KEY UPDATE id=VALUES(id), data=VALUES(data), custo=VALUES(custo), utente=VALUES(utente);");
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return true;
    }

}
