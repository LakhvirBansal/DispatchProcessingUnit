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
import com.dpu.model.CarrierAdditionalContactsModel;
import com.dpu.model.CarrierModel;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.service.CarrierService;

@RestController
@RequestMapping(value = "carrier")
public class CarrierController {

	Logger logger = Logger.getLogger(CarrierController.class);

	@Autowired
	CarrierService carrierService;

	@Value("${carrier_unable_to_add_message}")
	private String carrier_unable_to_add_message;

	@Value("${carrier_unable_to_delete_message}")
	private String carrier_unable_to_delete_message;

	@Value("${carrier_unable_to_update_message}")
	private String carrier_unable_to_update_message;

	@Value("${carrierAdditionalContact_unable_to_delete_message}")
	private String carrierAdditionalContact_unable_to_delete_message;

	ObjectMapper mapper = new ObjectMapper();

	/**
	 * this method is used to get all the carrier
	 * 
	 * @return List<carrier>
	 * @author sumit
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAllCarrier() {

		logger.info("CarrierController getAllCarrier() starts");
		String json = new String();

		try {
			List<CarrierModel> carrierResponse = carrierService.getAll();

			if (carrierResponse != null) {
				json = mapper.writeValueAsString(carrierResponse);
			}

		} catch (Exception e) {
			logger.info("Exception inside CarrierController getAllCarrier() :"
					+ e.getMessage());
		}

		logger.info("CarrierController getAllCarrier() ends");
		return json;
	}

	/**
	 * this method is used to delete  the carrier based on carrierId
	 * @param carrierId
	 * @return List<carrier>
	 * @author sumit
	 */
	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public Object deleteCarrier(@PathVariable("id") Long carrierId) {

		logger.info(" CarrierController delete() starts , carrierId  : "
				+ carrierId);
		Object obj = null;

		try {
			obj = carrierService.delete(carrierId);
			if (obj instanceof Success) {
				obj = new ResponseEntity<Object>(obj, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(obj, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.info("Exception inside CarrierController delete() :"
					+ e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0,
					carrier_unable_to_delete_message, Iconstants.ERROR),
					HttpStatus.BAD_REQUEST);
		}

		logger.info(" CarrierController delete() ends ");
		return obj;
	}

	/**
	 * this method is used to get  the carrier based on carrierId
	 * @param carrierId
	 * @return  carrier
	 * @author sumit
	 */
	@RequestMapping(value = "/{carrierId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getCarrierByCarrierId(@PathVariable("carrierId") Long carrierId) {

		logger.info(" CarrierController get() starts, carrierId :" + carrierId);
		String json = new String();

		try {
			CarrierModel carrierResponse = carrierService.get(carrierId);
			if (carrierResponse != null) {
				json = mapper.writeValueAsString(carrierResponse);
			}
		} catch (Exception e) {
			logger.info("Exception inside CarrierController get() :"
					+ e.getMessage());
		}

		logger.info(" CarrierController get() ends, carrierId :" + carrierId);
		return json;
	}

	/**
	 * this method is used to add carrier
	 * 
	 * @param carrierResponse
	 * @return all carries data
	 * @author sumit
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public Object addNewCarrier(@RequestBody CarrierModel carrierResponse) {

		logger.info("CarrierController add() starts");
		Object obj = null;

		try {
			obj = carrierService.addCarrierData(carrierResponse);

			if (obj instanceof Success) {
				obj = new ResponseEntity<Object>(obj, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(obj, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			logger.info("Exception inside carrierController add() :"
					+ e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0,
					carrier_unable_to_add_message, Iconstants.ERROR),
					HttpStatus.BAD_REQUEST);
		}

		logger.info("carrierController add() Ends");
		return obj;
	}

	/**
	 * this method is used to get  the contact based on contactId
	 * @param contactId
	 * @return  CarrierAdditionalContactsModel
	 * @author sumit
	 */
	@RequestMapping(value = "/contactId/{contactId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getContactById(@PathVariable("contactId") Long contactId) {

		logger.info(" CarrierController getContactById() starts, contactId :"
				+ contactId);
		String json = new String();

		try {
			CarrierAdditionalContactsModel additionalContact = carrierService
					.getContactById(contactId);
			if (additionalContact != null) {
				json = mapper.writeValueAsString(additionalContact);
			}
		} catch (Exception e) {
			logger.info("Exception inside CarrierController getContactById() :"
					+ e.getMessage());
		}

		logger.info(" CarrierController getContactById() ends, contactId :"
				+ contactId);
		return json;
	}

	/**
	 * this method is used to get  the List of contact based on carrierId
	 * @param carrierId
	 * @return  List<CarrierAdditionalContactsModel>
	 * @author sumit
	 */
	@RequestMapping(value = "/carrierId/{carrierId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getContactByCarriertId(@PathVariable("carrierId") Long carrierId) {

		logger.info(" CarrierController getContactByCarriertId() starts, carrierId :"
				+ carrierId);
		String json = new String();

		try {
			List<CarrierAdditionalContactsModel> additionalContact = carrierService
					.getContactByCarrierId(carrierId);
			if (additionalContact != null) {
				json = mapper.writeValueAsString(additionalContact);
				System.out.println("json : " + json);
			}
		} catch (Exception e) {
			logger.info("Exception inside CarrierController getContactByCarriertId() :"
					+ e.getMessage());
		}

		logger.info(" CarrierController getContactByCarriertId() ends, carrier :"
				+ carrierId);
		return json;
	}

	/**
	 * this method is used to searchCarrier based on carrier Name
	 * 
	 * @param carrierName
	 * @return List<Carrier>
	 */
	@RequestMapping(value = "/{carrierName}/search", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object searchCarrier(@PathVariable("carrierName") String carrierName) {

		logger.info("Inside CarrierController searchCarrier() Starts, carrierName :"
				+ carrierName);
		String json = new String();

		try {
			List<CarrierModel> carrierList = carrierService
					.getCarriersByCarrierName(carrierName);
			if (carrierList != null && carrierList.size() > 0) {
				json = mapper.writeValueAsString(carrierList);
			}
		} catch (Exception e) {
			logger.error("Exception inside CarrierController searchCarrier() is :"
					+ e.getMessage());
		}

		logger.info(" Inside CarrierController searchCarrier() Ends, carrierName :"
				+ carrierName);
		return json;
	}

	/**
	 * this method is used to getAll carrierName and carierId
	 * 
	 *  
	 * @return List<Carrier>
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET, value = "/IdAndName")
	public Object getAllCarriersIdAndName() {

		logger.info("CarrierController getAllCarriersIdAndName() starts");
		String json = new String();

		try {
			List<CarrierModel> carrierResponse = carrierService
					.getAllCarriersIdAndName();

			if (carrierResponse != null) {
				json = mapper.writeValueAsString(carrierResponse);
			}

		} catch (Exception e) {
			logger.info("Exception inside CarrierController getAllCarrier() :"
					+ e.getMessage());
		}

		logger.info("CarrierController getAllCarrier() ends");
		return json;
	}

	/**
	 * this method is used to updateCarrier based on carrierId
	 * 
	 * @param carrierId
	 * @return List<Carrier>
	 */
	@RequestMapping(value = "/{carrierId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Object updateCarrier(@PathVariable("carrierId") Long carrierId,
			@RequestBody CarrierModel carrierResponse) {

		logger.info(" CarrierController updateCarrier() starts, carrierId :"
				+ carrierId);
		Object obj = null;

		try {
			obj = carrierService.updateCarrier(carrierId, carrierResponse);
			if (obj instanceof Success) {
				obj = new ResponseEntity<Object>(obj, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(obj, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.info("Exception inside CarrierController updateCarrier() :"
					+ e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0,
					carrier_unable_to_update_message, Iconstants.ERROR),
					HttpStatus.BAD_REQUEST);
		}

		logger.info(" CarrierController updateCarrier() ends, carrierId :" + carrierId);
		return obj;
	}

	/**
	 * this method is used to delete the contact based on contactId
	 * @param contactId
	 *  
	 * @author sumit
	 */
	@RequestMapping(value = "/contactId/{contactId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public Object deleteContactByContactId(
			@PathVariable("contactId") Long contactId) {

		logger.info(" CarrierController deleteContactByContactId() starts ");
		Object obj = null;

		try {
			obj = carrierService
					.deleteAdditionalContactByAdditionalContactId(contactId);
			if (obj instanceof Success) {
				obj = new ResponseEntity<Object>(obj, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(obj, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.info("Exception inside CarrierController deleteContactByContactId() :"
					+ e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0,
					carrierAdditionalContact_unable_to_delete_message,
					Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}

		logger.info(" CarrierController deleteContactByContactId() ends ");
		return obj;
	}

}
