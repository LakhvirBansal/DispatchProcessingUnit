package com.dpu.dao.impl;


import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.dpu.dao.CompanyBillingLocationDao;
import com.dpu.entity.CompanyBillingLocation;

@Repository
public class CompanyBillingLocationDaoImpl extends GenericDaoImpl<CompanyBillingLocation> implements CompanyBillingLocationDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<CompanyBillingLocation> getBillingLocationsByCompanyId(Long companyId, Session session) {
		StringBuilder sb = new StringBuilder(" select cbl from CompanyBillingLocation cbl join fetch cbl.status where cbl.company.companyId =:companyId " );
		Query query = session.createQuery(sb.toString());
		query.setParameter("companyId", companyId);
		return query.list();
	}

	@Override
	public void insertBillingLocation(CompanyBillingLocation comBillingLocation, Session session) {
		session.save(comBillingLocation);
	}

	@Override
	public void deleteBillingLocation(CompanyBillingLocation companyBillingLocation, Session session) {
		session.delete(companyBillingLocation);
		
	}

}
