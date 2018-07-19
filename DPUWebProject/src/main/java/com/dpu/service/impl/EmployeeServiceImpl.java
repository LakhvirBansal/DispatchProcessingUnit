package com.dpu.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.dpu.constants.Iconstants;
import com.dpu.dao.EmployeeDao;
import com.dpu.entity.Employee;
import com.dpu.model.EmployeeModel;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.service.EmployeeService;
import com.dpu.util.DateUtil;

@Component
public class EmployeeServiceImpl implements EmployeeService {

	Logger logger = Logger.getLogger(EmployeeServiceImpl.class);

	@Autowired
	EmployeeDao employeeDao;

	@Autowired
	SessionFactory sessionFactory;
	
	@Value("${user_added_message}")
	private String user_added_message;

	@Value("${user_unable_to_add_message}")
	private String user_unable_to_add_message;

	@Value("${user_deleted_message}")
	private String user_deleted_message;

	@Value("${user_unable_to_delete_message}")
	private String user_unable_to_delete_message;

	@Value("${user_updated_message}")
	private String user_updated_message;

	@Value("${user_unable_to_update_message}")
	private String user_unable_to_update_message;

	@Value("${user_already_used_message}")
	private String user_already_used_message;
	
	@Value("${user_login_message}")
	private String user_login_message;
	
	@Value("${unable_login_message}")
	private String unable_login_message;
	
	@Value("${username_already_exist}")
	private String username_already_exist;
	
	@Value("${email_already_exist}")
	private String email_already_exist;

	private Object createSuccessObject(String msg, long code) {
		Success success = new Success();
		success.setMessage(msg);
		success.setResultList(getAll());
		return success;
	}

	private Object createFailedObject(String msg, long code) {
		Failed failed = new Failed();
		failed.setCode(code);
		failed.setMessage(msg);
		//failed.setResultList(getAll());
		return failed;
	}

	@Override
	public Object add(EmployeeModel employeeModel) {

		logger.info("EmployeeServiceImpl: add():  STARTS");
		Session session = null;
		Transaction tx = null;

		try {

			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Employee employee = new Employee();
			setEmployeeValues(employeeModel, employee);
			employeeDao.add(session, employee);
			if (tx != null) {
				tx.commit();
			}

		} catch (Exception e) {
			logger.fatal("EmployeeServiceImpl: add(): Exception: " + e.getMessage());
			if (tx != null) {
				tx.rollback();
			}
			if(e instanceof ConstraintViolationException){
				ConstraintViolationException c = (ConstraintViolationException) e;
				String constraintName = c.getConstraintName();
				if(Iconstants.UNIQUE_USER_NAME.equals(constraintName)) {
					return createFailedObject(username_already_exist,0);
				}
				if(Iconstants.UNIQUE_EMAIL.equals(constraintName)) {
					return createFailedObject(email_already_exist,0);
				}
			}
			
			
			return createFailedObject(user_unable_to_add_message,0);
		} finally {
			logger.info("EmployeeServiceImpl: add():  finally block");
			if (session != null) {
				session.close();
			}
		}

		logger.info("EmployeeServiceImpl: add():  ENDS");

		return createSuccessObject(user_added_message, 0);
	}

private void setEmployeeValues(EmployeeModel employeeModel, Employee employee) {
		
		logger.info("EmployeeDaoImpl: setEmployeeValues(): STARTS");
		employee.setFirstName(employeeModel.getFirstName());
		employee.setLastName(employeeModel.getLastName());
		employee.setJobTitle(employeeModel.getJobTitle());
		employee.setUsername(employeeModel.getUsername());
		employee.setPassword(employeeModel.getPassword());
		employee.setEmail(employeeModel.getEmail());
		employee.setPhone(employeeModel.getPhone());
		String hiringDate = employeeModel.getHiringdate();
		String terminationDate = employeeModel.getTerminationdate();

		if (!StringUtils.isEmpty(hiringDate)) {
			employee.setHiringDate(DateUtil.changeStringToDate(hiringDate));
		}
		if (!StringUtils.isEmpty(terminationDate)) {
			employee.setTerminationDate(DateUtil.changeStringToDate(terminationDate));
		}
		employee.setCreatedBy(employeeModel.getCreatedBy());
		employee.setModifiedBy(employeeModel.getModifiedBy());
		logger.info("EmployeeDaoImpl: setEmployeeValues(): ENDS");

	}

	
	@Override
	public List<EmployeeModel> getAll() {
		List<Employee> employees = null;
		List<EmployeeModel> employeeResponse = new ArrayList<EmployeeModel>();
		employees = employeeDao.findAll();
		if (employees != null && employees.size() > 0) {
			for (Employee employee : employees) {
				EmployeeModel employeeModel = setEmployeeData(employee);
				employeeResponse.add(employeeModel);
			}
		}
		return employeeResponse;
	}
	
