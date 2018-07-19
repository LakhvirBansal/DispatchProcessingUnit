package com.dpu.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.dpu.dao.CarrierAdditionalContactsDao;
import com.dpu.entity.CarrierAdditionalContacts;

@Repository
public class CarrierAdditionalContactsDaoImpl extends
		GenericDaoImpl<CarrierAdditionalContacts> implements
		CarrierAdditionalContactsDao {

	@Override
	public void deleteAdditionalContact(
			CarrierAdditionalContacts carrierAdditionalContact, Session session) {

		session.delete(carrierAdditionalContact);

	}

	@Override
	public List<CarrierAdditionalContacts> getAdditionalContactsByCarrierId(
			Long contactId, Session session) {

		StringBuilder sb = new StringBuilder(
				" select cac from CarrierAdditionalContacts cac  where cac.additionalContactId =:contactId ");
		Query query = session.createQuery(sb.toString());
		query.setParameter("contactId", contactId);
		return query.list();

	}

	@Override
	public void insertAdditionalContacts(
			CarrierAdditionalContacts comAdditionalContact, Session session) {

		try {
			session.save(comAdditionalContact);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public CarrierAdditionalContacts findById(Session session, Long contactId) {

		StringBuilder sb = new StringBuilder(
				"select cac from CarrierAdditionalContacts cac  where cac.additionalContactId =:contactId ");
		Query query = session.createQuery(sb.toString());
		query.setParameter("contactId", contactId);
		@SuppressWarnings("unchecked")
		List<CarrierAdditionalContacts> listOfCarrierAdditionalContacts = query
				.list();
		return listOfCarrierAdditionalContacts.get(0);
	}

}
