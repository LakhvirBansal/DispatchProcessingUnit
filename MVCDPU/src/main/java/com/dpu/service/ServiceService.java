
package com.dpu.service;

import java.util.List;

import com.dpu.model.DPUService;

public interface ServiceService {
	Object add(DPUService dpuService);

	Object delete(Long id);

	List<DPUService> getAll();

	DPUService get(Long id);

	DPUService getOpenAdd();

	Object update(Long id, DPUService dpuService);

	List<DPUService> getServiceByServiceName(String serviceName);
	
	List<DPUService> getServiceData();
}
