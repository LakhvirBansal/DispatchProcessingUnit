/**
 * 
 */
package com.dpu.dao;

import java.util.List;

import org.hibernate.Session;

import com.dpu.entity.Division;
import com.dpu.model.DivisionReq;

/**
 * @author jagvir
 *
 */
public interface DivisionDao extends GenericDao<Division> {

	Division add(Session session, DivisionReq divisionReq);

	List<Division> findDivisionsByName(String divisionName, Session session);

}
