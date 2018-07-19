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
import com.dpu.model.DPUService;
import com.dpu.model.Failed;
import com.dpu.service.ServiceService;

@Controller
public class WebServiceController {

	@Autowired
	ServiceService serviceService;

	Logger logger = Logger.getLogger(WebServiceController.class);

	@RequestMapping(value = "/showservice", method = RequestMethod.GET)
	public ModelAndView showServiceScreen() {
		ModelAndView modelAndView = new ModelAndView();
		List<DPUService> lstServices = serviceService.getAll();
		modelAndView.addObject("LIST_SERVICE", lstServices);
		modelAndView.setViewName("service");
		return modelAndView;
	}

	@RequestMapping(value = "/service/getopenadd", method = RequestMethod.GET)
	@ResponseBody
	public DPUService getOpenAdd() {
		DPUService dPUService = null;
		try {
			dPUService = serviceService.getOpenAdd();
		} catch (Exception e) {
			System.out.println(e);
		}
		return dPUService;
	}

	private Object createFailedObject(String errorMessage) {
		Failed failed = new Failed();
		failed.setMessage(errorMessage);
		return failed;
	}

	@RequestMapping(value = "/saveservice", method = RequestMethod.POST)
	@ResponseBody
	public Object saveTerminal(@ModelAttribute("service") DPUService dpuService, HttpServletRequest request) {
		HttpSession session = request.getSession();

		if (session != null) {
			if (session.getAttribute("un") != null) {
				Object response = serviceService.add(dpuService);
				if (response instanceof Failed) {
					return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
				} else {
					return new ResponseEntity<Object>(response, HttpStatus.OK);
				}
			}
		}
		return new ResponseEntity<Object>(createFailedObject(Iconstants.SESSION_TIME_OUT_MESSAGE),
				HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/getservice/serviceId", method = RequestMethod.GET)
	@ResponseBody
	public DPUService getService(@RequestParam("serviceId") Long shipperId) {
		DPUService dpuService = null;
		try {
			dpuService = serviceService.get(shipperId);
		} catch (Exception e) {
			System.out.println(e);
			logger.info("Exception in getCategory is: " + e);
		}
		return dpuService;
	}

	@RequestMapping(value = "/updateservice", method = RequestMethod.POST)
	@ResponseBody
	public Object updateService(@ModelAttribute("service") DPUService dpuService,
			@RequestParam("serviceid") Long serviceId, HttpServletRequest request) {
		HttpSession session = request.getSession();

		if (session != null) {
			if (session.getAttribute("un") != null) {
				Object response = serviceService.update(serviceId, dpuService);
				if (response instanceof Failed) {
					return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
				} else {
					return new ResponseEntity<Object>(response, HttpStatus.OK);
				}
			}
		}
		return new ResponseEntity<Object>(createFailedObject(Iconstants.SESSION_TIME_OUT_MESSAGE),
				HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/deleteservice/{serviceid}", method = RequestMethod.GET)
	@ResponseBody
	public Object deleteService(@PathVariable("serviceid") Long serviceId) {
		Object response = serviceService.delete(serviceId);
		if (response instanceof Failed) {
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}
}
