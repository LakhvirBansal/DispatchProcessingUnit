package com.dpu.dao;

import java.util.List;

import org.hibernate.Session;

import com.dpu.entity.CarrierAdditionalContacts;

public interface CarrierAdditionalContactsDao extends GenericDao<CarrierAdditionalContacts>{

	void deleteAdditionalContact(CarrierAdditionalContacts companyAdditionalContacts, Session session);
	List<CarrierAdditionalContacts> getAdditionalContactsByCarrierId(Long carrierId,Session session);
	void insertAdditionalContacts(CarrierAdditionalContacts comAdditionalContact, Session session);
	public CarrierAdditionalContacts findById(Session session, Long id);
}
