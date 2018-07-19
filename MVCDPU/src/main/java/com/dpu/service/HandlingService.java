package com.dpu.service;

import java.util.List;
import com.dpu.model.HandlingModel;

public interface HandlingService {
	Object update(Long id, HandlingModel handlingModel);

	Object delete(Long id);

	List<HandlingModel> getAll();

	HandlingModel getOpenAdd();

	HandlingModel get(Long id);
	
	List<HandlingModel> getSpecificData();

	Object addHandling(HandlingModel handlingModel);

	List<HandlingModel> getHandlingByHandlingName(String handlingName);

}
