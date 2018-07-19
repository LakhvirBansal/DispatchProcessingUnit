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
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.model.TaxCodeModel;
import com.dpu.service.TaxCodeService;

@RestController
@RequestMapping(value = "taxCode")
public class TaxCodeController {

	Logger logger = Logger.getLogger(TaxCodeController.class);

	@Autowired
	TaxCodeService taxCodeService;

	ObjectMapper mapper = new ObjectMapper();
	
	@Value("${taxcode_unable_to_add_message}")
	private String taxcode_unable_to_add_message;

	@Value("${taxcode_unable_to_delete_message}")
	private String taxcode_unable_to_delete_message;
	
	@Value("${taxcode_unable_to_update_message}")
	private String taxcode_unable_to_update_message;

	/**
	 * this method is used to get all taxCodes
	 * @return List<taxCode>
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAll() {

		logger.info("Inside TaxCodeController getAll() Starts ");
		String json = null;

		try {
			List<TaxCodeModel> responses = taxCodeService.getAll();
			if (responses != null && !responses.isEmpty()) {
				json = mapper.writeValueAsString(responses);
			}

		} catch (Exception e) {
			logger.error("Exception inside TaxCodeController getAll() :"+ e.getMessage());
		}
		
		logger.info("Inside TaxCodeController getAll() Ends ");
		return json;
	}

	/**
	 * this method is used to add the Tax code
	 * @param taxCodeModel
	 * @return List<Tax code>
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public Object add(@RequestBody TaxCodeModel taxCodeModel) {

		logger.info("Inside TaxCodeController add() starts ");
		Object obj = null;
		
		try {

			Object result = taxCodeService.addTaxCode(taxCodeModel);
			if (result instanceof Success) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			logger.error("Exception inside TaxCodeController add() :"+ e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0,taxcode_unable_to_add_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}
		
		logger.info("Inside TaxCodeController add() ends ");
		return obj;
	}

	/**
	 * this method is used to delete the taxCode based on taxcodeId
	 * @param id
	 * @return List<Tax codes>
	 */
	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public Object delete(@PathVariable("id") Long taxCodeId) {

		logger.info("Inside TaxCodeController delete() Starts, handlingId is :" + taxCodeId);
		Object obj = null;

		try {
			Object result = taxCodeService.delete(taxCodeId);
			if (result instanceof Success) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);

			}
		} catch (Exception e) {
			logger.error("Exception inside TaxCodeController delete() :"+ e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0,taxcode_unable_to_delete_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}
		
		logger.info("Inside TaxCodeController delete() Ends, id is :" + taxCodeId);
		return obj;

	}

	/**
	 * this method is used to update the taxCode based on taxCodeId
	 * @param taxCodeId
	 * @param taxCodeModel
	 * @return List<TaxCode>
	 */
	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Object update(@PathVariable("id") Long taxCodeId, @RequestBody TaxCodeModel taxCodeModel) {

		logger.info("Inside TaxCodeController update() Starts, taxCodeId is :" + taxCodeId);
		Object obj = null;
		try {
			Object result = taxCodeService.update(taxCodeId, taxCodeModel);
			if (result instanceof Success) {
				obj = new ResponseEntity<Object>(result, HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);

			}
		} catch (Exception e) {
			logger.error("Exception inside TaxCodeController update() :"+ e.getMessage());
			obj = new ResponseEntity<Object>(new Failed(0,taxcode_unable_to_update_message, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
		}

		logger.info("Inside TaxCodeController update() Ends, taxCodeId is :" + taxCodeId);
		return obj;
	}

	/**
	 * this method is used to get taxcode data based on taxcodeId
	 * @param taxCodeId
	 * @return taxCodeData
	 */
	@RequestMapping(value = "/{taxCodeId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getTaxcodeById(@PathVariable("taxCodeId") Long taxCodeId) {
		
		logger.info("Inside TaxCodeController getTaxcodeById() Starts, Id:"+ taxCodeId);
		String json = null;

		try {

			TaxCodeModel taxCodeModel = taxCodeService.get(taxCodeId);

			if (taxCodeModel != null) {
				json = mapper.writeValueAsString(taxCodeModel);
			}
		} catch (Exception e) {
			logger.error("Exception inside TaxCodeController getTaxcodeById() :"+ e.getMessage());
		}

		logger.info("Inside TaxCodeController getTaxcodeById() Ends, Id:"+ taxCodeId);
		return json;
	}

	/**
	 * this method is used when we click on add button on handling screen
	 * send master data
	 * @return master data for add handling
	 */
	@RequestMapping(value = "/openAdd", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object openAdd() {
		
		logger.info("Inside TaxCodeController openAdd() Starts ");
		String json = null;

		try {
			TaxCodeModel model = taxCodeService.getOpenAdd();
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(model);
		} catch (Exception e) {
			logger.error(" Exception inside TaxCodeController openAdd() :"+ e.getMessage());
		}

		logger.info("Inside TaxCodeController openAdd() ends ");
		return json;
	}

	/**
	 * this method is used to get taxcode data based on taxcode name
	 * @param taxCodeName
	 * @return List<TaxCode>
	 */
	@RequestMapping(value = "/{taxCodeName}/search", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object searchTaxCode(@PathVariable("taxCodeName") String taxCodeName) {

		logger.info("Inside TaxCodeController searchTaxCode() Starts, taxCodeName :"+ taxCodeName);
		String json = new String();

		try {
			List<TaxCodeModel> taxCodeList = taxCodeService.getTaxCodeByTaxCodeName(taxCodeName);
			if (taxCodeList != null && taxCodeList.size() > 0) {
				json = mapper.writeValueAsString(taxCodeList);
			}
		} catch (Exception e) {
			logger.error("Exception inside TaxCodeController searchTaxCode() is :"+ e.getMessage());
		}

		logger.info(" Inside TaxCodeController searchTaxCode() Ends, taxCodeName :"+ taxCodeName);
		return json;
	}

	/**
	 * this method is used to get specific taxcode data (id and name)
	 * @return taxcode Id and name
	 */
	
	@RequestMapping(value = "/specificData", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getSpecificData() {

		logger.info("Inside TaxCodeController getSpecificData() Starts ");
		String json = new String();

		try {
			List<TaxCodeModel> taxCodeList = taxCodeService.getSpecificData();
			if (taxCodeList != null && taxCodeList.size() > 0) {
				json = mapper.writeValueAsString(taxCodeList);
			}
		} catch (Exception e) {
			logger.error("Exception inside TaxCodeController getSpecificData() is :"+ e.getMessage());
		}

		logger.info("Inside TaxCodeController getSpecificData() Ends ");
		return json;
	}
}
