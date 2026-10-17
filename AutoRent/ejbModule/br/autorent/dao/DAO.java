package br.autorent.dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

/**
 * 
 * @param <T>
 */
public class DAO<T> {
	
	private final Class<T> classObj;
	private final EntityManager entityManager;
	
	public DAO(Class<T> classObj, EntityManager em) {
		this.classObj = classObj;
		this.entityManager = em;
	}

	/**
	 * 
	 * @param t
	 */
	public void add(T t) {
		entityManager.persist(t);
	}

	/**
	 * 
	 * @param t
	 */
	public void delete(T t) {
		
		if (!entityManager.contains(t)) 
	        t = entityManager.merge(t);
		
		entityManager.remove(t);
	}

	/**
	 * 
	 * @param t
	 */
	public T update(T t) {
		return entityManager.merge(t);
	}

	/**
	 * 
	 * @return
	 */
	public List<T> listAll() {
		
		CriteriaQuery<T> query = entityManager.getCriteriaBuilder().createQuery(classObj);
		query.select(query.from(classObj));
		List<T> list = entityManager.createQuery(query).getResultList();
		
		return list;
	}

	/**
	 * 
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public List<T> searchByField(String fieldName, Object value) {
		
	    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	    CriteriaQuery<T> query = cb.createQuery(classObj);
	    Root<T> root = query.from(classObj);

	    query.select(root).where(cb.equal(root.get(fieldName), value));

	    return entityManager.createQuery(query).getResultList();
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public T searchByPrimaryKey(Object primaryKey) {
		return entityManager.find(classObj, primaryKey);
	}
}