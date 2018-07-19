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
import com.dpu.model.CarrierContractModel;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.service.CarrierContractService;

@RestController
@RequestMapping(value = "carrierContract")
public class CarrierContractController {

	Logger logger = Logger.getLogger(CarrierContractController.class);

	ObjectMapper mapper = new ObjectMapper();

	@Value("${CarrierContract_unable_to_add_message}")
	private String CarrierContract_unable_to_add_message;

	@Value("${CarrierContract_unable_to_delete_message}")
	private String CarrierContract_unable_to_delete_message;

	@Value("${CarrierContract_unable_to_update_message}")
	private String CarrierContract_unable_to_update_message;

	@Autowired
	CarrierContractService carrierContractService;

	/**
	 * this method is used to get all the carrierContract
	 * 
	 * @return List<carrierContracts>
	 * @author sumit
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAllCarrierContract() {

		logger.info("CarrierContractController getAllCarrierContract() starts");
		String json = new String();

		try {
			List<CarrierContractModel> carrierResponse = carrierContractService.getAllCarrierContract();

			if (carrierResponse != null) {
				json = mapper.writeValueAsString(carrierResponse);
			}

		} catch (Exception e) {
			logger.info("Exception inside CarrierContractController getAllCarrierContract() :" + e.getMessage());
		}

		logger.info("CarrierContractController getAllCarrierContract() ends");
		return json;
	}

	/**
	 * this method is used to add new CarrierContract
	 * 
	 * @param CarrierContractModel
	 * @return List<CarrierContracts>
	 * @author
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<Object> addCarrierContract(@RequestBody CarrierContractModel carrierContract) {

		System.out.println("Enter Post");
		logger.info("Inside CarrierContractController addCarrierContract Starts. ");
		ResponseEntity<Object> obj = null;

		try {
			Object result = carrierContractService.addCarrierContract(carrierContract);
			if (result instanceof Success) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.error("Exception Inside CarrierContractController addCarrierContract :" + e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0, CarrierContract_unable_to_add_message, Iconstants.ERROR),
					HttpStatus.BAD_REQUEST);
		}

		logger.info("Inside CarrierContractController addCarrierContract Ends. ");
		return obj;
	}

	/**
	 * this method is used to delete the carrierContract based on
	 * carrierContractId
	 * 
	 * @param carrierContractId
	 * @return List<carrierContract>
	 * @author sumit
	 */
	@RequestMapping(value = "/{carrierContractId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public Object deleteCarrierContract(@PathVariable("carrierContractId") Long carrierContractId) {

		logger.info(
				"Inside CarrierContractController deleteCarrierContract() : carrierContractId " + carrierContractId);
		Object obj = null;

		try {
			Object result = carrierContractService.deleteCarrierContract(carrierContractId);
			if (result instanceof Success) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.error("Exception inside CarrierContractController deleteCarrierContract() :" + e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0, CarrierContract_unable_to_delete_message, Iconstants.ERROR),
					HttpStatus.BAD_REQUEST);
		}

		logger.info("Inside CarrierContractController deleteCarrierContract() Ends, carrierContractId :"
				+ carrierContractId);
		return obj;
	}

	/**
	 * this method is used when we click on add button in carrierContract
	 * 
	 * @return master data for carrierContract
	 * @author
	 */
	@RequestMapping(value = "/openAdd", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object openAdd() {

		logger.info(" Inside CarrierContractController openAdd() Starts ");
		String json = null;

		try {
			CarrierContractModel carrierContractModel = carrierContractService.getOpenAdd();
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(carrierContractModel);
		} catch (Exception e) {
			logger.error("Exception inside CarrierContractController openAdd() :" + e.getMessage());
		}

		logger.info(" Inside CarrierContractController openAdd() Ends ");
		return json;
	}

	/**
	 * this method is used to update the carrierContract based on carrierContractId
	 * 
	 * @param carrierContractId
	 * @param carrierContract
	 * @return List<carrierContract>
	 * @author sumit
	 */
	@RequestMapping(value = "/{carrierContractId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Object updateCarrierContract(@PathVariable("carrierContractId") Long carrierContractId,
			@RequestBody CarrierContractModel CarrierContractModel) {

		logger.info("Inside CarrierContractController updateCarrierContract() starts, carrierContractId : "
				+ carrierContractId);
		Object obj = null;

		try {
			Object result = carrierContractService.updateCarrierContract(carrierContractId, CarrierContractModel);

			if (result instanceof Success) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.error("Exception inside CarrierContractController updateCarrierContract() :" + e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0, CarrierContract_unable_to_update_message, Iconstants.ERROR),
					HttpStatus.BAD_REQUEST);
		}

		logger.info("Inside CarrierContractController updateCarrierContract() Ends, carrierContractId :"
				+ carrierContractId);
		return obj;
	}

	/**
	 * this method is used to delete the carrierContract based on
	 * carrierContractId
	 * 
	 * @param carrierContractId
	 * @return List<carrierContract>
	 * @author sumit
	 */
	@RequestMapping(value = "/{carrierContractId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getCarrierContractById(@PathVariable("carrierContractId") Long carrierContractId) {

		logger.info("Inside CarrierContractController getCarrierContractById() : carrierContractId " + carrierContractId);
		Object obj = new String();

		try {
			obj = carrierContractService.getCarrierContractById(carrierContractId);
		} catch (Exception e) {
			logger.error("Exception inside CarrierContractController getCarrierContractById() :" + e.getMessage());
		}

		logger.info("Inside CarrierContractController getCarrierContractById() Ends, carrierContractId :"
				+ carrierContractId);
		return obj;
	}

}
