package com.dpu.service;

import java.util.List;

import org.hibernate.Session;

import com.dpu.model.ShipperModel;

public interface ShipperService {

	Object add(ShipperModel shipperResponse);

	Object delete(Long id);

	List<ShipperModel> getAll();

	ShipperModel get(Long id);

	ShipperModel getMasterData();

	List<ShipperModel> getShipperByCompanyName(String companyName);

	Object update(Long id, ShipperModel shipperResponse);
	
	List<ShipperModel> getSpecificData(Session session);

	ShipperModel getParticularData(Long id);
}
