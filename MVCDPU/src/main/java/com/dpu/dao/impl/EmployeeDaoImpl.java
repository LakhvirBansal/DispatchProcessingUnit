package com.dpu.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.dpu.dao.EmployeeDao;
import com.dpu.entity.Employee;
import com.dpu.model.EmployeeModel;

/**
 * @author gagan
 *
 */
@Repository
public class EmployeeDaoImpl extends GenericDaoImpl<Employee> implements EmployeeDao {

	@Override
	public void add(Session session, Employee employee) {

		session.save(employee);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Employee> getUserByUserName(Session session, String userName) {
		StringBuilder sb = new StringBuilder(" select h from Employee h  where h.username like :username ");
		Query query = session.createQuery(sb.toString());
		query.setParameter("username", "%"+userName+"%");
		return query.list();
	}

	@Override
	public Employee getUserByUserName(Session session, EmployeeModel employeeModel) {
		StringBuilder sb = new StringBuilder("  from Employee where username like :username ");
		Query query = session.createQuery(sb.toString());
		query.setParameter("username",employeeModel.getUsername());
		return (Employee) query.uniqueResult();
	}

	@Override
	public void update(Employee employee, Session session) {
		session.update(employee);
		
	}
	
}
