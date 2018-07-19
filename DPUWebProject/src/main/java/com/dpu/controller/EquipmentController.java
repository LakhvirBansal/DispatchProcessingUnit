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
import com.dpu.model.EquipmentReq;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.service.EquipmentService;
import com.dpu.util.MessageProperties;

/**
 * @author jagvir
 *
 */
@RestController
@RequestMapping(value = "equipment")
public class EquipmentController extends MessageProperties {

	Logger logger = Logger.getLogger(EquipmentController.class);

	@Autowired
	EquipmentService equipmentService;

	ObjectMapper mapper = new ObjectMapper();
	
	@Value("${equipment_unable_to_add_message}")
	private String equipment_unable_to_add_message;
	
	@Value("${equipment_unable_to_delete_message}")
	private String equipment_unable_to_delete_message;
	
	@Value("${equipment_unable_to_update_message}")
	private String equipment_unable_to_update_message;
	

	@RequestMapping(value = "/{equipmentname}/search", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object searchEquipment(@PathVariable("equipmentname") String equipmentName) {
		
		logger.info("EquipmentController searchEquipment starts, equipmentName :"+equipmentName);
		String json = new String();
		
		try {
			List<EquipmentReq> lstequipments = equipmentService.getAll(equipmentName);
			if (lstequipments != null && lstequipments.size() > 0) {
				json = mapper.writeValueAsString(lstequipments);
			}
		} catch (Exception e) {
			logger.error("Exception inside EquipmentController searchEquipment :" + e.getMessage());
		}
		
		logger.info("EquipmentController searchEquipment ends, equipmentName :"+equipmentName);
		return json;
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAllEquipment() {
	
		logger.info("EquipmentController getAllEquipment() starts");
		String json = new String();
		
		try {
			List<EquipmentReq> lstequipments = equipmentService.getAll("");
			if (lstequipments != null && lstequipments.size() > 0) {
				json = mapper.writeValueAsString(lstequipments);
			}
		} catch (Exception e) {
			logger.error("Exception inside EquipmentController getAllEquipment() :" + e.getMessage());
		}
		
		logger.info("EquipmentController getAllEquipment() ends");
		return json;
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public Object add(@RequestBody EquipmentReq equipmentReq) {

		logger.info("EquipmentController add() STARTS");
		Object obj = null;
		try {

			Object result = equipmentService.add(equipmentReq);

			if (result instanceof Success) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);

			}
		} catch (Exception e) {
			logger.fatal("EquipmentController: add(): Exception: "+ e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0, equipment_unable_to_add_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}

		logger.info("EquipmentController: add(): ENDS");

		return obj;
	}

	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public Object delete(@PathVariable("id") Long id) {
		
		logger.info("EquipmentController delete() starts, equipmentId :"+id);
		Object obj = null;
		try {
			Object result = equipmentService.delete(id);

			if (result instanceof Success) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);

			}
		} catch (Exception e) {
			logger.error("Exception inside EquipmentController delete() :"+e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0, equipment_unable_to_delete_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}
		
		logger.info("EquipmentController delete() ends, equipmentId :"+id);
		return obj;

	}

	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Object update(@PathVariable("id") Long id,@RequestBody EquipmentReq equipmentReq) {
		
		logger.info("EquipmentController update() starts, equipmentId :"+id);
		Object obj = null;
		try {
			equipmentReq.setEquipmentId(id);
			Object result = equipmentService.update(id, equipmentReq);
			if (result instanceof Success) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			logger.error("Exception inside EquipmentController update() :" + e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0, equipment_unable_to_update_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}
		logger.info("EquipmentController update() ends, equipmentId :"+id);
		return obj;
	}

	@RequestMapping(value = "/{equipmentId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object get(@PathVariable("equipmentId") Long id) {

		logger.info("EquipmentController get starts, equipmentId :"+id);
		String json = new String();
		try {

			EquipmentReq equipmentReq = equipmentService.get(id);
			if (equipmentReq != null) {
				ObjectMapper mapper = new ObjectMapper();
				json = mapper.writeValueAsString(equipmentReq);
			}
		} catch (Exception e) {
			logger.info("EquipmentController: get(): Exception:  "+ e.getMessage());
		}

		logger.info("EquipmentController: get(): ENDS, equipmentId :"+id);
		return json;
	}

}
