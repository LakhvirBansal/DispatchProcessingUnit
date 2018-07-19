package com.dpu.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.dpu.dao.TaxCodeDao;
import com.dpu.entity.TaxCode;

@Repository
public class TaxCodeDaoImpl extends GenericDaoImpl<TaxCode> implements TaxCodeDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<TaxCode> findAll(Session session) {
		
		StringBuilder sb = new StringBuilder(" select h from TaxCode h left join fetch h.accountForSale left join fetch h.accountForRevenue ");
		Query query = session.createQuery(sb.toString());
		return query.list();
	}

	@Override
	public TaxCode findById(Long id, Session session) {
		StringBuilder sb = new StringBuilder(" select h from TaxCode h left join fetch h.accountForSale left join fetch h.accountForRevenue where h.taxCodeId =:taxCodeId ");
		Query query = session.createQuery(sb.toString());
		query.setParameter("taxCodeId", id);
		return (TaxCode) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TaxCode> getTaxCodesByTaxCodeNames(Session session, String taxCodeName) {
		StringBuilder sb = new StringBuilder(" select h from TaxCode h left join fetch h.accountForSale left join fetch h.accountForRevenue where h.taxCode like :taxCodeName ");
		Query query = session.createQuery(sb.toString());
		query.setParameter("taxCodeName", "%"+taxCodeName+"%");
		return query.list();
	}

}
