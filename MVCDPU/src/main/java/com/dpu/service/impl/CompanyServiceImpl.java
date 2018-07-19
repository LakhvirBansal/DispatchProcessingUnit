package com.dpu.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.dpu.common.AllList;
import com.dpu.dao.CategoryDao;
import com.dpu.dao.CompanyAdditionalContactsDao;
import com.dpu.dao.CompanyBillingLocationDao;
import com.dpu.dao.CompanyDao;
import com.dpu.dao.DivisionDao;
import com.dpu.dao.SaleDao;
import com.dpu.entity.Company;
import com.dpu.entity.CompanyAdditionalContacts;
import com.dpu.entity.CompanyBillingLocation;
import com.dpu.entity.Status;
import com.dpu.entity.Type;
import com.dpu.model.AdditionalContacts;
import com.dpu.model.BillingLocation;
import com.dpu.model.CategoryModel;
import com.dpu.model.CompanyResponse;
import com.dpu.model.DivisionReq;
import com.dpu.model.Failed;
import com.dpu.model.SaleReq;
import com.dpu.model.Success;
import com.dpu.model.TypeResponse;
import com.dpu.service.CategoryService;
import com.dpu.service.CompanyAdditionalContactsService;
import com.dpu.service.CompanyBillingLocationService;
import com.dpu.service.CompanyService;
import com.dpu.service.DivisionService;
import com.dpu.service.SaleService;
import com.dpu.service.StatusService;
import com.dpu.service.TypeService;

