package com.dpu.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dpu.dao.CarrierAdditionalContactsDao;
import com.dpu.entity.CarrierAdditionalContacts;
import com.dpu.model.CarrierAdditionalContactsModel;
import com.dpu.service.CarrierAdditionalContactService;

@Component
public class CarrierAdditionalContactServiceImpl implements
		CarrierAdditionalContactService {

	@Autowired
	CarrierAdditionalContactsDao carrierAdditionalContactsDao;

	@Override
	public List<CarrierAdditionalContacts> getAll(Long carrierId,
			Session session) {

		List<CarrierAdditionalContacts> additionalContacts = carrierAdditionalContactsDao
				.getAdditionalContactsByCarrierId(carrierId, session);
		return additionalContacts;
	}

	@Override
	public List<CarrierAdditionalContactsModel> getAll() {

		List<CarrierAdditionalContacts> additionalContacts = carrierAdditionalContactsDao
				.findAll();
		List<CarrierAdditionalContactsModel> reponse = new ArrayList<CarrierAdditionalContactsModel>();

		if (additionalContacts != null) {
			for (CarrierAdditionalContacts carrierAdditionalContact : additionalContacts) {
				CarrierAdditionalContactsModel carrierAdditionalContactsModel = new CarrierAdditionalContactsModel();
				BeanUtils.copyProperties(carrierAdditionalContact,
						carrierAdditionalContactsModel);
				reponse.add(carrierAdditionalContactsModel);
			}
		}
		return reponse;
	}

}
