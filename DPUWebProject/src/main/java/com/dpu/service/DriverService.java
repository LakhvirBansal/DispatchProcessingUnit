package com.dpu.service;

import java.util.List;

import com.dpu.entity.Driver;
import com.dpu.model.DriverModel;

/**
 * @author sumit
 *
 */

public interface DriverService {

	Object updateDriver(Long driverId, DriverModel driverReq);
	
	List<DriverModel> getAllDriver();
	
	Object deleteDriver(Long driverId);

	Object getDriverByDriverId(Long driverId);

	DriverModel getOpenAdd();

	Object addDriver(DriverModel driverReq);

	List<DriverModel> getDriverByDriverCodeOrName(String driverCodeOrName);
	
	List<DriverModel> getSpecificData();
	
}

