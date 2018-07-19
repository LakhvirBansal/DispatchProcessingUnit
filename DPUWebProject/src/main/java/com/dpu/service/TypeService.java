package com.dpu.service;

import java.util.List;

import com.dpu.entity.Type;
import com.dpu.model.TypeResponse;

public interface TypeService {

	List<TypeResponse> getAll(Long typeValue);


	Type get(Long typeId);

	Type getByName(Long typeValue, String name);
	
}
