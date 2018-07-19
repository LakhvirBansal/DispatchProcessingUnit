package com.dpu.service;

import java.util.List;

import org.hibernate.Session;

import com.dpu.model.CarrierAdditionalContactsModel;
import com.dpu.model.CarrierModel;

public interface CarrierService {

	List<CarrierModel> getAll( );

	Object delete(Long carrierId);

	Object updateCarrier(Long id, CarrierModel carrierResponse);

	CarrierModel get(Long id);

	Object addCarrierData(CarrierModel carrierResponse);

	CarrierAdditionalContactsModel getContactById(Long id);

	List<CarrierModel> getCarriersByCarrierName(String carrierName);

	List<CarrierModel> getAllCarriersIdAndName();

	Object deleteAdditionalContactByAdditionalContactId(Long contactId);

	List<CarrierAdditionalContactsModel> getContactByCarrierId(Long id);

}
