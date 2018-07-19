package com.dpu.dao.impl;


import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.dpu.dao.CompanyAdditionalContactsDao;
import com.dpu.entity.CompanyAdditionalContacts;

@Repository
public class CompanyAdditionalContactsDaoImpl extends GenericDaoImpl<CompanyAdditionalContacts> implements CompanyAdditionalContactsDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<CompanyAdditionalContacts> getContactsByCompanyID(Long companyId, Session session) {
		StringBuilder sb = new StringBuilder(" select cac from CompanyAdditionalContacts cac join fetch cac.status where cac.company.companyId =:companyId " );
		Query query = session.createQuery(sb.toString());
		query.setParameter("companyId", companyId);
		return query.list();
	}

	@Override
	public void insertAdditionalContacts(CompanyAdditionalContacts comAdditionalContact, Session session) {
		session.save(comAdditionalContact);
	}

	@Override
	public void deleteAdditionalContact(CompanyAdditionalContacts companyAdditionalContacts, Session session) {
		session.delete(companyAdditionalContacts);
		
	}

}
