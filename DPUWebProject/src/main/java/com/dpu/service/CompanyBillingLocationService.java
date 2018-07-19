package com.dpu.service;

import java.util.List;

import org.hibernate.Session;

import com.dpu.entity.CompanyBillingLocation;

public interface CompanyBillingLocationService {

	CompanyBillingLocation add(CompanyBillingLocation companyBillingLocation);
	
	CompanyBillingLocation update(CompanyBillingLocation companyBillingLocation);
	
	boolean delete(Long id);
	
	CompanyBillingLocation get(Long id);

	List<CompanyBillingLocation> getAll(Long id, Session session);
	
}
