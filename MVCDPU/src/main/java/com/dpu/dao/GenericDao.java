package com.dpu.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;

public interface GenericDao<T> {


	public T save(T entity);

	public T update(T entity);

	public void delete(T entity);

	public T findById(Object id);

	public List<T> findAll();

	public List<T> find(Criterion criterion);

	public Integer countDistinct(Criterion criterion, String projection);

	public List<T> findByCriteria(LogicalExpression reExp, String columnName);

	public List<Object[]> getSpecificData(Session session ,String tableName, String firstColumn, String secondColumn);
	
	 
}