@Component
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyDao companyDao;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private SaleService saleService;

	@Autowired
	private DivisionService divisionService;

	@Autowired
	private CategoryDao categoryDao;

	@Autowired
	private DivisionDao divisionDao;

	@Autowired
	private SaleDao saleDao;

	@Autowired
	private TypeService typeService;

	@Autowired
	private CompanyBillingLocationDao companyBillingLocationDao;

	@Autowired
	private CompanyAdditionalContactsDao companyAdditionalContactsDao;

	@Autowired
	private CompanyBillingLocationService companyBillingLocationService;

	@Autowired
	private CompanyAdditionalContactsService companyAdditionalContactsService;

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private StatusService statusService;

	Logger logger = Logger.getLogger(CompanyServiceImpl.class);

	@Value("${company_added_message}")
	private String company_added_message;

	@Value("${company_unable_to_add_message}")
	private String company_unable_to_add_message;

	@Value("${company_unable_to_add_two_primary_message}")
	private String company_unable_to_add_two_primary_message;

	@Value("${company_deleted_message}")
	private String company_deleted_message;

	@Value("${company_unable_to_delete_message}")
	private String company_unable_to_delete_message;

	@Value("${company_updated_message}")
	private String company_updated_message;

	@Value("${company_unable_to_update_message}")
	private String company_unable_to_update_message;

	@Value("${company_dependent_message}")
	private String company_dependent_message;

	@Override
	public Object addCompanyData(CompanyResponse companyResponse) {

		logger.info("Inside CompanyServiceImpl addCompanyData() starts");
		Session session = null;
		Transaction tx = null;
		boolean sts = true;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Company company = setCompanyValues(companyResponse);
			company = companyDao.insertCompanyData(company, session);

			List<BillingLocation> billingLocations = companyResponse.getBillingLocations();
			if (billingLocations != null && !billingLocations.isEmpty()) {
				for (BillingLocation billingLocation : billingLocations) {
					CompanyBillingLocation comBillingLocation = setBillingData(billingLocation, company);
					companyBillingLocationDao.insertBillingLocation(comBillingLocation, session);
				}
			}

			List<AdditionalContacts> additionalContacts = companyResponse.getAdditionalContacts();
			if (additionalContacts != null && !additionalContacts.isEmpty()) {
				for (AdditionalContacts additionalContact : additionalContacts) {
					CompanyAdditionalContacts comAdditionalContact = setAdditionalContactData(additionalContact,
							company);

					sts = isPrimaryFunctionExist(session, company.getCompanyId(),
							comAdditionalContact.getFunction().getTypeId());
					if (sts) {
						companyAdditionalContactsDao.insertAdditionalContacts(comAdditionalContact, session);
					} else {
						return createFailedObject(company_unable_to_add_two_primary_message);
					}
				}
			}

		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}

			logger.error("Exception inside CompanyServiceImpl addCompanyData() :" + e.getMessage());
			return createFailedObject(company_unable_to_add_message);
		} finally {
			if (sts && tx != null) {
				tx.commit();
			}
			if (session != null) {
				session.close();
			}
		}

		logger.info("Inside CompanyServiceImpl addCompanyData() ends");
		return createSuccessObject(company_added_message);
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

	private CompanyAdditionalContacts setAdditionalContactData(AdditionalContacts additionalContact, Company company) {

		CompanyAdditionalContacts companyAdditionalContact = new CompanyAdditionalContacts();
		companyAdditionalContact.setCellular(additionalContact.getCellular());
		companyAdditionalContact.setCompany(company);
		companyAdditionalContact.setCustomerName(additionalContact.getCustomerName());
		companyAdditionalContact.setEmail(additionalContact.getEmail());
		companyAdditionalContact.setExt(additionalContact.getExt());
		companyAdditionalContact.setFax(additionalContact.getFax());
		companyAdditionalContact.setPhone(additionalContact.getPhone());
		companyAdditionalContact.setPosition(additionalContact.getPosition());
		companyAdditionalContact.setPrefix(additionalContact.getPrefix());
		companyAdditionalContact.setStatus(statusService.get(additionalContact.getStatusId()));
		companyAdditionalContact.setFunction(typeService.get(additionalContact.getFunctionId()));
	//	companyAdditionalContact.setCountry(typeService.get(additionalContact.getCountryId()));

		return companyAdditionalContact;
	}

	private boolean isPrimaryFunctionExist(Session session, Long companyId, Long functionId) {

		Query query = session.createQuery(
				"from CompanyAdditionalContacts where company = " + companyId + " and function = " + functionId);
		/*
		 * Criterion companyAdditionaContactCriteria = Restrictions.and(
		 * Restrictions.eq("company", companyId), Restrictions.eq("function",
		 * 83)); List<CompanyAdditionalContacts> companyAdditionalContacts =
		 * companyAdditionalContactsDao .find(companyAdditionaContactCriteria);
		 */
		if (functionId == 83) {
			@SuppressWarnings("unchecked")
			List<CompanyAdditionalContacts> companyAdditionalContacts = query.list();
			if (companyAdditionalContacts != null && !companyAdditionalContacts.isEmpty()) {
				if (companyAdditionalContacts.size() >= 1)
					return false;

			}
		}
		return true;

	}

	private CompanyBillingLocation setBillingData(BillingLocation billingLocation, Company company) {

		CompanyBillingLocation comBillingLocation = new CompanyBillingLocation();
		comBillingLocation.setAddress(billingLocation.getAddress());
		//comBillingLocation.setArCDN(billingLocation.getArCDN());
		//comBillingLocation.setArUS(billingLocation.getArUS());
		//comBillingLocation.setCellular(billingLocation.getCellular());
		comBillingLocation.setCity(billingLocation.getCity());
		comBillingLocation.setCompany(company);
		comBillingLocation.setContact(billingLocation.getContact());
	//	comBillingLocation.setEmail(billingLocation.getEmail());
		//comBillingLocation.setExt(billingLocation.getExt());
		comBillingLocation.setFax(billingLocation.getFax());
		comBillingLocation.setName(billingLocation.getName());
		comBillingLocation.setPhone(billingLocation.getPhone());
	//	comBillingLocation.setPosition(billingLocation.getPosition());
		//comBillingLocation.setPrefix(billingLocation.getPrefix());
		comBillingLocation.setProvinceState(billingLocation.getProvinceState());
		comBillingLocation.setStatus(statusService.get(billingLocation.getStatusId()));
		//comBillingLocation.setTollfree(billingLocation.getTollfree());
		//comBillingLocation.setUnitNo(billingLocation.getUnitNo());
		comBillingLocation.setZip(billingLocation.getZip());
		return comBillingLocation;
	}

	private Company setCompanyValues(CompanyResponse companyResponse) {

		Company company = new Company();
		company.setName(companyResponse.getName());
		company.setContact(companyResponse.getContact());
		company.setAddress(companyResponse.getAddress());
		company.setPosition(companyResponse.getPosition());
		company.setUnitNo(companyResponse.getUnitNo());
		company.setPhone(companyResponse.getPhone());
		company.setExt(companyResponse.getExt());
		company.setCity(companyResponse.getCity());
		company.setFax(companyResponse.getFax());
		company.setCompanyPrefix(companyResponse.getCompanyPrefix());
		company.setProvinceState(companyResponse.getProvinceState());
		company.setZip(companyResponse.getZip());
		company.setAfterHours(companyResponse.getAfterHours());
		company.setEmail(companyResponse.getEmail());
		company.setTollfree(companyResponse.getTollfree());
		company.setWebsite(companyResponse.getWebsite());
		company.setCellular(companyResponse.getCellular());
		company.setPager(companyResponse.getPager());
		company.setCategory(categoryDao.findById(companyResponse.getCategoryId()));
		company.setDivision(divisionDao.findById(companyResponse.getDivisionId()));
		company.setSale(saleDao.findById(companyResponse.getSaleId()));
		company.setCountry(typeService.get(companyResponse.getCountryId()));
		return company;
	}

	@Override
	public Company update(Company company) {

		return companyDao.update(company);
	}

	@Override
	public Object delete(Long companyId) {

		logger.info("Inside CompanyServiceImpl addCompanyData() starts");
		Session session = null;
		Transaction tx = null;

		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Company company = companyDao.findById(companyId, session);

			if (company != null) {

				List<CompanyBillingLocation> listCompanyBillingLocations = companyBillingLocationService
						.getAll(companyId, session);
				if (listCompanyBillingLocations != null && !listCompanyBillingLocations.isEmpty()) {
					for (CompanyBillingLocation companyBillingLocation : listCompanyBillingLocations) {
						companyBillingLocationDao.deleteBillingLocation(companyBillingLocation, session);
					}
				}

				List<CompanyAdditionalContacts> comAddContacts = companyAdditionalContactsService.getAll(companyId,
						session);
				if (comAddContacts != null && !comAddContacts.isEmpty()) {
					for (CompanyAdditionalContacts companyAdditionalContacts : comAddContacts) {
						companyAdditionalContactsDao.deleteAdditionalContact(companyAdditionalContacts, session);
					}
				}
				companyDao.deleteCompany(company, session);
			} else {
				return createFailedObject(company_unable_to_delete_message);
			}
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			if (e instanceof ConstraintViolationException) {
				return createFailedObject(company_dependent_message);
			}
			return createFailedObject(company_unable_to_delete_message);
		} finally {
			if (tx != null) {
				tx.commit();
			}
			if (session != null) {
				session.close();
			}
		}
		return createSuccessObject(company_deleted_message);
	}

	@Override
	public List<CompanyResponse> getAll() {

		Session session = sessionFactory.openSession();
		List<CompanyResponse> returnResponse = new ArrayList<CompanyResponse>();

		try {
			List<Company> companies = companyDao.findAll();
			
			if (companies != null && !companies.isEmpty()) {
				for (Company company : companies) {
					CompanyResponse response = new CompanyResponse();
					setCompanyData(session, company, response);
					returnResponse.add(response);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return returnResponse;
	}

	@Override
	public CompanyResponse get(Long id) {

		Session session = null;
		CompanyResponse response = new CompanyResponse();
		try {
			session = sessionFactory.openSession();
			Company company = companyDao.findById(id, session);

			if (company != null) {
				setCompanyData(session, company, response);
				// BeanUtils.copyProperties(response, company);
				List<CompanyBillingLocation> listCompanyBillingLocations = companyBillingLocationService.getAll(id,session);

				if (listCompanyBillingLocations != null && !listCompanyBillingLocations.isEmpty()) {
					List<BillingLocation> billingLocations = new ArrayList<BillingLocation>();
					for (CompanyBillingLocation companyBillingLocation : listCompanyBillingLocations) {
						BillingLocation location = new BillingLocation();
						try {
							BeanUtils.copyProperties(location, companyBillingLocation);
							location.setStatusId(companyBillingLocation.getStatus().getId());
						} catch (IllegalAccessException | InvocationTargetException e) {
							e.printStackTrace();
						}
						billingLocations.add(location);
					}
					response.setBillingLocations(billingLocations);
				}

				List<CompanyAdditionalContacts> comAddContacts = companyAdditionalContactsService.getAll(id, session);

				if (comAddContacts != null && !comAddContacts.isEmpty()) {
					List<AdditionalContacts> addContacts = new ArrayList<AdditionalContacts>();
					for (CompanyAdditionalContacts companyAdditionalContacts : comAddContacts) {
						AdditionalContacts addContact = new AdditionalContacts();
						try {
							addContact = setAdditionalContactsValue(companyAdditionalContacts);
							addContact.setStatusName(companyAdditionalContacts.getStatus().getStatus());
							addContact.setFunctionName(companyAdditionalContacts.getFunction().getTypeName());
						} catch (Exception e) {
							e.printStackTrace();
						}

						addContacts.add(addContact);
					}

					response.setAdditionalContacts(addContacts);
				}

				List<Status> statusList = AllList.getStatusList(session);
				response.setStatusList(statusList);

				List<CategoryModel> categoryList = AllList.getCategoryList(session,  "Category", "categoryId", "name");
				response.setCategoryList(categoryList);
  
				List<DivisionReq> divisionList = AllList.getDivisionList(session,"Division", "divisionId", "divisionId");
				response.setDivisionList(divisionList);
				 
				List<SaleReq> saleList = AllList.getSaleList(session,"Sale", "saleId", "name");
				response.setSaleList(saleList);

			/*	List<TypeResponse> countryList = AllList.getTypeResponse(session, 21l);
				response.setCountryList(countryList);*/
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return response;
	}

	private void setCompanyData(Session session, Company companyObj, CompanyResponse response) {

		response.setCompanyId(companyObj.getCompanyId());
		response.setAddress(companyObj.getAddress());
		response.setCity(companyObj.getCity());
		response.setEmail(companyObj.getEmail());
		response.setName(companyObj.getName());
		response.setProvinceState(companyObj.getProvinceState());
		response.setUnitNo(companyObj.getUnitNo());
		response.setWebsite(companyObj.getWebsite());

		if (companyObj.getCategory() != null) {
			response.setCategoryName(companyObj.getCategory().getName());
			response.setCategoryId(companyObj.getCategory().getCategoryId());
		}
		if (companyObj.getDivision() != null) {
			response.setDivisionName(companyObj.getDivision().getDivisionName());
			response.setDivisionId(companyObj.getDivision().getDivisionId());
		}
		if (companyObj.getSale() != null) {
			response.setSaleName(companyObj.getSale().getName());
			response.setSaleId(companyObj.getSale().getSaleId());
		}
		if (companyObj.getCountry() != null) {
			response.setCountryName(companyObj.getCountry().getTypeName());
			response.setCountryId(companyObj.getCountry().getTypeId());

		}

		List<AdditionalContacts> additionalContactsList = new ArrayList<AdditionalContacts>();
		try {
			Query query = session
					.createQuery("from CompanyAdditionalContacts where company = " + companyObj.getCompanyId());

			List<CompanyAdditionalContacts> companyAdditionalContactsList = query.list();

			if (companyAdditionalContactsList != null) {
				for (CompanyAdditionalContacts companyAdditionalContacts : companyAdditionalContactsList) {
					Type type = companyAdditionalContacts.getFunction();

					if (type.getTypeId() == 83) {
						AdditionalContacts additionalContacts = setAdditionalContactsValue(companyAdditionalContacts);
						additionalContactsList.add(additionalContacts);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.setAdditionalContacts(additionalContactsList);

	}

	private AdditionalContacts setAdditionalContactsValue(CompanyAdditionalContacts companyAdditionalContact) {

		AdditionalContacts additionalContact = new AdditionalContacts();
		additionalContact.setAdditionalContactId(companyAdditionalContact.getAdditionalContactId());
		additionalContact.setCellular(companyAdditionalContact.getCellular());
		additionalContact.setCustomerName(companyAdditionalContact.getCustomerName());
		additionalContact.setEmail(companyAdditionalContact.getEmail());
		additionalContact.setExt(companyAdditionalContact.getExt());
		additionalContact.setFax(companyAdditionalContact.getFax());
		Type functionType = companyAdditionalContact.getFunction();
		if (functionType != null)
			additionalContact.setFunctionName(functionType.getTypeName());
		additionalContact.setPhone(companyAdditionalContact.getPhone());
		additionalContact.setPosition(companyAdditionalContact.getPosition());
		additionalContact.setPrefix(companyAdditionalContact.getPrefix());
		additionalContact.setStatusName(companyAdditionalContact.getStatus().getStatus());
	//	Type countryType = companyAdditionalContact.getCountry();
	//	if (countryType != null)
	//		additionalContact.setCountryName(countryType.getTypeName());
		return additionalContact;
	}

	@Override
	public List<CompanyResponse> getCompanyData() {

		List<Object[]> companyData = companyDao.getCompanyData();
		List<CompanyResponse> returnRes = new ArrayList<CompanyResponse>();

		if (companyData != null && !companyData.isEmpty()) {
			for (Object[] row : companyData) {
				CompanyResponse res = new CompanyResponse();
				res.setCompanyId(Long.valueOf(String.valueOf(row[0])));
				res.setName(String.valueOf(row[1]));
				returnRes.add(res);
			}
		}

		return returnRes;
	}

	@Override
	public Object update(Long id, CompanyResponse companyResponse) {

		Company company = companyDao.findById(id);
		Session session = null;
		Transaction tx = null;
		boolean sts = true;

		try {
			if (company != null) {
				session = sessionFactory.openSession();
				tx = session.beginTransaction();

				sts = companyDao.updateData(company, companyResponse, session);
			} else {
				return createFailedObject(company_unable_to_update_message);
			}
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			return createFailedObject(company_unable_to_update_message);
		} finally {
			if (sts && tx != null) {
				tx.commit();
			}
			if (session != null) {
				session.close();
			}
		}
		if (!sts)
			return createFailedObject(company_unable_to_add_two_primary_message);
		return createSuccessObject(company_updated_message);
	}

	@Override
	public CompanyResponse getOpenAdd() {

		Session session = sessionFactory.openSession();
		CompanyResponse companyResponse = new CompanyResponse();

		try{
		List<Object[]> categoryListObj = categoryDao.getSpecificData(session, "Category", "categoryId", "name");
		List<CategoryModel> categoryList = new ArrayList<CategoryModel>();
		Iterator<Object[]> categoryItr = categoryListObj.iterator();
	
		while(categoryItr.hasNext()){
			Object o[] = (Object[])categoryItr.next();
			CategoryModel type = new CategoryModel();
			type.setCategoryId(Long.parseLong(String.valueOf(o[0])));
			type.setName(String.valueOf(o[1]));
			categoryList.add(type);
		}
		companyResponse.setCategoryList(categoryList);

		List<Object[]> divisionListObj =  categoryDao.getSpecificData(session,"Division", "divisionId", "divisionId");
		
		List<DivisionReq> divisionList = new ArrayList<DivisionReq>();
		Iterator<Object[]> divisionIt = divisionListObj.iterator();
	
		while(divisionIt.hasNext())
		{
			Object o[] = (Object[])divisionIt.next();
			DivisionReq type = new DivisionReq();
			type.setDivisionId(Long.parseLong(String.valueOf(o[0])));
			type.setDivisionName(String.valueOf(o[1]));
			divisionList.add(type);
		}
		companyResponse.setDivisionList(divisionList);

		//List<SaleReq> saleList = saleService.getAll();
		List<Object[]> saleListObj =  categoryDao.getSpecificData(session,"Sale", "saleId", "name");
		
		List<SaleReq> saleList = new ArrayList<SaleReq>();
		Iterator<Object[]> saleItr = saleListObj.iterator();
	
		while(saleItr.hasNext())
		{
			Object o[] = (Object[])saleItr.next();
			SaleReq sale = new SaleReq();
			sale.setSaleId(Long.parseLong(String.valueOf(o[0])));
			sale.setName (String.valueOf(o[1]));
			saleList.add(sale);
		}
		companyResponse.setSaleList(saleList);

		/*List<TypeResponse> countryList = AllList.getTypeResponse(session, 21l);
		companyResponse.setCountryList(countryList);*/
		
		}catch(Exception e){
			
		}finally{
			if(session != null){
				session.close();
			}
		}
		return companyResponse;
	}

	@Override
	public CompanyResponse getOpenAddAdditionalContact() {

		Session session = sessionFactory.openSession();
		CompanyResponse companyResponse = new CompanyResponse();
		
		try{

			List<Status> statusList = AllList.getStatusList(session);
			companyResponse.setStatusList(statusList);
			List<TypeResponse> functionList = AllList.getTypeResponse(session, 20l);
			companyResponse.setFunctionList(functionList);
		
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(session != null){
				session.close();
			}
		}
		return companyResponse;
	}

	@Override
	public CompanyResponse getCompanyBillingLocationAndContacts(Long companyId) {

		CompanyResponse companyResponse = new CompanyResponse();
		Session session = null;

		try {
			Company company = companyDao.findById(companyId);
			if (company != null) {
				session = sessionFactory.openSession();
				List<Object[]> billingLocationData = companyDao.getBillingLocations(company.getCompanyId(), session);
				if (billingLocationData != null && !billingLocationData.isEmpty()) {
					List<BillingLocation> billingLocations = new ArrayList<BillingLocation>();
					for (Object[] row : billingLocationData) {
						BillingLocation billingLocation = new BillingLocation();
						billingLocation.setBillingLocationId(Long.parseLong(String.valueOf(row[0])));
						billingLocation.setName(String.valueOf(row[1]));
						billingLocations.add(billingLocation);
					}

					companyResponse.setBillingLocations(billingLocations);
				}

				List<Object[]> additionalContacts = companyDao.getAdditionalContacts(company.getCompanyId(), session);
				if (additionalContacts != null && !additionalContacts.isEmpty()) {
					List<AdditionalContacts> additionalContactList = new ArrayList<AdditionalContacts>();
					for (Object[] row : additionalContacts) {
						AdditionalContacts additionalContact = new AdditionalContacts();
						additionalContact.setAdditionalContactId(Long.parseLong(String.valueOf(row[0])));
						additionalContact.setCustomerName(String.valueOf(row[1]));
						additionalContactList.add(additionalContact);
					}

					companyResponse.setAdditionalContacts(additionalContactList);
				}
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return companyResponse;
	}

	@Override
	public List<CompanyResponse> getCompanyByCompanyName(String companyName) {

		Session session = null;
		List<CompanyResponse> response = new ArrayList<CompanyResponse>();
		try {

			session = sessionFactory.openSession();
			List<Company> companyList = companyDao.getCompaniesByCompanyName(companyName, session);
			if (companyList != null && !companyList.isEmpty()) {
				for (Company company : companyList) {
					CompanyResponse companyResponse = new CompanyResponse();
					/*
					 * org.springframework.beans.BeanUtils.copyProperties(
					 * company, companyResponse);
					 */
					setCompanyData(session, company, companyResponse);
					response.add(companyResponse);
				}
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return response;
	}
}
