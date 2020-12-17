package mg.cnaps.services;

import java.io.Serializable;
import java.util.List;

public interface CRUDService<E> {

	E save(E entity);
	
	List<E> save(List<E> entity);

	E getById(Serializable id);

	List<E> getAll();

	void delete(Serializable id);
}
