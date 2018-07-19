package com.dpu.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.dpu.dao.VehicleMaintainanceCategoryDao;
import com.dpu.entity.VehicleMaintainanceCategory;

@Repository
public class VehicleMaintainanceCategoryDaoImpl extends GenericDaoImpl<VehicleMaintainanceCategory> implements VehicleMaintainanceCategoryDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleMaintainanceCategory> findAll(Session session) {
		
		StringBuilder sb = new StringBuilder(" from VehicleMaintainanceCategory ");
		Query query = session.createQuery(sb.toString());
		return query.list();
	}

	@Override
	public VehicleMaintainanceCategory findById(Long id, Session session) {
		StringBuilder sb = new StringBuilder(" from VehicleMaintainanceCategory where id =:vmcId ");
		Query query = session.createQuery(sb.toString());
		query.setParameter("vmcId", id);
		return (VehicleMaintainanceCategory) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleMaintainanceCategory> getVmcByVmcName(Session session, String vmcName) {
		StringBuilder sb = new StringBuilder(" select h from VehicleMaintainanceCategory h  where h.name like :vmcName ");
		Query query = session.createQuery(sb.toString());
		query.setParameter("vmcName", "%"+vmcName+"%");
		return query.list();
	}

}
