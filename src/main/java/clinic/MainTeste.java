package clinic;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.sql.*;

public class MainTeste {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:ClinicDB.db";

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
                //stat1.executeUpdate("DROP TABLE utentes");
                //stat2.executeUpdate("DROP TABLE consultas");
                //stat.executeUpdate(sqlUtente);
                //stat1.executeUpdate(sqlConsulta);
                  //stat2.executeUpdate(sql2);
                //stat.executeUpdate(sql2);
                //stat.executeUpdate(insertConsulta);
                //stat3.executeUpdate(alterTable1);
                //stat1.executeUpdate("DELETE FROM utentes WHERE id=2");
                PreparedStatement pstmt = conn.prepareStatement("INSERT OR REPLACE INTO documentos (id,doc, fk_utente_id) " +
                        "VALUES (8,?,2)");
                byte[] f = readFile("C:\\Users\\Asus\\Desktop\\clinica.png");
                System.out.println(f);
                pstmt.setBytes(1, f);
                pstmt.execute();
                File file = new File("coco3.png");
                FileOutputStream fos =  new FileOutputStream(file);
                ResultSet res = stat4.executeQuery("select * from utentes");
                ResultSet res1 = stat5.executeQuery("select * from documentos WHERE id=8");
                Image image1 = null;
                while (res1.next()) {
                    InputStream input = res1.getBinaryStream("doc");
                    byte[] buffer = new byte[1024];
                    while (input.read(buffer) > 0) {
                        fos.write(buffer);
                    }
                }
               /* Stage stage = new Stage();
                ImageView image = new ImageView(image1);
                VBox vbox=new VBox();
                vbox.getChildren().add(image);
                Scene scene = new Scene(vbox);
                stage.setScene(scene);
                stage.show();*/


            }

        } catch (SQLException | FileNotFoundException e) {
            e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static byte[] readFile(String file) {
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
}
