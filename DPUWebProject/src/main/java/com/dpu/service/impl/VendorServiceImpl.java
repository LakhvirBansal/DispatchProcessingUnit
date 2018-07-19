package com.dpu.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.dpu.common.AllList;
import com.dpu.dao.CompanyAdditionalContactsDao;
import com.dpu.dao.CompanyBillingLocationDao;
import com.dpu.dao.VendorDao;
import com.dpu.entity.Country;
import com.dpu.entity.State;
import com.dpu.entity.Status;
import com.dpu.entity.Vendor;
import com.dpu.entity.VendorBillingLocation;
import com.dpu.entity.VendorContacts;
import com.dpu.model.CompanyResponse;
import com.dpu.model.CountryStateCityModel;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.model.VendorAdditionalContactsModel;
import com.dpu.model.VendorBillingLocationModel;
import com.dpu.model.VendorModel;
import com.dpu.service.CompanyAdditionalContactsService;
import com.dpu.service.CompanyBillingLocationService;
import com.dpu.service.CountryStateCityService;
import com.dpu.service.StatusService;
import com.dpu.service.VendorService;
import com.dpu.util.ValidationUtil;

@Component
public class VendorServiceImpl implements VendorService {

	@Autowired
	VendorDao vendorDao;

	@Autowired
	CompanyBillingLocationDao companyBillingLocationDao;

	@Autowired
	CompanyAdditionalContactsDao companyAdditionalContactsDao;

	@Autowired
	CompanyBillingLocationService companyBillingLocationService;

	@Autowired
	CompanyAdditionalContactsService companyAdditionalContactsService;

	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	StatusService statusService;
	
	@Autowired
	CountryStateCityService countryStateCityService;

	Logger logger = Logger.getLogger(VendorServiceImpl.class);

	@Value("${vendor_added_message}")
	private String vendor_added_message;

	@Value("${vendor_unable_to_add_message}")
	private String vendor_unable_to_add_message;

	@Value("${vendor_deleted_message}")
	private String vendor_deleted_message;

	@Value("${vendor_unable_to_delete_message}")
	private String vendor_unable_to_delete_message;

	@Value("${vendor_updated_message}")
	private String vendor_updated_message;

	@Value("${vendor_unable_to_update_message}")
	private String vendor_unable_to_update_message;

	@Value("${vendor_dependent_message}")
	private String vendor_dependent_message;

	@Value("${vendor_additional_contact_delete_message}")
	private String vendor_additional_contact_delete_message;

	@Value("${vendor_unable_additional_contact_delete_message}")
	private String vendor_unable_additional_contact_delete_message;

	@Value("${vendor_additional_contact_dependent_message}")
	private String vendor_additional_contact_dependent_message;

	@Value("${vendor_billing_location_delete_message}")
	private String vendor_billing_location_delete_message;

	@Value("${vendor_unable_billing_location_delete_message}")
	private String vendor_unable_billing_location_delete_message;

	@Value("${vendor_billing_location_dependent_message}")
	private String vendor_billing_location_dependent_message;

	@Override
	public Object addVendorData(VendorModel vendorModel) {

		logger.info("Inside VendorServiceImpl addVendorData() starts");
		Session session = null;
		Transaction tx = null;

		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Vendor vendor = setVendorValues(null, vendorModel, session);
			vendor = vendorDao.insertVendorData(vendor, session);

			List<VendorBillingLocationModel> billingLocations = vendorModel.getBillingLocations();
			if (billingLocations != null && !billingLocations.isEmpty()) {
				for (VendorBillingLocationModel billingLocation : billingLocations) {
					VendorBillingLocation comBillingLocation = setBillingData(billingLocation, vendor, null);
					vendorDao.insertBillingLocation(comBillingLocation, session);
				}
			}

			List<VendorAdditionalContactsModel> additionalContacts = vendorModel.getAdditionalContacts();
			if (additionalContacts != null && !additionalContacts.isEmpty()) {
				for (VendorAdditionalContactsModel additionalContact : additionalContacts) {
					VendorContacts comAdditionalContact = setAdditionalContactData(additionalContact, vendor, null);
					vendorDao.insertAdditionalContacts(comAdditionalContact, session);
				}
			}

		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}

