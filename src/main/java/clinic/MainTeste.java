package clinic;

import java.sql.*;

public class MainTeste {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:clinicDB1.db";

        try (
                Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
                Statement statPragma = conn.createStatement();
                statPragma.executeUpdate("PRAGMA foreign_keys = ON");
                String sql = "select * from utentes;";
                String sql4 = "select * from consultas;";
                String alterTable = "ALTER TABLE consultas " +
                        "ADD COLUMN motivo TEXT(128);";
                String alterTable1 = "ALTER TABLE consultas RENAME COLUMN Estado TO estado";
                String sqlUtente = "CREATE TABLE IF NOT EXISTS utentes (" +
                        "  id INTEGER PRIMARY KEY ," +
                        "  nascimento VARCHAR(45) NOT NULL," +
                        "  telemovel INT NOT NULL," +
                        "  idade INT NOT NULL," +
                        "  profissao VARCHAR(45) NULL," +
                        "  historico_familiar TEXT(512) NULL," +
                        "  historico_pessoal TEXT(512) NULL," +
                        "  nome VARCHAR(64) NULL," +
                        "  atividade_fisica VARCHAR(128) NULL)"
                        ;
                String sqlConsulta = "CREATE TABLE IF NOT EXISTS consultas (" +
                        "id INTEGER PRIMARY KEY,"+
                        "dataa DATE NOT NULL,"+
                        "custo FLOAT NULL,"+
                        "utente_id INT NULL,"+
                        "FOREIGN KEY (utente_id)"+
                        "REFERENCES utentes (id)"+
                        "ON DELETE CASCADE "+
                        "ON UPDATE NO ACTION);";
                String sql3 =
                        "INSERT OR REPLACE INTO utentes (id, nascimento, telemovel, idade, profissao, historico_familiar,historico_pessoal,nome,atividade_fisica) " +
                                "VALUES (3, '2021-1-1',1,1,'profissao1','historico f','historico pess','nomeAlterado3','ativ fisica2')";//+
                                //"ON DUPLICATE KEY UPDATE nascimento=VALUES(nascimento), telemovel=VALUES(telemovel), " +
                                //"idade=VALUES(idade),profissao=VALUES(profissao), historico_familiar=VALUES(historico_familiar), " +
                                //"historico_pessoal=VALUES(historico_pessoal), nome=VALUES(nome), atividade_fisica=VALUES(atividade_fisica);";
                String sql2 = "insert into utentes (nascimento, telemovel, idade, profissao, historico_familiar,historico_pessoal,nome,atividade_fisica) " +
                        "VALUES ('2021-1-1',1,1,'profissao1','historico f','historico pess','nome1','ativ fisica2')";

                String insertConsulta = "insert into consultas (id, dataa, custo, fk_utente_id, estado) VALUES (null, '2000-01-02',4,3, 'coco')";
                String updateTeste = "UPDATE utentes SET nascimento='" + "ad" +"', telemovel= " + "1234" + ","+
                        "idade=" + "12" + ", profissao = '" + "prof" + "', historico_familiar='"+"fam"+
                        "', historico_pessoal='"+"pess" + "', nome='"+ "nomeAlteradoUpdate" + "', atividade_fisica='"+"fisica"+
                        "' WHERE id=" + "3";
                Statement stat = conn.createStatement();
                Statement stat1 = conn.createStatement();
                Statement stat2 = conn.createStatement();
                Statement stat3 = conn.createStatement();
                Statement stat4 = conn.createStatement();
                Statement stat5 = conn.createStatement();
                stat1.executeUpdate("DROP TABLE utentes");
                stat2.executeUpdate("DROP TABLE consultas");
                //stat.executeUpdate(sqlUtente);
                //stat1.executeUpdate(sqlConsulta);
                  //stat2.executeUpdate(sql2);
                //stat.executeUpdate(sql2);
                //stat.executeUpdate(insertConsulta);
                //stat3.executeUpdate(alterTable1);
                //stat1.executeUpdate("DELETE FROM utentes WHERE id=2");
                ResultSet res = stat4.executeQuery("select * from utentes");
                ResultSet res1 = stat5.executeQuery("select * from consultas");
                while (res1.next()) {
                    int id1 = res1.getInt("id");
                    String utenteid = res1.getString("estado");
                    System.out.println("Consulta " + id1 + " " + utenteid);
                }
                while (res.next()) {
                    String nome = res.getString("nascimento");
                    int id = res.getInt("id");
                    String nome1 = res.getString("nome");
                    System.out.println("Utente "+ id + " " + nome1);
                }



            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.exit(0);

    }
}
