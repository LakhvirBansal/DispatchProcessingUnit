package com.dpu.service;

import java.util.List;

import com.dpu.model.CustomBrokerResponse;

public interface CustomBrokerService {
	Object add(CustomBrokerResponse customBrokerReponse);

	Object delete(Long id);

	List<CustomBrokerResponse> getAll();

	CustomBrokerResponse get(Long id);

	CustomBrokerResponse getOpenAdd();

	Object update(Long id, CustomBrokerResponse customBrokerService);

	List<CustomBrokerResponse> getCustomBrokerByCustomBrokerName(String customBrokerName);
	
}
