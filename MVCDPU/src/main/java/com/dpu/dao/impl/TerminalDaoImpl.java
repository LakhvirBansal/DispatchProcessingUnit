package com.dpu.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dpu.dao.TerminalDao;
import com.dpu.entity.Terminal;

@Repository
@Transactional
public class TerminalDaoImpl extends GenericDaoImpl<Terminal> implements TerminalDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Terminal> findAll(String terminalName, Session session) {
		
		StringBuilder queryAppender = new StringBuilder("select t from Terminal t join fetch t.shipper shipper ");
		if(terminalName != null) {
			queryAppender.append(" where t.terminalName like '%" + terminalName + "%'");
		}
		List<Terminal> terminalList = session.createQuery(queryAppender.toString()).list();
		return terminalList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Terminal findById(Session session, Long terminalId) {
		
		StringBuilder queryAppender = new StringBuilder("select t from Terminal t join fetch t.shipper shipper where t.terminalId =:terminalid");
		Query query = session.createQuery(queryAppender.toString());
		query.setParameter("terminalid", terminalId);
		List<Terminal> terminalList = query.list();
		if(terminalList != null && terminalList.size() > 0) {
			return terminalList.get(0);
		}
		return null;
	}

}
