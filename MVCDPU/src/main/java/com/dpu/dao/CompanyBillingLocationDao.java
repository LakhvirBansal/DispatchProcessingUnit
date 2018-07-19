package com.dpu.dao;

import java.util.List;

import org.hibernate.Session;

import com.dpu.entity.CompanyBillingLocation;

/**
 * @author gagandeep
 *
 */

public interface CompanyBillingLocationDao extends GenericDao<CompanyBillingLocation> {

	List<CompanyBillingLocation> getBillingLocationsByCompanyId(Long companyId, Session session);

	void insertBillingLocation(CompanyBillingLocation comBillingLocation, Session session);

	void deleteBillingLocation(CompanyBillingLocation companyBillingLocation,Session session);

}
