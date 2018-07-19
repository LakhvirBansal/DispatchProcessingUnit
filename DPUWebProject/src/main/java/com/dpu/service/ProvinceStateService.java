
package com.dpu.service;

import java.util.List;

import com.dpu.model.ProvinceStateResponse;

public interface ProvinceStateService {
	
	List<ProvinceStateResponse> getProvinces(String provinceName);

}
