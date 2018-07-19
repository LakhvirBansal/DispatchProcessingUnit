package com.dpu.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dpu.dao.ProvinceStateDao;
import com.dpu.entity.ProvinceState;
import com.dpu.model.ProvinceStateResponse;
import com.dpu.service.ProvinceStateService;

@Component
public class ProvinceStateServiceImpl implements ProvinceStateService {
	
	@Autowired
	ProvinceStateDao provinceStateDao;

	Logger logger = Logger.getLogger(ProvinceStateServiceImpl.class);

	public List<ProvinceStateResponse> getProvinces(String provinceName) {

		logger.info("ProvinceStateServiceImpl: getProvinces(): STARTS");
		
		List<ProvinceStateResponse> provinces = new ArrayList<ProvinceStateResponse>();

		try {
			Criterion criterion = Restrictions.like("stateName", provinceName, MatchMode.START);
			List<ProvinceState> listProvince = provinceStateDao.find(criterion);
			if(listProvince != null && listProvince.size() > 0) {
				for(ProvinceState provinceState : listProvince) {
					ProvinceStateResponse response = new ProvinceStateResponse();
					response.setStateId(provinceState.getStateId());
					response.setStateName(provinceState.getStateName());
					response.setStateCode(provinceState.getStateCode());
					provinces.add(response);
				}
			}
		} catch(Exception e) {
			logger.error("ProvinceStateServiceImpl: getProvinces(): Exception: " + e.getMessage());
		} 
		
		logger.info("ProvinceStateServiceImpl: getProvinces(): ENDS");

		return provinces;
	}
}