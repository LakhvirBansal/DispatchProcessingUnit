package com.dpu.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.dpu.dao.CarrierContractDao;
import com.dpu.entity.CarrierContract;

@Repository
public class CarrierContractDaoImpl extends GenericDaoImpl<CarrierContract>
		implements CarrierContractDao {

	@Override
	public List<CarrierContract> findAllCarrierContract(Session session) {

		StringBuilder sb = new StringBuilder(
				" select cc from CarrierContract cc join fetch cc.carrier join fetch cc.arrangedWith join fetch cc.driver join fetch cc.currency ")
				.append(" join fetch cc.category join fetch cc.role  join fetch cc.equipment join fetch cc.commodity join fetch cc.division join fetch cc.dispatcher");
		// StringBuilder sb = new StringBuilder("from CarrierContract");
		Query query = session.createQuery(sb.toString());
		return query.list();
	}

}
