package clinic.data;

import clinic.business.Consulta;

import java.util.Collection;

public interface DAO {

    Collection<Consulta> getConsulta(int id);

}
