package com.dpu.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import com.dpu.dao.CarrierDao;
import com.dpu.entity.Carrier;
import com.dpu.entity.CarrierAdditionalContacts;
import com.dpu.model.CarrierAdditionalContactsModel;
import com.dpu.model.CarrierModel;

@Repository
public class CarrierDaoImpl extends GenericDaoImpl<Carrier> implements
		CarrierDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<CarrierModel> getAllCarrier(Session session) {

		StringBuilder sb = new StringBuilder("from Carrier");
		Query query = session.createQuery(sb.toString());
		return query.list();
	}

	@Override
	public Carrier findById(Long carrierId, Session session) {

		StringBuilder sb = new StringBuilder(
				" select c from Carrier c where c.carrierId =:carrierId ");
		Query query = session.createQuery(sb.toString());
		query.setParameter("carrierId", carrierId);
		return (Carrier) query.uniqueResult();

	}

	@Override
	public CarrierAdditionalContacts findByAdditionalContactId(Long contactId,
			Session session) {

		StringBuilder sb = new StringBuilder(
				" select c from CarrierAdditionalContacts c where c.additionalContactId =:contactId ");
		Query query = session.createQuery(sb.toString());
		query.setParameter("contactId", contactId);
		return (CarrierAdditionalContacts) query.uniqueResult();

	}

	@Override
	public void deleteCarrier(Carrier carrier, Session session) {

		session.delete(carrier);

	}

	@Override
	public void updateData(Carrier carrier, CarrierModel carrierResponse,
			Session session) {

		String[] ignoreProp = new String[1];
		ignoreProp[0] = "carrierId";
		BeanUtils.copyProperties(carrierResponse, carrier, ignoreProp);
		session.saveOrUpdate(carrier);

		List<CarrierAdditionalContactsModel> additionalContactsList = carrierResponse
				.getCarrierAdditionalContactModel();
		if (additionalContactsList != null && !additionalContactsList.isEmpty()) {
			for (CarrierAdditionalContactsModel additionalContacts : additionalContactsList) {
				CarrierAdditionalContacts comAdditionalContacts = new CarrierAdditionalContacts();
				BeanUtils.copyProperties(additionalContacts,
						comAdditionalContacts);
				comAdditionalContacts.setCarrier(carrier);
				// comAdditionalContacts.setStatus(statusService.get(additionalContacts.getStatusId()));
				session.saveOrUpdate(comAdditionalContacts);
			}
		}
	}

	@Override
	public Carrier insertCarrierData(Carrier carrier, Session session) {

		session.save(carrier);
		return carrier;

	}

	@Override
	public List<Carrier> getCarriersByCarrierName(String carrierName,
			Session session) {

		StringBuilder builder = new StringBuilder();
		builder.append("select c from Carrier c where c.name like :carrierName ");
		Query query = session.createQuery(builder.toString());
		query.setParameter("carrierName", "%" + carrierName + "%");
		return query.list();
	}

	@Override
	public List<Object[]> findCarrierIdAndName(Session session) {

		StringBuilder builder = new StringBuilder();
		builder.append("select carrierId,name from Carrier");
		Query query = session.createQuery(builder.toString());
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CarrierAdditionalContacts> getAdditionalContactsByCarrierId(
			Long carrierId, Session session) {

		List<CarrierAdditionalContacts> carrierAdditionalContacts = (List<CarrierAdditionalContacts>) session
				.createQuery(
						"from CarrierAdditionalContacts ca  where ca.carrier = "
								+ carrierId + ")").list();
		return carrierAdditionalContacts;
	}

}
