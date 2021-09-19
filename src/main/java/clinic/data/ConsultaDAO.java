package clinic.data;

import clinic.business.Consulta;
import clinic.business.Utente;

import java.sql.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ConsultaDAO implements Map<Integer, Consulta> {

    private static ConsultaDAO singleton = null;

    private ConsultaDAO() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("PRAGMA foreign_keys = ON");
            String sql = "CREATE TABLE IF NOT EXISTS consultas (" +
                        "id INTEGER PRIMARY KEY,"+
                        "dataa TEXT(64) NOT NULL,"+
                        "custo FLOAT NULL,"+
                        "obs TEXT NULL," +
                        "estado VARCHAR(32) NULL," +
                        "fk_utente_id INT NULL,"+
                        "motivo VARCHAR(128) NULL," +
                        "FOREIGN KEY (fk_utente_id)"+
                        "REFERENCES utentes (id)"+
                        "ON DELETE CASCADE "+
                        "ON UPDATE NO ACTION);";
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            // Erro a criar tabela...
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    /**
     * Implementação do padrão Singleton
     *
     * @return devolve a instância única desta classe
     */
    public static ConsultaDAO getInstance() {
        if (ConsultaDAO.singleton == null) {
            ConsultaDAO.singleton = new ConsultaDAO();
        }
        return ConsultaDAO.singleton;
    }

    /**
     * @return número de turmas na base de dados
     */
    @Override
    public int size() {
        int i = 0;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM consultas")) {
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
                     stm.executeQuery("SELECT id FROM consultas WHERE id="+key.toString()+"")) {
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
    public Consulta get(Object key) {
        Consulta a = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL);
             Statement stm = conn.createStatement();
             Statement stm1 = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM consultas WHERE id="+key)) {
            if (rs.next()) {  // A chave existe na tabela
                //ResultSet rs1 = stm1.executeQuery("SELECT * FROM utentes WHERE id= "+rs.getInt("fk_utente_id"));
                /*Utente u = new Utente(rs1.getInt("id"), rs1.getString("nascimento"), rs1.getInt("telemovel"),
                        rs1.getInt("idade"), rs1.getString("profissao"),rs1.getString("historico_familiar"),
                        rs1.getString("historico_pessoal"),rs1.getString("nome"),rs1.getString("atividade_fisica"));*/
                // Reconstruir o aluno com os dados obtidos da BD - a chave estranjeira da turma, não é utilizada aqui.
                a = new Consulta(rs.getInt("id"), rs.getString("dataa"), rs.getFloat("custo"),
                        rs.getString("obs"), rs.getString("estado"),rs.getString("motivo"), rs.getInt("fk_utente_id"));
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
    public Consulta put(Integer key, Consulta a) {
        Consulta res = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("PRAGMA foreign_keys = ON");
            stm.executeUpdate(
                    "INSERT OR REPLACE INTO consultas (id,dataa, custo, obs, estado, motivo, fk_utente_id)" +
                    "VALUES ("+a.getId()+",'"+a.getData()+"', "+a.getCusto()+",'"+a.getObs()+
                    "', '" + a.getEstado() + "', '" + a.getMotivo() + "'," +a.getUtente()+")");
                            //"ON DUPLICATE KEY UPDATE id=VALUES(id), data=VALUES(data), custo=VALUES(custo), utente=VALUES(utente);");
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
    public Consulta remove(Object key) {
        Consulta t = this.get(key);
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("DELETE FROM consultas WHERE id="+key);
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
     * @param consultas as turmas a adicionar
     * @throws NullPointerException Em caso de erro - deveriam ser criadas exepções do projecto
     */
    @Override
    public void putAll(Map<? extends Integer, ? extends Consulta> consultas) {
        for(Consulta t : consultas.values()) {
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

            stm.executeUpdate("TRUNCATE consultas");
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
    public Collection<Consulta> values() {
        Collection<Consulta > res = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT id FROM consultas")) { // ResultSet com os ids de todas as turmas
            while (rs.next()) {
                String idt = rs.getString("id"); // Obtemos um id de turma do ResultSet
                Consulta t = this.get(idt);                    // Utilizamos o get para construir as turmas uma a uma
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
    public Set<Entry<Integer, Consulta>> entrySet() {
        throw new NullPointerException("public Set<Map.Entry<Integer,Consulta>> entrySet() not implemented!");
    }


}
