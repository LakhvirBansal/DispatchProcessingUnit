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
import com.dpu.model.ShipperModel;
import com.dpu.service.ShipperService;

@Controller
public class WebLocationController {

	@Autowired
	ShipperService shipperService;

	Logger logger = Logger.getLogger(WebLocationController.class);

	@RequestMapping(value = "/showshipper", method = RequestMethod.GET)
	public ModelAndView showShipperScreen() {
		ModelAndView modelAndView = new ModelAndView();
		List<ShipperModel> lstLocations = shipperService.getAll();
		modelAndView.addObject("LIST_LOCATION", lstLocations);
		modelAndView.setViewName("shipper");
		return modelAndView;
	}

	@RequestMapping(value = "/shipper/getopenadd", method = RequestMethod.GET)
	@ResponseBody
	public ShipperModel getOpenAdd() {
		ShipperModel ShipperModel = null;
		try {
			ShipperModel = shipperService.getMasterData();
		} catch (Exception e) {
			System.out.println(e);
		}
		return ShipperModel;
	}

	@RequestMapping(value = "/saveshipper", method = RequestMethod.POST)
	@ResponseBody
	public Object saveShipper(@ModelAttribute("shipper") ShipperModel ShipperModel, HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session != null) {
			if (session.getAttribute("un") != null) {
				Object response = shipperService.add(ShipperModel);
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

	private Object createFailedObject(String errorMessage) {
		Failed failed = new Failed();
		failed.setMessage(errorMessage);
		return failed;
	}

	@RequestMapping(value = "/getshipper/shipperId", method = RequestMethod.GET)
	@ResponseBody
	public ShipperModel getShipper(@RequestParam("shipperId") Long shipperId) {
		ShipperModel ShipperModel = null;
		try {
			ShipperModel = (ShipperModel) shipperService.get(shipperId);
		} catch (Exception e) {
			System.out.println(e);
			logger.info("Exception in getCategory is: " + e);
		}
		return ShipperModel;
	}

	@RequestMapping(value = "/updateshipper", method = RequestMethod.POST)
	@ResponseBody
	public Object updateShipper(@ModelAttribute("shipper") ShipperModel ShipperModel,
			@RequestParam("shipperid") Long shipperId, HttpServletRequest request) {

		HttpSession session = request.getSession();

		if (session != null) {
			if (session.getAttribute("un") != null) {
				Object response = shipperService.update(shipperId, ShipperModel);
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

	@RequestMapping(value = "/deleteshipper/{shipperid}", method = RequestMethod.GET)
	@ResponseBody
	public Object deleteShipper(@PathVariable("shipperid") Long shipperId) {
		Object response = shipperService.delete(shipperId);
		if (response instanceof Failed) {
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}
}
