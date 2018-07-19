package com.dpu.service;

import java.util.List;

import com.dpu.entity.Trailer;
import com.dpu.model.TrailerModel;

public interface TrailerService {

	Object add(TrailerModel trailerRequest);
	
	Object delete(Long trailerId);
	
	Object update(Long trailerId, TrailerModel trailerRequest);
	
	List<TrailerModel> getAll();
	
	TrailerModel get(Long id);

	TrailerModel getOpenAdd();
	
	List<TrailerModel> getSpecificData();
}
