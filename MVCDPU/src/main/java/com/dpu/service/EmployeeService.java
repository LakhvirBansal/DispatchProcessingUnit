package com.dpu.service;

import java.util.List;

import com.dpu.model.EmployeeModel;

public interface EmployeeService {

	Object add(EmployeeModel employeeModel);

	List<EmployeeModel> getAll();

	Object getUserById(Long userId);

	Object delete(Long userId);

	List<EmployeeModel> getUserByUserName(String userName);

	Object update(Long userId, EmployeeModel employeeModel);

	Object getUserByLoginCredentials(EmployeeModel employeeModel);

}
