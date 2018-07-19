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
import com.dpu.model.TruckModel;
import com.dpu.service.TruckService;

@Controller
public class WebTruckController {

	@Autowired
	TruckService truckService;

	Logger logger = Logger.getLogger(WebTruckController.class);

	@RequestMapping(value = "/showtruck", method = RequestMethod.GET)
	public ModelAndView showTruckScreen() {
		ModelAndView modelAndView = new ModelAndView();
		List<TruckModel> lstTrucks = truckService.getAllTrucks("");
		modelAndView.addObject("LIST_TRUCK", lstTrucks);
		modelAndView.setViewName("truck");
		return modelAndView;
	}

	@RequestMapping(value = "/truck/getopenadd", method = RequestMethod.GET)
	@ResponseBody
	public TruckModel getOpenAdd() {
		TruckModel TruckModel = null;
		try {
			TruckModel = truckService.getOpenAdd();
		} catch (Exception e) {
			System.out.println(e);
		}
		return TruckModel;
	}

	private Object createFailedObject(String errorMessage) {
		Failed failed = new Failed();
		failed.setMessage(errorMessage);
		return failed;
	}

	@RequestMapping(value = "/savetruck", method = RequestMethod.POST)
	@ResponseBody
	public Object saveTruck(@ModelAttribute("truck") TruckModel TruckModel, HttpServletRequest request) {
		HttpSession session = request.getSession();

		if (session != null) {
			if (session.getAttribute("un") != null) {
				Object response = truckService.add(TruckModel);
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

	@RequestMapping(value = "/gettruck/truckId", method = RequestMethod.GET)
	@ResponseBody
	public TruckModel getTruck(@RequestParam("truckId") Long truckId) {
		TruckModel truckRequest = null;
		try {
			truckRequest = truckService.get(truckId);
		} catch (Exception e) {
			System.out.println(e);
			logger.info("Exception in getCategory is: " + e);
		}
		return truckRequest;
	}

	@RequestMapping(value = "/updatetruck", method = RequestMethod.POST)
	@ResponseBody
	public Object updateTruck(@ModelAttribute("truck") TruckModel TruckModel,
			@RequestParam("truckid") Long truckId, HttpServletRequest request) {

		HttpSession session = request.getSession();

		if (session != null) {
			if (session.getAttribute("un") != null) {
				Object response = truckService.update(truckId, TruckModel);
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

	@RequestMapping(value = "/deletetruck/{truckid}", method = RequestMethod.GET)
	@ResponseBody
	public Object deleteTruck(@PathVariable("truckid") Long truckId) {
		Object response = truckService.delete(truckId);
		if (response instanceof Failed) {
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}
}
