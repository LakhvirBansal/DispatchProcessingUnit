package com.dpu.service;

import java.util.List;

import org.hibernate.Session;

import com.dpu.entity.CarrierAdditionalContacts;
import com.dpu.model.CarrierAdditionalContactsModel;

public interface CarrierAdditionalContactService {

	public List<CarrierAdditionalContacts> getAll(Long carrierId, Session session);

	List<CarrierAdditionalContactsModel> getAll();

}
