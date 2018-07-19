package com.dpu.service;

import java.util.List;

import com.dpu.model.VehicleMaintainanceCategoryModel;

public interface VehicleMaintainanceCategoryService {

	Object addVMC(VehicleMaintainanceCategoryModel vehicleMaintainanceCategoryModel);

	List<VehicleMaintainanceCategoryModel> getAll();

	VehicleMaintainanceCategoryModel get(Long vmcId);

	Object update(Long vmcId, VehicleMaintainanceCategoryModel vehicleMaintainanceCategoryModel);

	List<VehicleMaintainanceCategoryModel> getVmcByVmcName(String vmcName);

	Object delete(Long vmcId);

	List<VehicleMaintainanceCategoryModel> getSpecificData();

}
