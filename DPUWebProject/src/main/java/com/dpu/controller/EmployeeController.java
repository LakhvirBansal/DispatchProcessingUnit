package com.dpu.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dpu.constants.Iconstants;
import com.dpu.model.EmployeeModel;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.service.EmployeeService;

@RestController
@RequestMapping(value = "employee")
public class EmployeeController {

	Logger logger = Logger.getLogger(EmployeeController.class);

	@Autowired
	EmployeeService employeeService;

	ObjectMapper mapper = new ObjectMapper();

	@Value("${user_unable_to_add_message}")
	private String user_unable_to_add_message;

	@Value("${user_unable_to_delete_message}")
	private String user_unable_to_delete_message;

	@Value("${user_unable_to_update_message}")
	private String user_unable_to_update_message;

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAll() {

		logger.info("Inside EmployeeController getAll() Starts ");
		String json = null;

		try {
			List<EmployeeModel> responses = employeeService.getAll();
			if (responses != null && !responses.isEmpty()) {
				json = mapper.writeValueAsString(responses);
			}

		} catch (Exception e) {
			logger.error("Exception inside EmployeeController getAll() :"
					+ e.getMessage());
		}

		logger.info("Inside EmployeeController getAll() Ends ");
		return json;
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public Object add(@RequestBody EmployeeModel employeeModel) {

		logger.info("Inside EmployeeController add() starts ");
		Object obj = null;

		try {

			Object result = employeeService.add(employeeModel);
			if (result instanceof Success) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			logger.error("Exception inside EmployeeController add() :"
					+ e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0,
					user_unable_to_add_message, Iconstants.ERROR),
					HttpStatus.BAD_REQUEST);
		}

		logger.info("Inside EmployeeController add() ends ");
		return obj;
	}

	@RequestMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public Object delete(@PathVariable("userId") Long userId) {

		logger.info("Inside EmployeeController delete() Starts, userId is :"
				+ userId);
		Object obj = null;

		try {
			Object result = employeeService.delete(userId);
			if (result instanceof Success) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);

			}
		} catch (Exception e) {
			logger.error("Exception inside EmployeeController delete() :"
					+ e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0,
					user_unable_to_delete_message, Iconstants.ERROR),
					HttpStatus.BAD_REQUEST);
		}
		logger.info("Inside EmployeeController delete() Ends, userId is :"
				+ userId);
		return obj;

	}

	@RequestMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Object update(@PathVariable("userId") Long userId,
			@RequestBody EmployeeModel employeeModel) {

		logger.info("Inside EmployeeController update() Starts, userId is :"
				+ userId);
		Object obj = null;
		try {
			Object result = employeeService.update(userId, employeeModel);
			if (result instanceof Success) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);

			}
		} catch (Exception e) {
			logger.error("Exception inside EmployeeController update() :"
					+ e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0,
					user_unable_to_update_message, Iconstants.ERROR),
					HttpStatus.BAD_REQUEST);
		}

		logger.info("Inside EmployeeController update() Ends, userId is :"
				+ userId);
		return obj;
	}

	@RequestMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Object login(@RequestBody EmployeeModel employeeModel) {

		logger.info("Inside EmployeeController login() Starts ");
		Object obj = null;
		try {

			Object result = employeeService
					.getUserByLoginCredentials(employeeModel);
			if (result instanceof Success) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);

			}
		} catch (Exception e) {
			logger.error("Exception inside EmployeeController login() :"
					+ e.getMessage());
		}

		logger.info("Inside EmployeeController login() Ends ");
		return obj;
	}

	@RequestMapping(value = "/loginuser", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Object loginUser(@RequestHeader("username") String username, @RequestHeader("password") String password) {

		logger.info("Inside EmployeeController login() Starts ");
		Object obj = null;
		try {


			EmployeeModel employeeModel = new EmployeeModel();
			employeeModel.setUsername(username);
			employeeModel.setPassword(password);
			Object result = employeeService
					.getUserByLoginCredentials(employeeModel);
			if (result instanceof Success) {
				Success s = (Success) result;
				return mapper.writeValueAsString(s);
				// obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				Failed f = (Failed) result;
				return mapper.writeValueAsString(f);
				// obj = new ResponseEntity<Object>(result,
				// HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.error("Exception inside EmployeeController login() :"
					+ e.getMessage());
		}

		logger.info("Inside EmployeeController login() Ends ");
		return obj;
	}

	@RequestMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getUserByUserId(@PathVariable("userId") Long userId) {

		logger.info("Inside EmployeeController getUserByUserId() Starts, userId:"
				+ userId);
		String json = null;

		try {

			EmployeeModel employeeModel = (EmployeeModel) employeeService
					.getUserById(userId);

			if (employeeModel != null) {
				json = mapper.writeValueAsString(employeeModel);
			}
		} catch (Exception e) {
			logger.error("Exception inside EmployeeController getUserByUserId() :"
					+ e.getMessage());
		}

		logger.info("Inside EmployeeController getUserByUserId() Ends, userId:"
				+ userId);
		return json;
	}

	@RequestMapping(value = "/{userName}/search", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object searchUserByUserName(@PathVariable("userName") String userName) {

		logger.info("Inside EmployeeController searchUserByUserName() Starts, userName :"
				+ userName);
		String json = new String();

		try {
			List<EmployeeModel> employeeList = employeeService
					.getUserByUserName(userName);
			if (employeeList != null && employeeList.size() > 0) {
				json = mapper.writeValueAsString(employeeList);
			}
		} catch (Exception e) {
			logger.error("Exception inside EmployeeController searchUserByUserName() is :"
					+ e.getMessage());
		}

		logger.info(" Inside EmployeeController searchUserByUserName() Ends, userName :"
				+ userName);
		return json;
	}

}