	private EmployeeModel setEmployeeData(Employee employee) {
	
		EmployeeModel employeeModel = new EmployeeModel();
		employeeModel.setEmployeeId(employee.getEmployeeId());
		employeeModel.setFirstName(employee.getFirstName());
		employeeModel.setLastName(employee.getLastName());
		employeeModel.setUsername(employee.getUsername());
		employeeModel.setJobTitle(employee.getJobTitle());
		employeeModel.setEmail(employee.getEmail());
		employeeModel.setPhone(employee.getPhone());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date hiringDate = employee.getHiringDate();
		Date terminationDate = employee.getTerminationDate();
		if (!StringUtils.isEmpty(hiringDate)) {
			employeeModel.setHiringdate(sdf.format(hiringDate));
		}

		if (!StringUtils.isEmpty(terminationDate)) {
			employeeModel.setTerminationdate(sdf.format(terminationDate));
		}
		
		return employeeModel;
	}

	@Override
	public Object getUserById(Long userId) {

		logger.info("Inside EmployeeServiceImpl getUserById() starts, userId :"+ userId);
		EmployeeModel employeeModel = null;
		Employee employee = employeeDao.findById(userId);
			
		if (employee != null) {
			employeeModel = setEmployeeData(employee);
			
			/*BeanUtils.copyProperties(employee, employeeModel);
			employeeModel.setEmployeeId(employee.getEmployeeId());*/
		}
		
		return employeeModel;
	}
	
	private Object createFailedObject(String errorMessage) {
		Failed failed = new Failed();
		failed.setMessage(errorMessage);
		return failed;
	}

	@Override
	public Object delete(Long userId) {
		logger.info("EmployeeServiceImpl delete() starts.");
		Session session = null;
		Transaction tx = null;

		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Employee employee = (Employee) session.get(Employee.class, userId);
			if (employee != null) {
				session.delete(employee);
				tx.commit();
			} else {
				return createFailedObject(user_unable_to_delete_message);
			}

		} catch (Exception e) {
			logger.info("Exception inside EmployeeServiceImpl delete() : " + e.getMessage());
			if (tx != null) {
				tx.rollback();
			}
			if (e instanceof ConstraintViolationException) {
				return createFailedObject(user_already_used_message);
			}
			return createFailedObject(user_unable_to_delete_message);
		} finally {
			if (session != null) {
				session.close();
			}
		}

		logger.info("EmployeeServiceImpl delete() ends.");
		return createSuccessObject(user_deleted_message);
	}

	private Object createSuccessObject(String msg) {

		Success success = new Success();
		success.setMessage(msg);
		success.setResultList(getAll());
		return success;
	}

	@Override
	public List<EmployeeModel> getUserByUserName(String userName) {
		
		logger.info("EmployeeServiceImpl getUserByUserName() starts ");
		Session session = null;
		List<EmployeeModel> employeeResponse = new ArrayList<EmployeeModel>();

		try {
			session = sessionFactory.openSession();
			List<Employee> employeeList = employeeDao.getUserByUserName(session, userName);
			if (employeeList != null && !employeeList.isEmpty()) {
				for (Employee employee : employeeList) {
					EmployeeModel employeeModel = setEmployeeData(employee);
					employeeResponse.add(employeeModel);
				}
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}

		logger.info("EmployeeServiceImpl getUserByUserName() ends ");
		return employeeResponse;
	}

	@Override
	public Object update(Long id, EmployeeModel employeeModel) {

		logger.info("EmployeeServiceImpl update() starts.");
		Session session =null;
		Transaction tx = null;
		
		try {
			
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Employee employee = employeeDao.findById(id);

			if (employee != null) {
				setEmployeeValues(employeeModel, employee);
				employeeDao.update(employee, session);
				tx.commit();
			} else {
				return createFailedObject(user_unable_to_update_message);
			}

		} catch (Exception e) {
			if(tx != null){
				tx.rollback();
			}
			logger.info("Exception inside EmployeeServiceImpl update() :"+ e.getMessage());
			if(e instanceof ConstraintViolationException){
				ConstraintViolationException c = (ConstraintViolationException) e;
				String constraintName = c.getConstraintName();
				if(Iconstants.UNIQUE_USER_NAME.equals(constraintName)) {
					return createFailedObject(username_already_exist,0);
				}
				if(Iconstants.UNIQUE_EMAIL.equals(constraintName)) {
					return createFailedObject(email_already_exist,0);
				}
			}
			return createFailedObject(user_unable_to_update_message);
		} finally{
			if(session != null){
				session.close();
			}
		}

		logger.info("EmployeeServiceImpl update() ends.");
		return createSuccessObject(user_updated_message);
	}
	@Override
	public Object getUserByLoginCredentials(EmployeeModel employeeModel) {

		logger.info("EmployeeServiceImpl update() starts.");
		Session session = null;
		boolean isvalid = false;
		Employee employee = null;
		try {
			session= sessionFactory.openSession();
			employee = employeeDao.getUserByUserName(session, employeeModel);

			if (employee != null) {
				if(employee.getPassword().equals(employeeModel.getPassword())){
					isvalid = true;
				}
			}

		} catch (Exception e) {
			logger.info("Exception inside EmployeeServiceImpl update() :"+ e.getMessage());
			return createFailedObject("");
		} finally{
			if(session != null){
				session.close();
			}
		}

		logger.info("EmployeeServiceImpl update() ends.");
		return isvalid?createLoginObject(employee):createFailedObject(unable_login_message);
	}

	private Object createLoginObject(Employee employee) {
		Success success = new Success();
		success.setMessage(user_login_message);
		EmployeeModel employeeModel = setEmployeeData(employee);
		success.setResultList(employeeModel);
		return success;
	}
	

}
