package com.dpu.dao;

import java.util.List;

import org.hibernate.Session;

import com.dpu.entity.Company;
import com.dpu.entity.CompanyBillingLocation;
import com.dpu.entity.Vendor;
import com.dpu.entity.VendorBillingLocation;
import com.dpu.entity.VendorContacts;
import com.dpu.model.CompanyResponse;
import com.dpu.model.VendorModel;

public interface VendorDao extends GenericDao<Vendor>{

	List<Object[]> getVendorData();

	void updateData(Vendor vendor , Session session);

	List<Object[]> getVendorBillingLocations(Long vendorId, Session session);

	List<Object[]> getVendorAdditionalContacts(Long vendorId, Session session);

	Vendor findById(Long id, Session session);

	Vendor insertVendorData(Vendor vendor, Session session);

	void deleteVendor(Vendor vendor, Session session);

	List<Vendor> getVendorsByVendorName(String vendorName, Session session);

	void insertBillingLocation(VendorBillingLocation vendorBillingLocation, Session session);

	void insertAdditionalContacts(VendorContacts vendorContacts, Session session);

	void updateDataAdditionalContact(VendorContacts comAdditionalContacts,Session session);

	void updateVendorBillingLocation(VendorBillingLocation vendorBillingLocation, Session session);

	boolean deleteAdditionalContact(Long vendorId, Session session, Long additionalContactId);

	boolean deleteBillingLocation(Long vendorId, Session session, Long billingLocationId);

	List<Vendor> findAll(Session session);
	
}
