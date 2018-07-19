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
import com.dpu.entity.CompanyAdditionalContacts;
import com.dpu.model.AdditionalContacts;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.service.CompanyAdditionalContactsService;
import com.dpu.util.MessageProperties;


@RestController
@RequestMapping(value = "company/{companyid}/additionalcontacts")
public class CompanyAdditionalContactsController extends MessageProperties {

	Logger logger = Logger.getLogger(CompanyAdditionalContactsController.class);
	
	@Autowired
	CompanyAdditionalContactsService companyAdditionalContactsService;
	
	ObjectMapper mapper = new ObjectMapper();

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object getAll(@PathVariable("companyid") Long companyId) {
		logger.info("[getAll] : Enter");
		String json = null;
		try {
			List<CompanyAdditionalContacts> lstCompanyAdditionalContacts = null;
			//companyAdditionalContactsService.getAll(companyId);
			List<AdditionalContacts> lstAdditionalContacts = new ArrayList<AdditionalContacts>();
			for(CompanyAdditionalContacts cac : lstCompanyAdditionalContacts) {
				AdditionalContacts contact = new AdditionalContacts();
				BeanUtils.copyProperties(contact, cac);
				lstAdditionalContacts.add(contact);
			}
			json = mapper.writeValueAsString(lstAdditionalContacts);
		} catch (Exception e) {
			
			System.out.println(e);
		}
		logger.info("[getAll] : Enter");
		return json;
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public Object add(@RequestBody CompanyAdditionalContacts companyAdditionalContacts) {
		logger.info("[add] : Enter");
		Object obj = null;
		try {
			CompanyAdditionalContacts response = companyAdditionalContactsService.add(companyAdditionalContacts);
			if(response != null) {
				obj = new ResponseEntity<Object>(new Success(Integer.parseInt(companyAddedCode), companyAddedMessage, Iconstants.SUCCESS), HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(new Failed(Integer.parseInt(companyUnableToAddCode), companyUnableToAddMessage, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {	
			System.out.println(e);
		}
		logger.info("[add] : Exit");
		return obj;
	}

	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public Object delete(@PathVariable("id") Long additionalContactId) {
		logger.info("[delete] : Enter : ID : "+additionalContactId);
		Object obj = null;
		boolean result = false;

		try {
			
			result = companyAdditionalContactsService.delete(additionalContactId);
			if(result) {
				obj = new ResponseEntity<Object>(new Success(Integer.parseInt(companyDeletedCode), companyDeletedMessage, Iconstants.SUCCESS), HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(new Failed(Integer.parseInt(companyUnableToDeleteCode), companyUnableToDeleteMessage, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		logger.info("[delete] : Exit ");
		return obj;
	}

	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Object update(@PathVariable("id") int id, @RequestBody CompanyAdditionalContacts companyAdditionalContacts) {
		logger.info("[update] : Enter : ID : "+id);
		Object obj = null;
		try {
			//companyAdditionalContacts.setAdditionalContactId(id);
			CompanyAdditionalContacts response = companyAdditionalContactsService.update(companyAdditionalContacts);
			if(response != null) {
				obj = new ResponseEntity<Object>(new Success(Integer.parseInt(companyUpdateCode), companyUpdateMessage, Iconstants.SUCCESS), HttpStatus.OK);
			} else {
				obj = new ResponseEntity<Object>(new Failed(Integer.parseInt(companyUnableToUpdateCode), companyUnableToUpdateMessage, Iconstants.ERROR), HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {	
			System.out.println(e);
		}
		logger.info("[update] : Exit:  : ");
		return obj;
	}

	@RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object get(@PathVariable("id") Long id) {
		logger.info("[get] : Enter : ID : "+id);
		String json = new String();
		try {
			CompanyAdditionalContacts companyAdditionalContacts = companyAdditionalContactsService.get(id);
			if(companyAdditionalContacts != null) {
				json = mapper.writeValueAsString(companyAdditionalContacts);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		logger.info("[get] : Enter : ID : "+id);
		return json;
	}
}
