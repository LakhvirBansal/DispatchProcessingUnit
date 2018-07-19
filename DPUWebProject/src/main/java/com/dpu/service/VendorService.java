package com.dpu.service;

import java.util.List;

import com.dpu.entity.Company;
import com.dpu.entity.Vendor;
import com.dpu.model.CompanyResponse;
import com.dpu.model.VendorModel;

public interface VendorService {

	Object addVendorData(VendorModel vendorModel);
	
	Vendor update(Vendor vendor);
	
	Object delete(Long vendorId);
	
	List<VendorModel> getAll();
	
	List<VendorModel> getCompanyData();

	VendorModel get(Long id);

	//Object update(Long id, CompanyResponse companyResponse);

	VendorModel getOpenAdd();
	
	CompanyResponse getCompanyBillingLocationAndContacts(Long companyId);

	List<VendorModel> getVendorByVendorName(String vendorName);

	VendorModel getVendorContacts(Long id);

	Object update(Long id, VendorModel vendorModel);

	Object deleteAdditionalContact(Long vendorId, Long additionalContactId);

	Object deleteBillingLocation(Long vendorId, Long billingLocationId);

	List<VendorModel> getSpecificData();
	
}
