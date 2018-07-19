/**
 * 
 */
package com.dpu.service;

import java.util.List;

import com.dpu.model.DivisionReq;

/**
 * @author jagvir
 *
 */
public interface DivisionService {
	
	Object update(Long id, DivisionReq divisionReq);

	Object delete(Long id);

	DivisionReq get(Long id);

	List<DivisionReq> getAll(String divisionName);

	Object add(DivisionReq divisionReq);
}
