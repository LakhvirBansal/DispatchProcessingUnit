/*package com.dpu.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dpu.dao.GenericDao;

@Repository
@Transactional
public class GenericDaoImpl<T> implements GenericDao<T> {

	@Autowired
	SessionFactory sessionFactory;

	Logger logger = Logger.getLogger(GenericDaoImpl.class);

	@SuppressWarnings({ "unchecked"})
	public T save(T entity) {
		T t = null;
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(entity);
			tx.commit();
			return entity;
		} catch (Exception e) {
			tx.rollback();
			logger.error("[save]" + e);
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return t;
	}

	@SuppressWarnings("unchecked")
	public T update(T entity) {
		T t = null;
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			t = (T) session.merge(entity);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			logger.error("[update]" + e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return t;
	}

	public void delete(T entity) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.delete(entity);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			logger.error("[delete]" + e.getCause());
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public T findById(Object id) {

		Session session = sessionFactory.openSession();
		T t = null;
		try {
			Type type = getClass().getGenericSuperclass();
			ParameterizedType pt = (ParameterizedType) type;
			Class<T> className = (Class) pt.getActualTypeArguments()[0];
			t = (T) session.get(className, (Serializable) id);
		} catch (Exception e) {
			logger.error("[findById]" + e.getCause());
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return t;
	}

	public List<T> findAll() {
		return find(null);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<T> find(Criterion criterion) {
		Session session = null;
		List<T> t = null;
		Type type = getClass().getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType) type;
		Class<T> className = (Class) pt.getActualTypeArguments()[0];
		try {
			session = sessionFactory.openSession();
			if (criterion != null) {

				t = session.createCriteria(className).add(criterion).list();

			} else {
				t = session.createCriteria(className).list();
			}

		} catch (Exception e) {
			logger.error("[find ]" + e.getCause());
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return t;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Integer countDistinct(Criterion criterion, String projection) {
		Session session = sessionFactory.openSession();
		Integer result = 0;
		Type type = getClass().getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType) type;
		Class<T> className = (Class) pt.getActualTypeArguments()[0];
		try {

			if (criterion != null) {

				if (projection != null) {

					result = (Integer) session
							.createCriteria(className)
							.add(criterion)
							.setProjection(
									Projections.countDistinct(projection))
							.uniqueResult();
				} else {
					logger.warn("[find] projection is null");
				}

			} else {
				logger.warn("[find] criterion is null");
			}

		} catch (Exception e) {
			logger.error("[find]" + e);
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<T> findByCriteria(LogicalExpression reExp, String columnName) {
		Session session = null;
		List<T> t = null;
		Type type = getClass().getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType) type;
		Class<T> className = (Class) pt.getActualTypeArguments()[0];
		try {
			session = sessionFactory.openSession();
			if (reExp != null) {
				t = session.createCriteria(className).add(reExp)
						.addOrder(Order.desc(columnName)).list();
			}
		} catch (Exception e) {
			logger.error("[findByCriteria]" + e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return t;
	}

}
 */

package com.dpu.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dpu.dao.GenericDao;

@Repository
@Transactional
public class GenericDaoImpl<T> implements GenericDao<T> {

	@Autowired
	SessionFactory sessionFactory;

	Logger logger = Logger.getLogger(GenericDaoImpl.class);

	@SuppressWarnings({ "unchecked" })
	public T save(T entity) {
		T t = null;
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			t = (T) session.save(entity);

			tx.commit();
			return entity;
		} catch (Exception e) {
			tx.rollback();
			logger.error("[save]" + e);
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public T update(T entity) {
		logger.info("[GenericDaoImpl] [update]");
		T t = null;
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			t = (T) session.merge(entity);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			logger.error("[GenericDaoImpl] [update]" + e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return t;
	}

	public void delete(T entity) {
		logger.info("[GenericDaoImpl] [delete]  : Enter");
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.delete(entity);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			logger.error("[GenericDaoImpl] [Delete]" + e);
		} finally {
			if (session != null) {
				session.close();

			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public T findById(Object id) {
		logger.info("[GenericDaoImpl] [findById]  : Enter");
		Session session = sessionFactory.openSession();
		T t = null;
		try {
			Type type = getClass().getGenericSuperclass();
			ParameterizedType pt = (ParameterizedType) type;
			Class<T> className = (Class) pt.getActualTypeArguments()[0];
			t = (T) session.get(className, (Serializable) id);
		} catch (Exception e) {
			logger.error("[findById]" + e.getCause());
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return t;
	}

	public List<T> findAll() {
		logger.info("[DivisionDaoImpl] [findAll] : Enter ");
		return find(null);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<T> find(Criterion criterion) {
		logger.info(" [find] : Enter ");
		Session session = null;
		List<T> t = null;
		Type type = getClass().getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType) type;
		Class<T> className = (Class) pt.getActualTypeArguments()[0];
		try {
			session = sessionFactory.openSession();
			if (criterion != null) {

				t = session.createCriteria(className).add(criterion).list();

			} else {
				t = session.createCriteria(className).list();
			}

		} catch (Exception e) {
			logger.error("[find ]" + e.getCause());
			System.out.println(e.getCause());
			System.out.println(e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		// System.out.println("size::: "+t.size());
		logger.info("[DivisionDaoImpl] [find] : Exit ");
		return t;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Integer countDistinct(Criterion criterion, String projection) {
		Session session = sessionFactory.openSession();
		Integer result = 0;
		Type type = getClass().getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType) type;
		Class<T> className = (Class) pt.getActualTypeArguments()[0];
		try {

			if (criterion != null) {

				if (projection != null) {

					result = (Integer) session
							.createCriteria(className)
							.add(criterion)
							.setProjection(
									Projections.countDistinct(projection))
							.uniqueResult();
				} else {
					logger.warn("[find] projection is null");
				}

			} else {
				logger.warn("[find] criterion is null");
			}

		} catch (Exception e) {
			logger.error("[find]" + e);
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<T> findByCriteria(LogicalExpression reExp, String columnName) {
		Session session = null;
		List<T> t = null;
		Type type = getClass().getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType) type;
		Class<T> className = (Class) pt.getActualTypeArguments()[0];
		try {
			session = sessionFactory.openSession();
			if (reExp != null) {
				t = session.createCriteria(className).add(reExp)
						.addOrder(Order.desc(columnName)).list();
			}
		} catch (Exception e) {
			logger.error("[findByCriteria]" + e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return t;
	}

	@SuppressWarnings({ "unchecked" })
	public List<Object[]> getSpecificData(Session session , String tableName, String firstColumn,
			String secondColumn) {

		List<Object[]> data = null;
	 
		try {
			Query query = session.createQuery(" select " + firstColumn + " , "
					+ secondColumn + " from " + tableName);
			 
			data = query.list();

		} catch (Exception e) {
			logger.error("[save]" + e);
		} finally {
	 
		}

		return data;
	}
	

}
