package br.autorent.sessionbeans.interfaces;

import java.util.List;

public interface ICrudService<T> {

	public void create(T t);
	public void delete(T t);
	public T update(T t);
	public T searchByPrimaryKey(Object primaryKey);
	public List<T> searchByField(String fieldName, Object value);
	public List<T> listAll();
}