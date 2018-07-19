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
import com.dpu.model.Failed;
import com.dpu.model.VehicleMaintainanceCategoryModel;
import com.dpu.service.VehicleMaintainanceCategoryService;

@Controller
public class WebVMCController {

	@Autowired
	VehicleMaintainanceCategoryService vehicleMaintainanceCategoryService;

	Logger logger = Logger.getLogger(WebVMCController.class);

	@RequestMapping(value = "/showvmc", method = RequestMethod.GET)
	public ModelAndView showVMC() {
		ModelAndView modelAndView = new ModelAndView();
		List<VehicleMaintainanceCategoryModel> lstVMCs = vehicleMaintainanceCategoryService.getAll();
		modelAndView.addObject("LIST_VMC", lstVMCs);
		modelAndView.setViewName("vmc");
		return modelAndView;
	}

	@RequestMapping(value = "/savevmc", method = RequestMethod.POST)
	@ResponseBody
	public Object saveVMC(@ModelAttribute("vmc") VehicleMaintainanceCategoryModel vehicleMaintainanceCategoryModel,
			HttpServletRequest request) {
		HttpSession session = request.getSession();

		if (session != null) {
			if (session.getAttribute("un") != null) {
				Object response = vehicleMaintainanceCategoryService.addVMC(vehicleMaintainanceCategoryModel);
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

	@RequestMapping(value = "/getvmc/vmcId", method = RequestMethod.GET)
	@ResponseBody
	public VehicleMaintainanceCategoryModel getVMC(@RequestParam("vmcId") Long vmcId) {
		VehicleMaintainanceCategoryModel vehicleMaintainanceCategoryModel = null;
		try {
			vehicleMaintainanceCategoryModel = vehicleMaintainanceCategoryService.get(vmcId);
		} catch (Exception e) {
			System.out.println(e);
			logger.info("Exception in getCategory is: " + e);
		}
		return vehicleMaintainanceCategoryModel;
	}

	private Object createFailedObject(String errorMessage) {
		Failed failed = new Failed();
		failed.setMessage(errorMessage);
		return failed;
	}

	@RequestMapping(value = "/updatevmc", method = RequestMethod.POST)
	@ResponseBody
	public Object updateVendor(
			@ModelAttribute("vmc") VehicleMaintainanceCategoryModel vehicleMaintainanceCategoryModel,
			@RequestParam("vmcid") Long vmcId, HttpServletRequest request) {
		HttpSession session = request.getSession();

		if (session != null) {
			if (session.getAttribute("un") != null) {
				Object response = vehicleMaintainanceCategoryService.update(vmcId, vehicleMaintainanceCategoryModel);
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

	@RequestMapping(value = "/deletevmc/{vmcid}", method = RequestMethod.GET)
	@ResponseBody
	public Object deleteVMC(@PathVariable("vmcid") Long vmcId) {
		Object response = vehicleMaintainanceCategoryService.delete(vmcId);
		if (response instanceof Failed) {
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}
}
