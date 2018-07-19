package com.dpu.dao;

import java.util.List;

import org.hibernate.Session;

import com.dpu.entity.VehicleMaintainanceCategory;
import com.dpu.model.VehicleMaintainanceCategoryModel;

public interface VehicleMaintainanceCategoryDao extends GenericDao<VehicleMaintainanceCategory> {

	List<VehicleMaintainanceCategory> findAll(Session session);

	VehicleMaintainanceCategory findById(Long id, Session session);

	List<VehicleMaintainanceCategory> getVmcByVmcName(Session session, String vmcName);

	/*List<Handling> findAll(Session session);

	Handling findById(Long id, Session session);

	List<Handling> getHandlingByHandlingName(Session session, String handlingName);*/

}
