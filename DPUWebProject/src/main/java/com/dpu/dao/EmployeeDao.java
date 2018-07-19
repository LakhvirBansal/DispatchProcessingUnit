package com.dpu.dao;

import java.util.List;

import org.hibernate.Session;

import com.dpu.entity.Employee;
import com.dpu.model.EmployeeModel;

public interface EmployeeDao extends GenericDao<Employee> {

	List<Employee> getUserByUserName(Session session, String userName);

	void add(Session session, Employee employee);

	Employee getUserByUserName(Session session, EmployeeModel employeeModel);

	void update(Employee employee, Session session);

}
