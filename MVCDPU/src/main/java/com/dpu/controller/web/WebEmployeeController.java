package com.dpu.controller.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dpu.constants.Iconstants;
import com.dpu.model.EmployeeModel;
import com.dpu.model.Failed;
import com.dpu.service.EmployeeService;
import com.dpu.util.DateUtil;
import com.dpu.util.HelperUtil;

@Controller
public class WebEmployeeController {

	@Autowired
	EmployeeService employeeService;
	
	Logger logger = Logger.getLogger(WebEmployeeController.class);
	
	@RequestMapping(value = "/showuser", method = RequestMethod.GET)
	public ModelAndView showUsers() {
		ModelAndView modelAndView = new ModelAndView();
		List<EmployeeModel> lstEmployees = employeeService.getAll();
		modelAndView.addObject("LIST_EMPLOYEE", lstEmployees);
		modelAndView.setViewName("employee");
		return modelAndView;
	}
	
	private Object createFailedObject(String errorMessage) {
		Failed failed = new Failed();
		failed.setMessage(errorMessage);
		return failed;
	}
	
	@RequestMapping(value = "/saveuser" , method = RequestMethod.POST)
	@ResponseBody public Object saveUser(@ModelAttribute("user") EmployeeModel employeeModel, HttpServletRequest request) {
		HttpSession session = request.getSession();

		if(session != null) {
			if(session.getAttribute("un") != null) {
				String hiringDt = employeeModel.getHiringdate();
				String terminationDt = employeeModel.getTerminationdate();
				if(HelperUtil.chkStringIsNotEmpty(hiringDt)) {
					String hiring = DateUtil.rearrangeDate(hiringDt);
					employeeModel.setHiringdate(hiring);
				}
				if(HelperUtil.chkStringIsNotEmpty(terminationDt)) {
					String termination = DateUtil.rearrangeDate(employeeModel.getTerminationdate());
					employeeModel.setTerminationdate(termination);
				}
				Object response = employeeService.add(employeeModel);
				if(response instanceof Failed) {
					return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
				} else {
					return new ResponseEntity<Object>(response, HttpStatus.OK);
				}
			}
		}
		return new ResponseEntity<Object>(createFailedObject(Iconstants.SESSION_TIME_OUT_MESSAGE), HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/getuser/userId" , method = RequestMethod.GET)
	@ResponseBody  public EmployeeModel getUser(@RequestParam("userId") Long userId) {
		EmployeeModel employeeModel = null;
		try {
			employeeModel = (EmployeeModel) employeeService.getUserById(userId);
		} catch (Exception e) {
			System.out.println(e);
			logger.info("Exception in getCategory is: " + e);
		}
		return employeeModel;
	}
	
	@RequestMapping(value = "/updateuser" , method = RequestMethod.POST)
	@ResponseBody public Object updateUser(@ModelAttribute("user") EmployeeModel employeeModel, @RequestParam("employeeid") Long employeeId, HttpServletRequest request) {
		
		HttpSession session = request.getSession();

		if(session != null) {
			if(session.getAttribute("un") != null) {
				
				String hiringDt = employeeModel.getHiringdate();
				String terminationDt = employeeModel.getTerminationdate();

				if(HelperUtil.chkStringIsNotEmpty(hiringDt)) {
					String hiring = DateUtil.rearrangeDate(hiringDt);
					employeeModel.setHiringdate(hiring);
				}

				if(HelperUtil.chkStringIsNotEmpty(terminationDt)) {
					String termination = DateUtil.rearrangeDate(terminationDt);
					employeeModel.setTerminationdate(termination);
				}

				Object response = employeeService.update(employeeId, employeeModel);
				if(response instanceof Failed) {
					return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
				} else {
					return new ResponseEntity<Object>(response, HttpStatus.OK);
				}
			}
		}
		return new ResponseEntity<Object>(createFailedObject(Iconstants.SESSION_TIME_OUT_MESSAGE), HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/deleteuser/{userid}" , method = RequestMethod.GET)
	@ResponseBody public Object deleteUser(@PathVariable("userid") Long employeeId) {
		Object response = employeeService.delete(employeeId);
		if(response instanceof Failed) {
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}
}
