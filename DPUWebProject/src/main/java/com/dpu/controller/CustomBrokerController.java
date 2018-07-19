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
import com.dpu.model.CustomBrokerResponse;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.service.CustomBrokerService;
import com.dpu.util.MessageProperties;

/**
 * @author anuj
 *
 */
@RestController
@RequestMapping(value = "custombroker")
public class CustomBrokerController extends MessageProperties {

	Logger logger = Logger.getLogger(CustomBrokerController.class);

	@Autowired
	CustomBrokerService customBrokerService;

	ObjectMapper mapper = new ObjectMapper();

	/**
	 * this method is used to get all customBrokers
	 * @return List<CustomBrokers>
	 * @author lakhvir.bansal
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAll() {
		logger.info("CustomBrokerController getAll() starts");
		String json = null;
		try {
			List<CustomBrokerResponse> lstcustomBrokerResp = customBrokerService.getAll();
			if (lstcustomBrokerResp != null && lstcustomBrokerResp.size() > 0) {
				json = mapper.writeValueAsString(lstcustomBrokerResp);
			}
		} catch (Exception e) {
			logger.error("Exception inside CustomBrokerController getAll() :"+ e.getMessage());
		}
		logger.info("CustomBrokerController getAll() ends");
		return json;
	}

	/**
	 * this method is used to add the custom broker
	 * @param customBrokerResp
	 * @return List<Custombroker>
	 * @author lakhvir.bansal
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public Object add(@RequestBody CustomBrokerResponse customBrokerResp) {
		logger.info("Inside CustomBrokerController add() starts");
		Object obj = null;
		try {

			Object result = customBrokerService.add(customBrokerResp);

			if (result instanceof Success) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			logger.error("Exception inside CustomBrokerController add() :"+ e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0,CommonProperties.custombroker_unable_to_add_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}

		logger.info("Inside CustomBrokerController add() ends");
		return obj;
	}

	/**
	 * this method is used to delete particular custom broker
	 * @param id
	 * @return List<customBroker>
	 * @author lakhvir.bansal
	 */
	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public Object delete(@PathVariable("id") Long id) {
		
		logger.info("Inside CustomBrokerController delete() starts");
		Object obj = null;
		
		try {
			Object result = null;
			result = customBrokerService.delete(id);

			if (result instanceof Success) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);

			}
		} catch (Exception e) {
			logger.error("Exception inside CustomBrokerController delete() :"+ e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0,CommonProperties.custombroker_unable_to_delete_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}

		logger.info("Inside CustomBrokerController delete() ends");
		return obj;

	}

	/**
	 * this method is used to update the particular custombroker 
	 * @param id
	 * @param customBrokerResp
	 * @return List<CustomBroker>
	 */
	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Object update(@PathVariable("id") Long id, @RequestBody CustomBrokerResponse customBrokerResp) {
		
		logger.info("Inside CustomBrokerController update() starts");
		Object obj = null;
		try {
			Object result = customBrokerService.update(id, customBrokerResp);

			if (result instanceof Success) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);

			}
		} catch (Exception e) {
			logger.error("Exception inside CustomBrokerController update() :"+ e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0,CommonProperties.custombroker_unable_to_update_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}
		
		logger.info("Inside CustomBrokerController update() ends");
		return obj;
	}

	/**
	 * this method is used to get the particular custom broker
	 * @param id
	 * @return customBroker data
	 * @author lakhvir.bansal
	 */
	@RequestMapping(value = "/{customBrokerId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object get(@PathVariable("customBrokerId") Long id) {
		
		logger.info("Inside CustomBrokerController get() starts");
		String json = null;
		
		try {
			CustomBrokerResponse customBrokerResp = customBrokerService.get(id);
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(customBrokerResp);
		} catch (Exception e) {
			logger.error("Exception inside CustomBrokerController get() :"+ e.getMessage());
		}

		logger.info("Inside CustomBrokerController get() ends");
		return json;
	}

	/**
	 * this method is used to get the master data when we click on add custombroker button
	 * @return masterdata for custom broker
	 * @author lakhvir.bansal
	 */
	@RequestMapping(value = "/openAdd", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object openAdd() {
		
		logger.info(" Inside CustomBrokerController openAdd() Starts ");
		String json = null;
		
		try {
			CustomBrokerResponse customBrokerResp = customBrokerService.getOpenAdd();
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(customBrokerResp);
		} catch (Exception e) {
			logger.error("Exception inside CustomBrokerController openAdd() :"+ e.getMessage());
		}
		
		logger.info(" Inside CustomBrokerController openAdd() Ends ");
		return json;
	}

	/**
	 * this method is used to search custom broker based on custom broker name
	 * @param customBrokerName
	 * @return list<customBroker>
	 * @author lakhvir.bansal
	 */
	@RequestMapping(value = "/{custombrokername}/search", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object searchCustomBroker(@PathVariable("custombrokername") String customBrokerName) {
		
		logger.info("Inside CustomBrokerController searchCustomBroker() Starts, customBrokerName :"	+ customBrokerName);
		String json = new String();
		
		try {
			List<CustomBrokerResponse> customBrokerRespList = customBrokerService.getCustomBrokerByCustomBrokerName(customBrokerName);
			if (customBrokerRespList != null && customBrokerRespList.size() > 0) {
				json = mapper.writeValueAsString(customBrokerRespList);
			}
		} catch (Exception e) {
			logger.error("Exception inside CustomBrokerController searchCustomBroker() is :"	+ e.getMessage());
		}
		
		logger.info(" Inside CustomBrokerController customBrokerName() Starts, customBrokerName :"+ customBrokerName);
		return json;
	}
}
