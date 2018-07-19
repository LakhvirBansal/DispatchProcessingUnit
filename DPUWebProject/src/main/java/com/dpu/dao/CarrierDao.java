package com.dpu.dao;

import java.util.List;

import org.hibernate.Session;

import com.dpu.entity.Carrier;
import com.dpu.entity.CarrierAdditionalContacts;
import com.dpu.model.CarrierModel;

public interface CarrierDao extends GenericDao<Carrier> {

	List<CarrierModel> getAllCarrier(Session session);

	Carrier findById(Long carrierId, Session session);

	void deleteCarrier(Carrier carrier, Session session);

	void updateData(Carrier carrier, CarrierModel carrierResponse,
			Session session);

	Carrier insertCarrierData(Carrier carrier, Session session);

	List<Carrier> getCarriersByCarrierName(String carrierName, Session session);

	List<Object[]> findCarrierIdAndName(Session session);

	CarrierAdditionalContacts findByAdditionalContactId(Long contactId,
			Session session);

	List<CarrierAdditionalContacts> getAdditionalContactsByCarrierId(
			Long carrierId, Session session);
}
