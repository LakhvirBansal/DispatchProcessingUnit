/**
 * 
 */
package com.dpu.dao;

import org.hibernate.Session;

import com.dpu.entity.Equipment;
import com.dpu.model.EquipmentReq;

/**
 * @author jagvir
 *
 */
public interface EquipmentDao extends GenericDao<Equipment> {

	Equipment add(Session session, EquipmentReq equipmentReq);

}
