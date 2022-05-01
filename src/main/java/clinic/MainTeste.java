package clinic;

import javafx.scene.image.Image;

import java.io.*;
import java.sql.*;
import java.util.Arrays;

public class MainTeste {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:ClinicDB.db";
        int i=0;
        while(i<10000) {
            try (
                    Connection conn = DriverManager.getConnection(url)) {
                if (conn != null) {
                    DatabaseMetaData meta = conn.getMetaData();
                    //System.out.println("The driver name is " + meta.getDriverName());
                    //System.out.println("A new database has been created.");
                    Statement statPragma = conn.createStatement();
                    statPragma.executeUpdate("PRAGMA foreign_keys = ON");

                    String insertUtnete = "INSERT INTO consultas VALUES (null, '','', 0,'','','','','nome6','',2)";

                    Statement stat4 = conn.createStatement();
                    Statement stat5 = conn.createStatement();

                    //ResultSet res = stat4.executeQuery("select * from utentes");

                    stat5.executeQuery(insertUtnete);


                }

            } catch (SQLException e) {
                e.getMessage();
            }
        i++;
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
