package com.dpu.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.dpu.dao.CategoryDao;
import com.dpu.dao.CompanyAdditionalContactsDao;
import com.dpu.dao.CompanyDao;
import com.dpu.dao.DivisionDao;
import com.dpu.dao.SaleDao;
import com.dpu.entity.Company;
import com.dpu.entity.CompanyAdditionalContacts;
import com.dpu.entity.CompanyBillingLocation;
import com.dpu.model.AdditionalContacts;
import com.dpu.model.BillingLocation;
import com.dpu.model.CompanyResponse;
import com.dpu.service.StatusService;
import com.dpu.service.TypeService;

@Repository
public class CompanyDaoImpl extends GenericDaoImpl<Company> implements
		CompanyDao {

	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	private CategoryDao categoryDao;

	@Autowired
	private DivisionDao divisionDao;

	@Autowired
	private SaleDao saleDao;

	@Autowired
	private StatusService statusService;

	@Autowired
	private TypeService typeService;
	
	@Autowired
	private CompanyAdditionalContactsDao companyAdditionalContactsDao;
	
	@Value("${company_unable_to_add_primary_again_message}")
	private String company_unable_to_add_primary_again_message;

	Logger logger = Logger.getLogger(CompanyDaoImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getCompanyData() {

		logger.info("CompanyDaoImpl getCompanyData() Ends");
		Session session = null;
		List<Object[]> returnList = null;
		try {
			session = sessionFactory.openSession();

			Query query = session
					.createSQLQuery(" select company_id, name from companymaster ");
			returnList = query.list();

		} catch (Exception e) {
			logger.error("Exception inside CompanyDaoImpl getCompanyData :"
					+ e.getCause());
		} finally {
			if (session != null) {
				session.close();
			}
		}

		logger.info("CompanyDaoImpl getCompanyData() Ends");
		return returnList;
	}

	@Override
	public boolean updateData(Company company, CompanyResponse companyResponse,
			Session session) {

		String[] ignoreProp = new String[1];
		ignoreProp[0] = "companyId";
		boolean sts = true;
		BeanUtils.copyProperties(companyResponse, company, ignoreProp);

		company.setCategory(categoryDao.findById(companyResponse
				.getCategoryId()));
		company.setDivision(divisionDao.findById(companyResponse
				.getDivisionId()));
		company.setSale(saleDao.findById(companyResponse.getSaleId()));
		company.setCountry(typeService.get(companyResponse
				.getCountryId()));
		session.saveOrUpdate(company);

		List<BillingLocation> billingocationList = companyResponse
				.getBillingLocations();

		if (billingocationList != null && !billingocationList.isEmpty()) {
			for (BillingLocation billingLocation : billingocationList) {
				CompanyBillingLocation companyBillingLocation = new CompanyBillingLocation();
				BeanUtils.copyProperties(billingLocation,
						companyBillingLocation);
				companyBillingLocation.setCompany(company);
				companyBillingLocation.setStatus(statusService
						.get(billingLocation.getStatusId()));
				session.saveOrUpdate(companyBillingLocation);
			}
		}

		List<AdditionalContacts> additionalContactsList = companyResponse.getAdditionalContacts();

		if (additionalContactsList != null && !additionalContactsList.isEmpty()) {
			for (AdditionalContacts additionalContacts : additionalContactsList) {
				CompanyAdditionalContacts comAdditionalContacts = new CompanyAdditionalContacts();
				BeanUtils.copyProperties(additionalContacts,
						comAdditionalContacts);
				comAdditionalContacts.setCompany(company);
				// Long companyId = company.getCompanyId();
				comAdditionalContacts.setStatus(statusService
						.get(additionalContacts.getStatusId()));
				if (additionalContacts.getFunctionId() != null)
					comAdditionalContacts.setFunction(typeService
							.get(additionalContacts.getFunctionId()));
				//sts = isPrimaryFunctionExist(session,company.getCompanyId(),additionalContacts.getFunctionId());
				if (sts)
					session.saveOrUpdate(comAdditionalContacts);
				sts = isPrimaryFunctionExist(session,company.getCompanyId(),additionalContacts.getFunctionId());
			}
		}
		return sts;
	}

	private boolean isPrimaryFunctionExist(Session session,Long companyId ,Long functionId) {

		
		Query query = session.createQuery("from CompanyAdditionalContacts where company = "+companyId+" and function ="+functionId);
		/*Criterion companyAdditionaContactCriteria = Restrictions.and(
				Restrictions.eq("company", companyId),
				Restrictions.eq("function", 83));
		List<CompanyAdditionalContacts> companyAdditionalContacts = companyAdditionalContactsDao
				.find(companyAdditionaContactCriteria);*/
		if(functionId == 83){
			@SuppressWarnings("unchecked")
			List<CompanyAdditionalContacts> companyAdditionalContacts = query.list();
			if (companyAdditionalContacts != null
					&& !companyAdditionalContacts.isEmpty()) {
				if (companyAdditionalContacts.size() > 1)
					return false;

			}
		}
		return true;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getBillingLocations(Long companyId, Session session) {

		List<Object[]> returnList = null;
		Query query = session
				.createSQLQuery(" select billing_location_id,name from billinglocationmaster where company_id =:companyId ");
		query.setParameter("companyId", companyId);
		returnList = query.list();
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getAdditionalContacts(Long companyId, Session session) {

		List<Object[]> returnList = null;
		Query query = session
				.createSQLQuery(" select add_contact_id,customer_name from additionalcontactmaster where company_id =:companyId ");
		query.setParameter("companyId", companyId);
		returnList = query.list();
		return returnList;
	}

	@Override
	public Company findById(Long companyId, Session session) {

		StringBuilder sb = new StringBuilder(
				" select c from Company c where c.companyId =:companyId ");
		Query query = session.createQuery(sb.toString());
		query.setParameter("companyId", companyId);
		return (Company) query.uniqueResult();
	}

	@Override
	public Company insertCompanyData(Company company, Session session) {

		session.save(company);
		return company;
	}

	@Override
	public void deleteCompany(Company company, Session session) {

		session.delete(company);
	}

	@Override
	public List<Company> getCompaniesByCompanyName(String companyName,
			Session session) {

		StringBuilder builder = new StringBuilder();
		builder.append("select c from Company c where c.name like :companyName ");
		Query query = session.createQuery(builder.toString());
		query.setParameter("companyName", "%" + companyName + "%");
		return query.list();
	}

}
