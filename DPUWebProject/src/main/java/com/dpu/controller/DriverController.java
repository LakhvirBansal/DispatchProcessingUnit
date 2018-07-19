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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dpu.constants.Iconstants;
import com.dpu.model.DriverModel;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.service.DriverService;
import com.dpu.util.MessageProperties;

@RestController
@RequestMapping(value = "driver")
public class DriverController extends MessageProperties  {

	Logger logger = Logger.getLogger(DriverController.class);

	@Autowired
	DriverService driverService;

	ObjectMapper mapper = new ObjectMapper();
	
	@Value("${Driver_unable_to_add_message}")
	private String Driver_unable_to_add_message;
	
	@Value("${Driver_unable_to_delete_message}")
	private String Driver_unable_to_delete_message;
	
	@Value("${Driver_unable_to_update_message}")
	private String Driver_unable_to_update_message;

	/**
	 * this method is used to get all the drivers
	 * @return List<drivers>
	 * @author lakhvir
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAllDrivers() {
		logger.info("Inside DriverController getAllDrivers() Starts");
		String json = null;
		try {
			
			List<DriverModel> lstdrivers = driverService.getAllDriver();

			if (lstdrivers != null && !lstdrivers.isEmpty()) {
				json = mapper.writeValueAsString(lstdrivers);
			}
		} catch (Exception e) {
			logger.error("Exception inside DriverController getAllDrivers() :" + e.getMessage());
		}
		logger.info("Inside DriverController getAllDrivers() Ends");
		return json;
	}

	/**
	 * this method is used to add new driver
	 * @param driverModel
	 * @return List<drivers>
	 * @author lakhvir
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<Object> addDriver(@RequestBody DriverModel driverModel) {
		
		logger.info("Inside DriverController addDriver Starts. ");
		ResponseEntity<Object> obj =  null;
		  
		try {
			Object result = driverService.addDriver(driverModel);
			if(result instanceof Success){ 
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else{
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.error("Exception Inside DriverController addDriver :"+e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0, Driver_unable_to_add_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}
		
		logger.info("Inside DriverController addDriver Ends. ");
		return obj;
	}

	/**
	 * this method is used to delete the driver based on driverId
	 * @param driverId
	 * @return List<driver>
	 * @author lakhvir
	 */
	@RequestMapping(value = "/{driverId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public Object deleteDriver(@PathVariable("driverId") Long driverId) {
		
		logger.info("Inside DriverController deleteDriver() : driverId " + driverId);
		Object  obj = null;
		
		try {
			Object result = driverService.deleteDriver(driverId);
			if(result instanceof Success) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.error("Exception inside DriverController deleteDriver() :" + e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0, Driver_unable_to_delete_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}
		
		logger.info("Inside DriverController deleteDriver() Ends, driverId :" + driverId);
		return obj;
	}

	/**
	 * this method is used to update the driver based on driverId
	 * @param driverCode
	 * @param driver
	 * @return List<driver>
	 * @author lakhvir
	 */
	@RequestMapping(value = "/{driverId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Object updateDriver(@PathVariable("driverId") Long driverId, @RequestBody DriverModel driverModel) {

		logger.info("Inside DriverController updateDriver() starts, driverId : "+ driverId);
		Object obj = null;
		
		try {
			Object result = driverService.updateDriver(driverId, driverModel);

			if(result instanceof Success) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.error("Exception inside DriverController updateDriver() :" + e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0, Driver_unable_to_update_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}
		
		logger.info("Inside DriverController updateDriver() Ends, driverId :" + driverId);
		return obj;
	}

	/**
	 * this method is used to get the driver details based on driverId
	 * @param driverId
	 * @return driver
	 * @author lakhvir
	 */
	@RequestMapping(value = "/{driverId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getDriverByDriverId(@PathVariable("driverId") Long driverId) {
		
		logger.info("Inside DriverController getDriverByDriverId() starts, driverId : "+ driverId);
		Object obj = null;
		
		try {
			obj = driverService.getDriverByDriverId(driverId);
		} catch (Exception e) {
			logger.error("Exception inside DriverController getDriverByDriverId() :" + e.getMessage());
		}
		
		logger.info("Inside DriverController getDriverByDriverId() ends, driverId : "+ driverId);
		return obj;
	}
	
	/**
	 * this method is used when we click on add button in driver
	 * @return master data for driver
	 * @author lakhvir.bansal
	 */
	@RequestMapping(value = "/openAdd", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object openAdd() {
		
		logger.info(" Inside driverController openAdd() Starts ");
		String json = null;
		
		try {
			DriverModel driverReq = driverService.getOpenAdd();
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(driverReq);
		} catch (Exception e) {
			logger.error("Exception inside DriverController openAdd() :" + e.getMessage());
		}
		
		logger.info(" Inside driverController openAdd() Ends ");
		return json;
	}
	
	/**
	 * this method is used to search driver by driver code or driver name
	 * @param driverCodeOrName
	 * @return List<driver>
	 * @author lakhvir.bansal
	 */
	@RequestMapping(value = "/{driverCodeOrName}/search", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object searchDriver(@PathVariable("driverCodeOrName") String driverCodeOrName) {
		
		logger.info("Inside driverController searchDriver() Starts, driverCodeOrName :"+driverCodeOrName);
		String json = new String();
		
		try {
			List<DriverModel> serviceList = driverService.getDriverByDriverCodeOrName(driverCodeOrName);
			if(serviceList != null && serviceList.size() > 0) {
				json = mapper.writeValueAsString(serviceList);
			}
		} catch (Exception e) {
			logger.error("Exception inside ServiceController searchDriver() :" + e.getMessage());
		}
		
		logger.info(" Inside ServiceController searchDriver() Starts, driverCodeOrName :"+driverCodeOrName);
		return json;
	}

	/**
	 * this method is used to return Specific data (driverId, fullname)
	 * @return List<DriverData>
	 * @author lakhvir.bansal
	 */
	@RequestMapping(value = "/specificData", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getSpecificData() {

		logger.info("Inside driverController getSpecificData() Starts");
		String json = new String();

		try {
			List<DriverModel> driverList = driverService.getSpecificData();
			if (driverList != null && driverList.size() > 0) {
				json = mapper.writeValueAsString(driverList);
			}
		} catch (Exception e) {
			logger.error("Exception inside  driverController getSpecificData() is :"+ e.getMessage());
		}

		logger.info("Inside driverController getSpecificData() ends");
		return json;
	}
}
