package br.autorent.sessionbeans.interfaces;

import java.util.List;

/**
 * Interface genérica para operações CRUD em entidades JPA.
 *
 * @param <T> tipo da entidade gerenciada
 */
public interface ICrudService<T> {

	/**
	 * Persiste uma nova entidade no banco de dados.
	 *
	 * @param t entidade a ser criada
	 */
	public void create(T t);

	/**
	 * Remove uma entidade do banco de dados.
	 *
	 * @param t entidade a ser removida
	 */
	public void delete(T t);

	/**
	 * Atualiza uma entidade existente no banco de dados.
	 *
	 * @param t entidade com dados atualizados
	 * @return entidade atualizada
	 */
	public T update(T t);

	/**
	 * Busca uma entidade pela chave primária.
	 *
	 * @param primaryKey chave primária da entidade
	 * @return entidade encontrada ou {@code null}
	 */
	public T searchByPrimaryKey(Object primaryKey);

	/**
	 * Busca entidades pelo valor de um campo específico.
	 *
	 * @param fieldName nome do campo da entidade
	 * @param value valor para filtrar
	 * @return lista de entidades que correspondem ao filtro
	 */
	public List<T> searchByField(String fieldName, Object value);

	/**
	 * Lista todas as entidades do tipo gerenciado.
	 *
	 * @return lista com todas as entidades
	 */
	public List<T> listAll();
}