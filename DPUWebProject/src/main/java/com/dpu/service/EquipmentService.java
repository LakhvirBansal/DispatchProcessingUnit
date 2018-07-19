/**
 * 
 */
package com.dpu.service;

import java.util.List;

import com.dpu.model.EquipmentReq;

/**
 * @author jagvir
 *
 */
public interface EquipmentService {

	Object update(Long id, EquipmentReq equipmentReq);

	Object delete(Long id);

	EquipmentReq get(Long id);

	List<EquipmentReq> getAll(String equipmentName);

	Object add(EquipmentReq equipmentReq);

}
