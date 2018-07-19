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
import com.dpu.model.VendorModel;
import com.dpu.service.VendorService;

@Controller
public class WebVendorController {

	@Autowired
	VendorService vendorService;

	Logger logger = Logger.getLogger(WebVendorController.class);

	@RequestMapping(value = "/showvendor", method = RequestMethod.GET)
	public ModelAndView showVendorScreen() {
		ModelAndView modelAndView = new ModelAndView();
		List<VendorModel> lstVendors = vendorService.getAll();
		modelAndView.addObject("LIST_VENDOR", lstVendors);
		modelAndView.setViewName("vendor");
		return modelAndView;
	}

	@RequestMapping(value = "/savevendor", method = RequestMethod.POST)
	@ResponseBody
	public Object saveVendor(@ModelAttribute("vendor") VendorModel vendorModel, HttpServletRequest request) {
		HttpSession session = request.getSession();

		if (session != null) {
			if (session.getAttribute("un") != null) {
				Object response = vendorService.addVendorData(vendorModel);
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

	@RequestMapping(value = "/getvendor/vendorId", method = RequestMethod.GET)
	@ResponseBody
	public VendorModel getVendor(@RequestParam("vendorId") Long vendorId) {
		VendorModel vendorModel = null;
		try {
			vendorModel = vendorService.get(vendorId);
		} catch (Exception e) {
			System.out.println(e);
			logger.info("Exception in getCategory is: " + e);
		}
		return vendorModel;
	}

	private Object createFailedObject(String errorMessage) {
		Failed failed = new Failed();
		failed.setMessage(errorMessage);
		return failed;
	}

	@RequestMapping(value = "/vendor/getopenadd", method = RequestMethod.GET)
	@ResponseBody
	public VendorModel getOpenAdd() {
		VendorModel VendorModel = null;
		try {
			VendorModel = vendorService.getOpenAdd();
		} catch (Exception e) {
			System.out.println(e);
		}
		return VendorModel;
	}

	@RequestMapping(value = "/updatevendor", method = RequestMethod.POST)
	@ResponseBody
	public Object updateVendor(@ModelAttribute("vendor") VendorModel vendorModel,
			@RequestParam("vendorid") Long vendorId, HttpServletRequest request) {

		HttpSession session = request.getSession();

		if (session != null) {
			if (session.getAttribute("un") != null) {
				Object response = vendorService.update(vendorId, vendorModel);
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

	@RequestMapping(value = "/deletevendor/{vendorid}", method = RequestMethod.GET)
	@ResponseBody
	public Object deleteVendor(@PathVariable("vendorid") Long vendorId) {
		Object response = vendorService.delete(vendorId);
		if (response instanceof Failed) {
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}
}
