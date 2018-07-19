/**
 * 
 */
package com.dpu.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dpu.model.ProvinceStateResponse;
import com.dpu.service.ProvinceStateService;

/**
 * @author gagan
 *
 */
@RestController
@RequestMapping(value = "province")
public class ProvinceStateController {

	Logger logger = Logger.getLogger(ProvinceStateController.class);

	@Autowired
	ProvinceStateService provinceStateService;

	ObjectMapper mapper = new ObjectMapper();

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object searchProvinces() {
		
		logger.info("Inside ProvinceStateController searchProvinces() Starts");
		
		String json = new String();
		try {
			List<ProvinceStateResponse> provinceList = provinceStateService.getProvinces("");
			if (provinceList != null && provinceList.size() > 0) {
				json = mapper.writeValueAsString(provinceList);
			}
		} catch (Exception e) {
			logger.error("Exception inside ProvinceStateController searchProvinces() Exception is :" + e);
		}
		
		logger.info(" Inside ProvinceStateController searchProvinces() ENDS");
		
		return json;
	}
	
	@RequestMapping(value = "/{provinceName}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Object searchProvinces(@PathVariable("provinceName") String provinceName) {
		
		logger.info("Inside ProvinceStateController searchProvinces() Starts, provinceName :" + provinceName);
		
		String json = new String();
		try {
			List<ProvinceStateResponse> provinceList = provinceStateService.getProvinces(provinceName);
			if (provinceList != null && provinceList.size() > 0) {
				json = mapper.writeValueAsString(provinceList);
			}
		} catch (Exception e) {
			logger.error("Exception inside ProvinceStateController searchProvinces() Exception is :" + e);
		}
		
		logger.info(" Inside ProvinceStateController searchProvinces() ENDS, provinceName :" + provinceName);
		
		return json;
	}

}
