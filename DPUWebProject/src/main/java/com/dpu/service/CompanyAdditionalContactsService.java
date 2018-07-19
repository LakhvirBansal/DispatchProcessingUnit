package com.dpu.service;

import java.util.List;

import org.hibernate.Session;

import com.dpu.entity.CompanyAdditionalContacts;

public interface CompanyAdditionalContactsService {

	CompanyAdditionalContacts add(CompanyAdditionalContacts companyAdditionalContacts);
	
	CompanyAdditionalContacts update(CompanyAdditionalContacts companyAdditionalContacts);
	
	boolean delete(Long additionalContactId);
	
	CompanyAdditionalContacts get(Long id);

	List<CompanyAdditionalContacts> getAll(Long id, Session session);
	
}
