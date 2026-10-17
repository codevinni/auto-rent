package br.autorent.sessionbeans.stateless;

import java.util.List;

import br.autorent.sessionbeans.interfaces.ICrudService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public abstract class AbstractCrudBean<T> implements ICrudService<T> {

	private final Class<T> classObj;
	
	@PersistenceContext(unitName = "autorent")
	private EntityManager entityManager;
	
	public AbstractCrudBean(Class<T> classObj) {
		this.classObj = classObj;
	}
	
	@Override
	public void create(T t) {
		entityManager.persist(t);
	}

	@Override
	public void delete(T t) {
		
		if (!entityManager.contains(t)) 
	        t = entityManager.merge(t);
		
		entityManager.remove(t);
	}

	@Override
	public T update(T t) {
		return entityManager.merge(t);
	}

	@Override
	public T searchByPrimaryKey(Object primaryKey) {
		return entityManager.find(classObj, primaryKey);
	}

	@Override
	public List<T> searchByField(String fieldName, Object value) {
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	    CriteriaQuery<T> query = cb.createQuery(classObj);
	    Root<T> root = query.from(classObj);

	    query.select(root).where(cb.equal(root.get(fieldName), value));

	    return entityManager.createQuery(query).getResultList();
	}

	@Override
	public List<T> listAll() {
		
		CriteriaQuery<T> query = entityManager.getCriteriaBuilder().createQuery(classObj);
		query.select(query.from(classObj));
		List<T> list = entityManager.createQuery(query).getResultList();
		
		return list;
	}

}