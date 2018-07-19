package com.dpu.service;

import java.util.List;

import com.dpu.model.TruckModel;

public interface TruckService {

	Object update(Long id, TruckModel truckResponse);

	Object delete(Long id);

	TruckModel get(Long id);

	List<TruckModel> getAllTrucks(String owner);

	Object add(TruckModel truckResponse);
	
	TruckModel getOpenAdd();
	
	List<TruckModel> getSpecificData();

}
