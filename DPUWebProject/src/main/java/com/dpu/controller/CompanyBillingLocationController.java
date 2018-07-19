package com.dpu.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
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

import com.dpu.constants.Iconstants;
import com.dpu.entity.CompanyBillingLocation;
import com.dpu.model.BillingLocation;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.service.CompanyBillingLocationService;
import com.dpu.util.MessageProperties;


@RestController
@RequestMapping(value = "company/{companyid}/billinglocations")
public class CompanyBillingLocationController extends MessageProperties {

	Logger logger = Logger.getLogger(CompanyBillingLocationController.class);
	
	@Autowired
	CompanyBillingLocationService companyBillingLocationService;
	
	ObjectMapper mapper = new ObjectMapper();

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAll(@PathVariable("companyid") Long companyId) {
		logger.info("[getAll] : Enter : ");
		String json = null;
		try {
			List<CompanyBillingLocation> lstCompanyBillingLocations = null;
					//companyBillingLocationService.getAll(companyId);
			List<BillingLocation> lstBillingLocations = new ArrayList<BillingLocation>();
			for(CompanyBillingLocation cbl : lstCompanyBillingLocations) {
				BillingLocation location = new BillingLocation();
				BeanUtils.copyProperties(location, cbl);
				lstBillingLocations.add(location);
			}
			json = mapper.writeValueAsString(lstBillingLocations);
		} catch (Exception e) {
			System.out.println(e);
		}
		logger.info("[getAll] : Exit : ");
		return json;
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public Object add(@RequestBody CompanyBillingLocation companyBillingLocation) {
		logger.info("[add] : Enter : ");
		Object obj = null;
		try {
			CompanyBillingLocation response = companyBillingLocationService.add(companyBillingLocation);
			if(response != null) {
				obj = new ResponseEntity<Object>(new Success(Integer.parseInt(companyAddedCode), companyAddedMessage, Iconstants.SUCCESS), HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(new Failed(Integer.parseInt(companyUnableToAddCode), companyUnableToAddMessage, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {	
			System.out.println(e);
		}
		logger.info("[add] : Exit : ");
		return obj;
	}

	/**
	 * this method is used to delete particular billing location based on billingLocationId
	 * @param id
	 * @return object
	 * @author lakhvir
	 */
	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public Object delete(@PathVariable("id") Long id) {
		logger.info("Inside CompanyBillingLocationController delete() starts, billingocationId: "+id);
		Object obj = null;
		boolean result = false;

		try {
			result = companyBillingLocationService.delete(id);
			if(result) {
				obj = new ResponseEntity<Object>(new Success(Integer.parseInt(companyDeletedCode), companyDeletedMessage, Iconstants.SUCCESS), HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(new Failed(Integer.parseInt(companyUnableToDeleteCode), companyUnableToDeleteMessage, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.error("Excepton inside CompanyBillingLocationController delete() :"+e.getMessage());
		}
		
		logger.info("Inside CompanyBillingLocationController delete() Ends, billingocationId: "+id);
		return obj;
	}

	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Object update(@PathVariable("id") int id, @RequestBody CompanyBillingLocation companyBillingLocation) {
		logger.info("[update] : Enter : Id : "+id);
		Object obj = null;
		try {
			//companyBillingLocation.setBillingLocationId(id);
			CompanyBillingLocation response = companyBillingLocationService.update(companyBillingLocation);
			if(response != null) {
				obj = new ResponseEntity<Object>(new Success(Integer.parseInt(companyUpdateCode), companyUpdateMessage, Iconstants.SUCCESS), HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(new Failed(Integer.parseInt(companyUnableToUpdateCode), companyUnableToUpdateMessage, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {	
			System.out.println(e);
		}
		logger.info("[update] : Exit ");
		return obj;
	}

	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object get(@PathVariable("id") Long id) {
		logger.info("[get] : Enter : Id : "+id);
		String json = new String();
		try {
			CompanyBillingLocation companyBillingLocation = companyBillingLocationService.get(id);
			if(companyBillingLocation != null) {
				json = mapper.writeValueAsString(companyBillingLocation);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		logger.info("[get] : Exit ");
		return json;
	}
}
