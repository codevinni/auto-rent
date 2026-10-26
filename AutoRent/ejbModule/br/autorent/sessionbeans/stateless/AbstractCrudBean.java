package br.autorent.sessionbeans.stateless;

import java.util.List;

import br.autorent.sessionbeans.interfaces.ICrudService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

/**
 * Implementação abstrata das operações CRUD genéricas usando JPA.
 * Subclasses concretas herdam as operações.
 *
 * @param <T> tipo da entidade gerenciada
 */
public abstract class AbstractCrudBean<T> implements ICrudService<T> {

	private final Class<T> classObj;
	
	@PersistenceContext(unitName = "autorent")
	private EntityManager entityManager;
	
	/**
	 * Inicializa o bean com a classe da entidade gerenciada.
	 *
	 * @param classObj classe da entidade
	 */
	public AbstractCrudBean(Class<T> classObj) {
		this.classObj = classObj;
	}
	
	/**
	 * Retorna o EntityManager gerenciado pelo container.
	 *
	 * @return instância do EntityManager
	 */
	protected EntityManager getEntityManager() {
		return entityManager;
	}
	
	/** {@inheritDoc} */
	@Override
	public void create(T t) {
		entityManager.persist(t);
	}

	/**
	 * {@inheritDoc}
	 * Faz merge antes de remover caso a entidade esteja detached.
	 */
	@Override
	public void delete(T t) {
		
		if (!entityManager.contains(t)) 
	        t = entityManager.merge(t);
		
		entityManager.remove(t);
	}

	/** {@inheritDoc} */
	@Override
	public T update(T t) {
		return entityManager.merge(t);
	}

	/** {@inheritDoc} */
	@Override
	public T searchByPrimaryKey(Object primaryKey) {
		return entityManager.find(classObj, primaryKey);
	}

	/**
	 * {@inheritDoc}
	 * Usa Criteria API para construir a consulta.
	 */
	@Override
	public List<T> searchByField(String fieldName, Object value) {
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	    CriteriaQuery<T> query = cb.createQuery(classObj);
	    Root<T> root = query.from(classObj);

	    query.select(root).where(cb.equal(root.get(fieldName), value));

	    return entityManager.createQuery(query).getResultList();
	}

	/**
	 * {@inheritDoc}
	 * Usa Criteria API para listar todas as entidades.
	 */
	@Override
	public List<T> listAll() {
		
		CriteriaQuery<T> query = entityManager.getCriteriaBuilder().createQuery(classObj);
		query.select(query.from(classObj));
		List<T> list = entityManager.createQuery(query).getResultList();
		
		return list;
	}

}