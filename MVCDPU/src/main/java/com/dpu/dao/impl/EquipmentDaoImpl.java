/**
 * 
 */
package com.dpu.dao.impl;

import java.util.Date;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.dpu.dao.EquipmentDao;
import com.dpu.entity.Equipment;
import com.dpu.entity.Type;
import com.dpu.model.EquipmentReq;

/**
 * @author jagvir
 *
 */
@Repository
public class EquipmentDaoImpl extends GenericDaoImpl<Equipment> implements EquipmentDao {

	@Override
	public Equipment add(Session session, EquipmentReq equipmentReq) {

		logger.info("EquipmentDaoImpl: add(): STARTS");
		Equipment equipment = null;
		try {
			
			equipment = setEquipmentValues(equipmentReq);
			Type type = (Type) session.get(Type.class, equipmentReq.getTypeId());
			equipment.setType(type);
			
			Long equipmentId = (Long) session.save(equipment);
			equipment.setEquipmentId(equipmentId);
		} catch (Exception e) {
			logger.fatal("EquipmentDaoImpl: add(): Exception: " + e.getMessage());
		}
		
		logger.info("EquipmentDaoImpl: add(): ENDS");

		return equipment;
	}
	
	private Equipment setEquipmentValues(EquipmentReq equipmentReq) {
		
		logger.info("EquipmentDaoImpl: setEquipmentValues(): STARTS");

		Equipment equipment = new Equipment();
		equipment.setEquipmentName(equipmentReq.getEquipmentName());
		equipment.setDescription(equipmentReq.getDescription());
		equipment.setCreatedBy("gagan");
		equipment.setCreatedOn(new Date());
		equipment.setModifiedBy("gagan");
		equipment.setModifiedOn(new Date());
		
		logger.info("EquipmentDaoImpl: setEquipmentValues(): ENDS");

		return equipment;
	}
	
}
