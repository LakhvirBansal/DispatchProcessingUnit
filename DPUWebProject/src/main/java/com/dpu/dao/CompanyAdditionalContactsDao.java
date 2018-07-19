package com.dpu.dao;

import java.util.List;

import org.hibernate.Session;

import com.dpu.entity.CompanyAdditionalContacts;

/**
 * @author gagandeep
 *
 */

public interface CompanyAdditionalContactsDao extends GenericDao<CompanyAdditionalContacts> {

	List<CompanyAdditionalContacts> getContactsByCompanyID(Long companyId,Session session);

	void insertAdditionalContacts(CompanyAdditionalContacts comAdditionalContact, Session session);

	void deleteAdditionalContact(CompanyAdditionalContacts companyAdditionalContacts, Session session);

}
