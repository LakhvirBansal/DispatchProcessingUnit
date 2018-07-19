package com.dpu.service.impl;

import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dpu.dao.CompanyBillingLocationDao;
import com.dpu.entity.CompanyBillingLocation;
import com.dpu.service.CompanyBillingLocationService;

@Component
public class CompanyBillingLocationServiceImpl implements CompanyBillingLocationService{

	@Autowired
	CompanyBillingLocationDao companyBillingLocationDao;
	
	@Override
	public CompanyBillingLocation add(CompanyBillingLocation companyBillingLocation) {
		return companyBillingLocationDao.save(companyBillingLocation);
	}

	@Override
	public CompanyBillingLocation update(CompanyBillingLocation companyBillingLocation) {
		return companyBillingLocationDao.update(companyBillingLocation);
	}

	@Override
	public boolean delete(Long billingId) {
		boolean result = false;
		try {
			CompanyBillingLocation billingLocation = get(billingId);
			if(billingLocation != null){
				companyBillingLocationDao.delete(billingLocation);
				result = true;
			}
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	@Override
	public List<CompanyBillingLocation> getAll(Long companyId, Session session) {
		List<CompanyBillingLocation> billingLocations = companyBillingLocationDao.getBillingLocationsByCompanyId(companyId,session);
		return billingLocations;
	}

	@Override
	public CompanyBillingLocation get(Long id) {
		return companyBillingLocationDao.findById(id);
	}
}
