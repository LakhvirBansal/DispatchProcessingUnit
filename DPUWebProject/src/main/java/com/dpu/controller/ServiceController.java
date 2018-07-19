package com.dpu.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dpu.common.CommonProperties;
import com.dpu.constants.Iconstants;
import com.dpu.model.DPUService;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.service.ServiceService;
import com.dpu.util.MessageProperties;

/**
 * @author jagvir
 *
 */
@RestController
@RequestMapping(value = "service")
public class ServiceController extends MessageProperties {

	Logger logger = Logger.getLogger(ServiceController.class);

	@Autowired
	ServiceService serviceService;

	ObjectMapper mapper = new ObjectMapper();

	/**
	 * this method is used to get All services
	 * @return List<Services>
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAll() {
		
		logger.info("ServiceController getAll() starts ");
		String json = null;
		
		try {
			List<DPUService> lstServices = serviceService.getAll();
			if (lstServices != null) {
				json = mapper.writeValueAsString(lstServices);
			}
		} catch (Exception e) {
			logger.error("Exception inside ServiceController getAll() :"+ e.getMessage());
		}
		
		logger.info("ServiceController getAll() ends ");
		return json;
	}

	/**
	 * this method is used to add the service
	 * @param dpuService
	 * @return  List<Services>
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public Object add(@RequestBody DPUService dpuService) {
		
		logger.info("Inside ServiceController add() starts");
		Object obj = null;
		
		try {

			Object result = serviceService.add(dpuService);

			if (result instanceof Success) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			logger.error("Exception inside ServiceController add() :"+ e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0,CommonProperties.service_unable_to_add_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}

		logger.info("Inside ServiceController add() ends");
		return obj;
	}

	/**
	 * this method is used to delete the particular service
	 * @param id
	 * @return  List<Services>
	 */
	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public Object delete(@PathVariable("id") Long id) {
		
		logger.info("Inside ServiceController delete() starts, serviceId :"+id);
		Object obj = null;
		try {
			Object result = null;
			result = serviceService.delete(id);

			if (result instanceof Success) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);

			}
		} catch (Exception e) {
			logger.error("Exception inside ServiceController delete() :"+ e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0,CommonProperties.service_unable_to_delete_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}
		
		logger.info("Inside ServiceController delete() ends, serviceId :"+id);
		return obj;

	}

	/**
	 * this method is used to update the particular service
	 * @param id
	 * @param dpuService
	 * @return  List<Services>
	 */
	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Object update(@PathVariable("id") Long id, @RequestBody DPUService dpuService) {
		
		logger.info("Inside ServiceController update() starts, serviceId :"+id);
		Object obj = null;
		
		try {
			Object result = serviceService.update(id, dpuService);

			if (result instanceof Success) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);

			}
		} catch (Exception e) {
			logger.error("Exception inside ServiceController update() :"+ e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0,CommonProperties.service_unable_to_update_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}
		
		logger.info("Inside ServiceController update() ends, serviceId :"+id);
		return obj;
	}

	/**
	 * this method is used to get the particular service
	 * @param id
	 * @return  List<Services>
	 */
	@RequestMapping(value = "/{serviceId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object get(@PathVariable("serviceId") Long id) {
		
		logger.info("Inside ServiceController get() starts, serviceId :"+id);
		String json = null;
		
		try {
			DPUService dpuService = serviceService.get(id);
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(dpuService);
		} catch (Exception e) {
			logger.error("Exception inside ServiceController get() :"+ e.getMessage());
		}
		
		logger.info("Inside ServiceController get() ends, serviceId :"+id);
		return json;
	}

	/**
	 * this method is used when we click on add button in service 
	 * @return masterdata for service
	 */
	@RequestMapping(value = "/openAdd", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object openAdd() {
		
		logger.info(" Inside ServiceController openAdd() Starts ");
		String json = null;
		
		try {
			DPUService dpuService = serviceService.getOpenAdd();
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(dpuService);
		} catch (Exception e) {
			logger.error("Exception inside ServiceController openAdd() :"+ e.getMessage());
		}
		
		logger.info(" Inside ServiceController openAdd() Ends ");
		return json;
	}

	/**
	 * this method is used to search service based on service name
	 * @param serviceName
	 * @return  List<Services>
	 */
	@RequestMapping(value = "/{serviceName}/search", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object searchService(@PathVariable("serviceName") String serviceName) {
		
		logger.info("Inside ServiceController searchService() Starts, serviceName :"+ serviceName);
		String json = new String();
		
		try {
			List<DPUService> serviceList = serviceService.getServiceByServiceName(serviceName);
			if (serviceList != null && serviceList.size() > 0) {
				json = mapper.writeValueAsString(serviceList);
			}
		} catch (Exception e) {
			logger.error("Exception inside ServiceController searchService() is :"+ e.getMessage());
		}
		
		logger.info(" Inside ServiceController searchService() Ends, serviceName :"+ serviceName);
		return json;
	}

}
