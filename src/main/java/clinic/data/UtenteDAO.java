package clinic.data;

import clinic.business.Utente;

import java.sql.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class UtenteDAO implements Map<Integer, Utente> {

    public static UtenteDAO singleton;

    private UtenteDAO() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("PRAGMA foreign_keys = ON");
            String sql = "CREATE TABLE IF NOT EXISTS utentes (" +
                    "  id INTEGER PRIMARY KEY ," +
                    "  nascimento VARCHAR(45) NOT NULL," +
                    "  telemovel INT NOT NULL," +
                    "  idade INT NOT NULL," +
                    "  profissao VARCHAR(45) NULL," +
                    "  morada VARCHAR(64) NULL," +
                    "  historico_familiar TEXT NULL," +
                    "  historico_pessoal TEXT NULL," +
                    "  nome VARCHAR(64) NULL," +
                    "  atividade_fisica VARCHAR NULL," +
                    "  fk_clinica_id INT NULL," +
                    "  FOREIGN KEY (fk_clinica_id)" +
                    "  REFERENCES clinicas (id)" +
                    "  ON DELETE NO ACTION" +
                    "  ON UPDATE NO ACTION);"
                    ;
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            // Erro a criar tabela...
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public static UtenteDAO getInstance() {
        if ( UtenteDAO.singleton == null) {
            UtenteDAO.singleton = new UtenteDAO();
        }
        return  UtenteDAO.singleton;
    }
    /**
     * @return número de turmas na base de dados
     */
    @Override
    public int size() {
        int i = 0;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM utentes")) {
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

    public int diarreia(){
        return 0;
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
                     stm.executeQuery("SELECT Id FROM utentes WHERE id="+key.toString())) {
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
        Utente t = (Utente) value;
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
    public Utente get(Object key) {
        Utente a = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM utentes WHERE id="+key)) {
            if (rs.next()) {  // A chave existe na tabela
                // Reconstruir o aluno com os dados obtidos da BD - a chave estranjeira da turma, não é utilizada aqui.
                a = new Utente(rs.getInt("id"), rs.getString("nascimento"), rs.getInt("telemovel"),
                        rs.getInt("idade"), rs.getString("profissao"),rs.getString("historico_familiar"),
                        rs.getString("historico_pessoal"),rs.getString("nome"),
                        rs.getString("atividade_fisica"), rs.getString("morada"),
                        rs.getInt("fk_clinica_id"));
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
    public Utente put(Integer key, Utente a) {
        Utente res = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("PRAGMA foreign_keys = ON");

            stm.executeUpdate(
                    "INSERT OR REPLACE INTO utentes (id,nascimento, telemovel, idade, profissao, historico_familiar," +
                            "historico_pessoal,nome,atividade_fisica, morada, fk_clinica_id) " +
                            "VALUES ("+a.getId()+",'"+a.getNascimento()+"', "+a.getTelemovel()+
                            ", "+a.getIdade()+", '"+a.getProfissao()+"', '"+a.getHistorico_familiar() +
                            "', '"+a.getHistorico_pessoal()+"', '"+a.getNome()+"','"+a.getAtividade_fisica() +
                            "', '" + a.getMorada() + "'," + a.getClinicaID() + ");");
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
    public Utente remove(Object key) {
        Utente t = this.get(key);
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("PRAGMA foreign_keys = ON");
            stm.executeUpdate("DELETE FROM utentes WHERE id='"+key+"'");
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
     * @param utentes as turmas a adicionar
     * @throws NullPointerException Em caso de erro - deveriam ser criadas exepções do projecto
     */
    @Override
    public void putAll(Map<? extends Integer, ? extends Utente> utentes) {
        for(Utente t : utentes.values()) {
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

            stm.executeUpdate("TRUNCATE utentes");
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
    public Collection<Utente> values() {
        Collection<Utente> res = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT id FROM utentes")) { // ResultSet com os ids de todas as turmas
            while (rs.next()) {
                String idt = rs.getString("id"); // Obtemos um id de turma do ResultSet
                Utente t = this.get(idt);                    // Utilizamos o get para construir as turmas uma a uma
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
    public Set<Entry<Integer, Utente>> entrySet() {
        throw new NullPointerException("public Set<Map.Entry<String,Utente>> entrySet() not implemented!");
    }

}