			logger.error("Exception inside VendorServiceImpl addVendorData() :" + e.getMessage());
			return createFailedObject(vendor_unable_to_add_message);
		} finally {
			if (tx != null) {
				tx.commit();
			}
			if (session != null) {
				session.close();
			}
		}

		logger.info("Inside VendorServiceImpl addVendorData() ends");
		return createSuccessObject(vendor_added_message);
	}

	@Override
	public Object update(Long id, VendorModel vendorModel) {

		Session session = null;
		Transaction tx = null;

		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Vendor vendor = (Vendor) session.get(Vendor.class, id);
			if (vendor != null) {

				setVendorValues(vendor, vendorModel, session);
				vendorDao.updateData(vendor, session);

				if(vendorModel.getBillingLocations() != null && vendorModel.getBillingLocations().size() > 0) {
					List<VendorBillingLocationModel> vendorBillingLocations = vendorModel.getBillingLocations();
					if (vendorBillingLocations != null && !vendorBillingLocations.isEmpty()) {
						for (VendorBillingLocationModel vendorBillingLocationModel : vendorBillingLocations) {
							VendorBillingLocation billingLocation = null;
							if (vendorBillingLocationModel.getVendorBillingLocationId() != null) {
								billingLocation = (VendorBillingLocation) session.get(VendorBillingLocation.class,
										vendorBillingLocationModel.getVendorBillingLocationId());
							}
							
							VendorBillingLocation vendorBillingLocation = setBillingData(vendorBillingLocationModel, vendor,
									billingLocation);
							vendorDao.updateVendorBillingLocation(vendorBillingLocation, session);
						}
					}
				}

				if(vendorModel.getAdditionalContacts() != null && vendorModel.getAdditionalContacts().size() > 0) {
					List<VendorAdditionalContactsModel> additionalContactsList = vendorModel.getAdditionalContacts();
	
					if (additionalContactsList != null && !additionalContactsList.isEmpty()) {
						for (VendorAdditionalContactsModel additionalContacts : additionalContactsList) {
							VendorContacts vendorContacts = null;
							if (additionalContacts.getVendorAdditionalContactId() != null) {
								vendorContacts = (VendorContacts) session.get(VendorContacts.class,
										additionalContacts.getVendorAdditionalContactId());
							}
							VendorContacts comAdditionalContacts = setAdditionalContactData(additionalContacts, vendor,
									vendorContacts);
							vendorDao.updateDataAdditionalContact(comAdditionalContacts, session);
						}
					}
				}
			} else {
				return createFailedObject(vendor_unable_to_update_message);
			}
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			return createFailedObject(vendor_unable_to_update_message);
		} finally {
			if (tx != null) {
				tx.commit();
			}
			if (session != null) {
				session.close();
			}
		}

		return createSuccessObject(vendor_updated_message);
	}

	private Object createFailedObject(String errorMessage) {
		Failed failed = new Failed();
		failed.setMessage(errorMessage);
		return failed;
	}

	private Object createSuccessObject(String message) {
		Success success = new Success();
		success.setMessage(message);
		success.setResultList(getAll());
		return success;
	}

	private VendorContacts setAdditionalContactData(VendorAdditionalContactsModel additionalContact, Vendor vendor,
			VendorContacts vendorContact) {

		if (vendorContact == null) {
			vendorContact = new VendorContacts();
		}
		vendorContact.setCellular(additionalContact.getCellular());
		vendorContact.setVendorObj(vendor);
		vendorContact.setVendorName(additionalContact.getVendorName());
		vendorContact.setEmail(additionalContact.getEmail());
		vendorContact.setExt(additionalContact.getExt());
		vendorContact.setFax(additionalContact.getFax());
		vendorContact.setPhone(additionalContact.getPhone());
		vendorContact.setPosition(additionalContact.getPosition());
		vendorContact.setPrefix(additionalContact.getPrefix());
		vendorContact.setStatus(statusService.get(additionalContact.getStatusId()));
		return vendorContact;
	}

	private VendorBillingLocation setBillingData(VendorBillingLocationModel billingLocation, Vendor vendor,
			VendorBillingLocation vendorBillingLocation) {

		if (vendorBillingLocation == null) {
			vendorBillingLocation = new VendorBillingLocation();
		}
		vendorBillingLocation.setAddress(billingLocation.getAddress());
		vendorBillingLocation.setArCDN(billingLocation.getArCDN());
		vendorBillingLocation.setArUS(billingLocation.getArUS());
		vendorBillingLocation.setCellular(billingLocation.getCellular());
		vendorBillingLocation.setCity(billingLocation.getCity());
		vendorBillingLocation.setVendorObj(vendor);
		vendorBillingLocation.setContact(billingLocation.getContact());
		vendorBillingLocation.setEmail(billingLocation.getEmail());
		vendorBillingLocation.setExt(billingLocation.getExt());
		vendorBillingLocation.setFax(billingLocation.getFax());
		vendorBillingLocation.setName(billingLocation.getName());
		vendorBillingLocation.setPhone(billingLocation.getPhone());
		vendorBillingLocation.setPosition(billingLocation.getPosition());
		vendorBillingLocation.setPrefix(billingLocation.getPrefix());
		vendorBillingLocation.setProvinceState(billingLocation.getProvinceState());

		if (!ValidationUtil.isNull(billingLocation.getStatusId())) {
			vendorBillingLocation.setStatus(statusService.get(billingLocation.getStatusId()));
		}
		vendorBillingLocation.setTollfree(billingLocation.getTollfree());
		vendorBillingLocation.setUnitNo(billingLocation.getUnitNo());
		vendorBillingLocation.setZip(billingLocation.getZip());
		return vendorBillingLocation;
	}

	private Vendor setVendorValues(Vendor vendor, VendorModel vendorModel, Session session) {
		if (vendor == null) {
			vendor = new Vendor();
		}
		vendor.setName(vendorModel.getName());
		vendor.setContact(vendorModel.getContact());
		vendor.setAddress(vendorModel.getAddress());
		vendor.setPosition(vendorModel.getPosition());
		vendor.setUnitNo(vendorModel.getUnitNo());
		vendor.setPhone(vendorModel.getPhone());
		vendor.setExt(vendorModel.getExt());
		vendor.setCity(vendorModel.getCity());
		vendor.setFax(vendorModel.getFax());
		vendor.setVendorPrefix(vendorModel.getVendorPrefix());
		vendor.setZip(vendorModel.getZip());
		vendor.setAfterHours(vendorModel.getAfterHours());
		vendor.setEmail(vendorModel.getEmail());
		vendor.setTollfree(vendorModel.getTollfree());
		vendor.setWebsite(vendorModel.getWebsite());
		vendor.setCellular(vendorModel.getCellular());
		vendor.setPager(vendorModel.getPager());
		
		if (!ValidationUtil.isNull(vendorModel.getCountryId())) {
			Country country = (Country) session.get(Country.class, vendorModel.getCountryId());
			vendor.setCountry(country);
		}
		
		if (!ValidationUtil.isNull(vendorModel.getStateId())) {
			State state = (State) session.get(State.class, vendorModel.getStateId());
			vendor.setState(state);
		}
		return vendor;
	}

	@Override
	public Vendor update(Vendor vendor) {
		return vendorDao.update(vendor);
	}

	@Override
	public Object delete(Long vendorId) {

		logger.info("Inside CompanyServiceImpl addCompanyData() starts");
		Session session = null;
		Transaction tx = null;

		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Vendor vendor = vendorDao.findById(vendorId, session);

			if (vendor != null) {

				List<VendorBillingLocation> listVendorBillingLocations = vendor.getBillingLocations();
				if (listVendorBillingLocations != null && !listVendorBillingLocations.isEmpty()) {
					for (VendorBillingLocation vendorBillingLocation : listVendorBillingLocations) {
						session.delete(vendorBillingLocation);
					}
				}

				List<VendorContacts> vendorContacts = vendor.getAdditionalContacts();
				if (vendorContacts != null && !vendorContacts.isEmpty()) {
					for (VendorContacts vendorContactObj : vendorContacts) {
						session.delete(vendorContactObj);
					}
				}
				vendorDao.deleteVendor(vendor, session);
				if (tx != null) {
					tx.commit();
				}
			} else {
				return createFailedObject(vendor_unable_to_delete_message);
			}
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			if (e instanceof ConstraintViolationException) {
				return createFailedObject(vendor_dependent_message);
			}
			return createFailedObject(vendor_unable_to_delete_message);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return createSuccessObject(vendor_deleted_message);
	}

	@Override
	public List<VendorModel> getAll() {

		List<VendorModel> returnResponse = new ArrayList<VendorModel>();
		Session session = null;
		
		try{
			session = sessionFactory.openSession();
			List<Vendor> vendors = vendorDao.findAll(session);
			if (vendors != null && !vendors.isEmpty()) {
				for (Vendor vendor : vendors) {
					VendorModel response = new VendorModel();
					setVendorData(vendor, response);

					returnResponse.add(response);
				}

			}
		} finally {
			if( session != null ){
				session.close();
			}
		}
		
		return returnResponse;
	}

	@Override
	public VendorModel get(Long id) {

		Session session = null;
		VendorModel response = new VendorModel();
		
		try {
			session = sessionFactory.openSession();
			Vendor vendor = vendorDao.findById(id, session);

			if (vendor != null) {
				setVendorData(vendor, response);
				List<VendorBillingLocation> listCompanyBillingLocations = vendor.getBillingLocations();

				if (listCompanyBillingLocations != null && !listCompanyBillingLocations.isEmpty()) {
					List<VendorBillingLocationModel> billingLocations = new ArrayList<VendorBillingLocationModel>();
					for (VendorBillingLocation vendorBillingLocation : listCompanyBillingLocations) {
						VendorBillingLocationModel location = new VendorBillingLocationModel();
						org.springframework.beans.BeanUtils.copyProperties(vendorBillingLocation, location);
						
						if (!ValidationUtil.isNull(vendorBillingLocation.getStatus())) {
							location.setStatusId(vendorBillingLocation.getStatus().getId());
						}
						billingLocations.add(location);
					}
					response.setBillingLocations(billingLocations);
				}

				List<Status> statusList = AllList.getStatusList(session);
				response.setStatusList(statusList);
				
				List<CountryStateCityModel> countryList = countryStateCityService.getAllCountries();
				response.setCountryList(countryList);
				
				if(vendor.getCountry() != null){
					List<CountryStateCityModel> stateList = countryStateCityService.getStatesByCountryId(vendor.getCountry().getCountryId());
					response.setStateList(stateList);
				}
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return response;
	}

	private void setVendorData(Vendor vendor, VendorModel response) {

		response.setVendorId(vendor.getVendorId());
		response.setAddress(vendor.getAddress());
		response.setAfterHours(vendor.getAfterHours());
		response.setCellular(vendor.getCellular());
		response.setCity(vendor.getCity());
		response.setVendorPrefix(vendor.getVendorPrefix());
		response.setContact(vendor.getContact());
		response.setVendorNotes(vendor.getVendorNotes());
		response.setEmail(vendor.getEmail());
		response.setExt(vendor.getExt());
		response.setFax(vendor.getFax());
		response.setName(vendor.getName());
		response.setPager(vendor.getPager());
		response.setPhone(vendor.getPhone());
		response.setPosition(vendor.getPosition());
		response.setTollfree(vendor.getTollfree());
		response.setZip(vendor.getZip());
		response.setUnitNo(vendor.getUnitNo());
		response.setWebsite(vendor.getWebsite());
		
		if (!ValidationUtil.isNull(vendor.getCountry())) {
			response.setCountryName(vendor.getCountry().getCountryName());
			response.setCountryId(vendor.getCountry().getCountryId());
		}
		
		if (!ValidationUtil.isNull(vendor.getState())) {
			response.setStateName(vendor.getState().getStateName());
			response.setStateId(vendor.getState().getStateId());
		}
	}

	@Override
	public List<VendorModel> getCompanyData() {

		List<Object[]> vendorData = vendorDao.getVendorData();
		List<VendorModel> returnRes = new ArrayList<VendorModel>();

		if (vendorData != null && !vendorData.isEmpty()) {
			for (Object[] row : vendorData) {
				VendorModel res = new VendorModel();
				res.setVendorId(Long.valueOf(String.valueOf(row[0])));
				res.setName(String.valueOf(row[1]));
				returnRes.add(res);
			}
		}

		return returnRes;
	}

	@Override
	public VendorModel getOpenAdd() {

		Session session = sessionFactory.openSession();
		VendorModel vendorModel = new VendorModel();

		try {
			List<Status> statusList = AllList.getStatusList(session);
			vendorModel.setStatusList(statusList);
			
			List<CountryStateCityModel> countryList = countryStateCityService.getAllCountries();
			vendorModel.setCountryList(countryList);
			
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return vendorModel;
	}

	@Override
	public CompanyResponse getCompanyBillingLocationAndContacts(Long companyId) {
		CompanyResponse companyResponse = new CompanyResponse();
		Session session = null;

		/*
		 * try{ Company company = companyDao.findById(companyId); if(company !=
		 * null){ session = sessionFactory.openSession(); List<Object[]>
		 * billingLocationData =
		 * companyDao.getBillingLocations(company.getCompanyId(), session);
		 * if(billingLocationData != null && !billingLocationData.isEmpty()){
		 * List<BillingLocation> billingLocations = new
		 * ArrayList<BillingLocation>(); for (Object[] row :
		 * billingLocationData) { BillingLocation billingLocation = new
		 * BillingLocation();
		 * billingLocation.setBillingLocationId(Long.parseLong(String.valueOf(
		 * row[0]))); billingLocation.setName(String.valueOf(row[1]));
		 * billingLocations.add(billingLocation); }
		 * 
		 * companyResponse.setBillingLocations(billingLocations); }
		 * 
		 * List<Object[]> additionalContacts =
		 * companyDao.getAdditionalContacts(company.getCompanyId(), session);
		 * if(additionalContacts != null && !additionalContacts.isEmpty()){
		 * List<AdditionalContacts> additionalContactList = new
		 * ArrayList<AdditionalContacts>(); for (Object[] row :
		 * additionalContacts) { AdditionalContacts additionalContact = new
		 * AdditionalContacts();
		 * additionalContact.setAdditionalContactId(Long.parseLong(String.
		 * valueOf(row[0])));
		 * additionalContact.setCustomerName(String.valueOf(row[1]));
		 * additionalContactList.add(additionalContact); }
		 * 
		 * companyResponse.setAdditionalContacts(additionalContactList); } } }
		 * finally{ if(session != null){ session.close(); } }
		 */

		return companyResponse;
	}

	@Override
	public List<VendorModel> getVendorByVendorName(String vendorName) {
		Session session = null;
		List<VendorModel> response = new ArrayList<VendorModel>();
		try {

			session = sessionFactory.openSession();
			List<Vendor> vendorList = vendorDao.getVendorsByVendorName(vendorName, session);
			if (vendorList != null && !vendorList.isEmpty()) {
				for (Vendor vendor : vendorList) {
					VendorModel obj = new VendorModel();
					setVendorData(vendor, obj);
					response.add(obj);
				}
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return response;
	}

	@Override
	public VendorModel getVendorContacts(Long vendorId) {

		Session session = null;
		VendorModel response = new VendorModel();
		try {
			session = sessionFactory.openSession();
			Vendor vendor = (Vendor) session.get(Vendor.class, vendorId);
			List<VendorContacts> contacts = vendor.getAdditionalContacts();
			if (contacts != null && !contacts.isEmpty()) {
				List<VendorAdditionalContactsModel> vendorAdditionalContacts = new ArrayList<VendorAdditionalContactsModel>();
				for (VendorContacts vendorContacts : contacts) {
					VendorAdditionalContactsModel addContact = new VendorAdditionalContactsModel();
					org.springframework.beans.BeanUtils.copyProperties(vendorContacts, addContact);
					addContact.setStatusId(vendorContacts.getStatus().getId());

					vendorAdditionalContacts.add(addContact);
				}
				response.setAdditionalContacts(vendorAdditionalContacts);

				/*
				 * for (Vendor vendor : vendorList) { VendorModel obj = new
				 * VendorModel(); setVendorData(vendor,obj); response.add(obj);
				 * }
				 */
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return response;
	}

	@Override
	public Object deleteAdditionalContact(Long vendorId, Long additionalContactId) {

		Session session = null;
		Transaction tx = null;

		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();

			boolean retVal = vendorDao.deleteAdditionalContact(vendorId, session, additionalContactId);
			if (!retVal) {
				return createFailedObject(vendor_unable_additional_contact_delete_message);
			}
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			if (e instanceof ConstraintViolationException) {
				return createFailedObject(vendor_additional_contact_dependent_message);
			}
			return createFailedObject(vendor_unable_additional_contact_delete_message);
		} finally {
			if (tx != null) {
				tx.commit();
			}
			if (session != null) {
				session.close();
			}
		}

		return createSuccessObjectWithOutList(vendor_additional_contact_delete_message);
	}

	private Object createSuccessObjectWithOutList(String message) {
		Success success = new Success();
		success.setMessage(message);
		return success;
	}

	@Override
	public Object deleteBillingLocation(Long vendorId, Long billingLocationId) {
		Session session = null;
		Transaction tx = null;

		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();

			boolean retVal = vendorDao.deleteBillingLocation(vendorId, session, billingLocationId);
			if (!retVal) {
				return createFailedObject(vendor_unable_billing_location_delete_message);
			}
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			if (e instanceof ConstraintViolationException) {
				return createFailedObject(vendor_billing_location_dependent_message);
			}
			return createFailedObject(vendor_unable_billing_location_delete_message);
		} finally {
			if (tx != null) {
				tx.commit();
			}
			if (session != null) {
				session.close();
			}
		}

		return createSuccessObjectWithOutList(vendor_billing_location_delete_message);
	}

	@Override
	public List<VendorModel> getSpecificData() {

		Session session = sessionFactory.openSession();
		List<VendorModel> result = new ArrayList<VendorModel>();
		
		try {
			List<Object[]> vendorData = vendorDao.getSpecificData(session, "Vendor", "vendorId", "name");

			if (vendorData != null && !vendorData.isEmpty()) {
				for (Object[] row : vendorData) {
					VendorModel vendorModel = new VendorModel();
					vendorModel.setVendorId((Long) row[0]);
					vendorModel.setName(String.valueOf(row[1]));
					result.add(vendorModel);
				}
			}
		}  finally {
			if (session != null) {
				session.close();
			}
		}
		return result;
	}
}
