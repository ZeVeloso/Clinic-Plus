package clinic.data;

import clinic.business.Clinica;
import clinic.business.Documento;

import java.sql.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DocumentoDao implements Map<Integer, Documento>{

    public static DocumentoDao singleton;

    private DocumentoDao() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("PRAGMA foreign_keys = ON");
            String sql = "CREATE TABLE IF NOT EXISTS documentos (" +
                    "  id INTEGER PRIMARY KEY," +
                    "  doc BLOB NULL," +
                    "  nome VARCHAR(64) NULL,"+
                    " fk_utente_id INT NULL," +
                    " FOREIGN KEY (fk_utente_id)" +
                    " REFERENCES utentes (id)" +
                    " ON DELETE CASCADE " +
                    " ON UPDATE NO ACTION);";
            ;
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            // Erro a criar tabela...
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public static DocumentoDao getInstance() {

        if ( DocumentoDao.singleton == null) {
            DocumentoDao.singleton = new  DocumentoDao();
        }
        return   DocumentoDao.singleton;
    }
    /**
     * @return número de turmas na base de dados
     */
    @Override
    public int size() {
        int i = 0;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM clinicas")) {
            if(rs.next()) {
                i = rs.getInt(1);
            }
        }
        catch (Exception e) {
            // Erro a criar tabela...
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return i;
    }

    /**
     * Método que verifica se existem turmas
     *
     * @return true se existirem 0 turmas
     */
    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * Método que cerifica se um id de turma existe na base de dados
     *
     * @param key id da turma
     * @return true se a turma existe
     * @throws NullPointerException Em caso de erro - deveriam ser criadas exepções do projecto
     */
    @Override
    public boolean containsKey(Object key) {
        boolean r;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL);
             Statement stm = conn.createStatement();
             ResultSet rs =
                     stm.executeQuery("SELECT Id FROM clinicas WHERE id="+key.toString())) {
            r = rs.next();
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    /**
     * Verifica se uma turma existe na base de dados
     *
     * Esta implementação é provisória. Devia testar o objecto e não apenas a chave.
     *
     * @param value ...
     * @return ...
     * @throws NullPointerException Em caso de erro - deveriam ser criadas exepções do projecto
     */
    @Override
    public boolean containsValue(Object value) {
        Documento t = (Documento) value;
        return this.containsKey(t.getId());
    }

    /**
     * Obter uma turma, dado o seu id
     *
     * @param key id da turma
     * @return a turma caso exista (null noutro caso)
     * @throws NullPointerException Em caso de erro - deveriam ser criadas exepções do projecto
     */
    @Override
    public Documento get(Object key) {
        Documento a = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM documentos WHERE id="+key)) {
            if (rs.next()) {  // A chave existe na tabela
                // Reconstruir o aluno com os dados obtidos da BD - a chave estranjeira da turma, não é utilizada aqui.
                a = new Documento(rs.getInt("id"), rs.getBlob("doc"), rs.getInt("fk_utente_id"));
            }

        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return a;
    }


    /**
     * Insere uma turma na base de dados
     *
     * ATENÇÂO: Esta implementação é provisória.
     * Falta devolver o valor existente (caso exista um)
     * Falta remover a sala anterior, caso esteja a ser alterada
     * Deveria utilizar transacções...
     *
     * @param key o id da turma
     * @param a a turma
     * @return para já retorna sempre null (deverá devolver o valor existente, caso exista um)
     * @throws NullPointerException Em caso de erro - deveriam ser criadas exepções do projecto
     */
    @Override
    public Documento put(Integer key, Documento a) {
        Documento res = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("PRAGMA foreign_keys = ON");

            stm.executeUpdate(
                    "INSERT OR REPLACE INTO documentos (id,nome) " +
                            "VALUES ("+a.getId()+",'"+a.getFile().getBinaryStream()+"')");
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }

    /**
     * Remover uma turma, dado o seu id
     *
     * NOTA: Não estamos a apagar a sala...
     *
     * @param key id da turma a remover
     * @return a turma removida
     * @throws NullPointerException Em caso de erro - deveriam ser criadas exepções do projecto
     */
    @Override
    public Documento remove(Object key) {
        Documento t = this.get(key);
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("PRAGMA foreign_keys = ON");
            stm.executeUpdate("DELETE FROM clinicas WHERE id='"+key+"'");
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return t;
    }

    /**
     * Adicionar um conjunto de turmas à base de dados
     *
     * @param clinicas as turmas a adicionar
     * @throws NullPointerException Em caso de erro - deveriam ser criadas exepções do projecto
     */
    @Override
    public void putAll(Map<? extends Integer, ? extends Documento> clinicas) {
        for(Documento t : clinicas.values()) {
            this.put(t.getId(), t);
        }
    }

    /**
     * Apagar todas as turmas
     *
     * @throws NullPointerException Em caso de erro - deveriam ser criadas exepções do projecto
     */
    @Override
    public void clear() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL);
             Statement stm = conn.createStatement()) {

            stm.executeUpdate("TRUNCATE clinicas");
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    /**
     * NÃO IMPLEMENTADO!
     * @return ainda nada!
     */

    @Override
    public Set<Integer> keySet() {
        throw new NullPointerException("Not implemented!");
    }


    /**
     * @return Todos as turmas da base de dados
     */
    @Override
    public Collection<Documento> values() {
        Collection<Documento> res = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT id FROM clinicas")) { // ResultSet com os ids de todas as turmas
            while (rs.next()) {
                String idt = rs.getString("id"); // Obtemos um id de turma do ResultSet
                Documento t = this.get(idt);                    // Utilizamos o get para construir as turmas uma a uma
                res.add(t);                                 // Adiciona a turma ao resultado.
            }
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }

    /**
     * NÃO IMPLEMENTADO!
     * @return ainda nada!
     */
    @Override
    public Set<Map.Entry<Integer, Documento>> entrySet() {
        throw new NullPointerException("public Set<Map.Entry<String,Clinica>> entrySet() not implemented!");
    }



}
