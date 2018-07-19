package com.dpu.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.dpu.dao.TaxCodeDao;
import com.dpu.entity.Account;
import com.dpu.entity.TaxCode;
import com.dpu.model.AccountModel;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.model.TaxCodeModel;
import com.dpu.service.AccountService;
import com.dpu.service.StatusService;
import com.dpu.service.TaxCodeService;

@Component
public class TaxCodeServiceImpl implements TaxCodeService {

	Logger logger = Logger.getLogger(TaxCodeServiceImpl.class);

	@Autowired
	TaxCodeDao taxCodeDao;

	@Autowired
	StatusService statusService;
	
	@Autowired
	AccountService accountService;

	@Autowired
	SessionFactory sessionFactory;
	
	@Value("${taxcode_added_message}")
	private String taxcode_added_message;
	
	@Value("${taxcode_unable_to_add_message}")
	private String taxcode_unable_to_add_message;
	
	@Value("${taxcode_deleted_message}")
	private String taxcode_deleted_message;
	
	@Value("${taxcode_unable_to_delete_message}")
	private String taxcode_unable_to_delete_message;
	
	@Value("${taxcode_updated_message}")
	private String taxcode_updated_message;
	
	@Value("${taxcode_unable_to_update_message}")
	private String taxcode_unable_to_update_message;
	
	@Value("${taxcode_dependent_message}")
	private String taxcode_dependent_message;
	
	@Override
	public List<TaxCodeModel> getAll() {
		
		logger.info("TaxCodeServiceImpl getAll() starts ");
		Session session = null;
		List<TaxCodeModel> taxCodeList = new ArrayList<TaxCodeModel>();

		try {
			session = sessionFactory.openSession();
			List<TaxCode> taxCodes = taxCodeDao.findAll(session);

			if (taxCodes != null && !taxCodes.isEmpty()) {
				for (TaxCode taxCode : taxCodes) {
					TaxCodeModel taxCodeModel = new TaxCodeModel();
					BeanUtils.copyProperties(taxCode, taxCodeModel);
					if(taxCode.getAccountForRevenue() != null){
						taxCodeModel.setGlAccountRevenueName(taxCode.getAccountForRevenue().getAccountName());
					}
					if(taxCode.getAccountForSale() != null){
						taxCodeModel.setGlAccountSaleName(taxCode.getAccountForRevenue().getAccountName());
					}
					taxCodeList.add(taxCodeModel);
				}
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
	
		logger.info("TaxCodeServiceImpl getAll() ends ");
		return taxCodeList;
	}

	private Object createSuccessObject(String msg) {
		Success success = new Success();
		success.setMessage(msg);
		success.setResultList(getAll());
		return success;
	}

	private Object createFailedObject(String msg) {
		Failed failed = new Failed();
		failed.setMessage(msg);
		//failed.setResultList(getAll());
		return failed;
	}

	@Override
	public Object addTaxCode(TaxCodeModel taxCodeModel) {

		logger.info("TaxCodeServiceImpl addTaxCode() starts ");
		TaxCode taxCode = null;
		Session session = null;
		Transaction tx = null;
		try {
			
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			taxCode = setTaxCodeValues(taxCodeModel, session);
			session.save(taxCode);

		} catch (Exception e) {
			if(tx != null){
				tx.rollback();
			}
			logger.info("Exception inside TaxCodeServiceImpl addTaxCode() :"+ e.getMessage());
			return createFailedObject(taxcode_unable_to_add_message);

		} finally{
			if(tx != null){
				tx.commit();
			} 
			if(session !=null){
				session.close();
			}
		}
		
		logger.info("TaxCodeServiceImpl addTaxCode() ends ");
		return createSuccessObject(taxcode_added_message);
	}

	private TaxCode setTaxCodeValues(TaxCodeModel taxCodeModel, Session session) {
		
		TaxCode taxCode = new TaxCode();
		BeanUtils.copyProperties(taxCodeModel, taxCode);
		
		if(taxCodeModel.getGlAccountRevenueId() != null){
			taxCode.setAccountForRevenue((Account) session.get(Account.class, taxCodeModel.getGlAccountRevenueId()));
		}
		
		if(taxCodeModel.getGlAccountSaleId() != null){
			taxCode.setAccountForSale((Account) session.get(Account.class, taxCodeModel.getGlAccountSaleId()));
		}
		return taxCode;
	}

	@Override
	public Object update(Long id, TaxCodeModel taxCodeModel) {

		logger.info("TaxCodeServiceImpl update() starts.");
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			TaxCode taxCode = (TaxCode) session.get(TaxCode.class, id);
			
			if (taxCode != null) {
				String[] ignorePro ={"taxCodeId"};
				BeanUtils.copyProperties(taxCodeModel, taxCode, ignorePro);
				if(taxCodeModel.getGlAccountRevenueId() != null){
					taxCode.setAccountForRevenue((Account) session.get(Account.class, taxCodeModel.getGlAccountRevenueId()));
				}
				
				if(taxCodeModel.getGlAccountSaleId() != null){
					taxCode.setAccountForSale((Account) session.get(Account.class, taxCodeModel.getGlAccountSaleId()));
				}
				session.update(taxCode);
				tx.commit();
			} else{
				return createFailedObject(taxcode_unable_to_update_message);
			}

		} catch (Exception e) {
			if(tx != null){
				tx.rollback();
			}
			logger.info("Exception inside TaxCodeServiceImpl update() :"+ e.getMessage());
			return createFailedObject(taxcode_unable_to_update_message);
		} finally{
			if(session != null){
				session.close();
			}
		}
		
		logger.info("TaxCodeServiceImpl update() ends.");
		return createSuccessObject(taxcode_updated_message);
	}

	@Override
	public Object delete(Long id) {
		
		logger.info("TaxCodeServiceImpl delete() starts.");
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			TaxCode taxCode = (TaxCode) session.get(TaxCode.class, id);
			if(taxCode != null){
				session.delete(taxCode);
				tx.commit();
			} else{
				return createFailedObject(taxcode_unable_to_delete_message);
			}
			
		} catch (Exception e) {
			logger.info("Exception inside TaxCodeServiceImpl delete() : " + e.getMessage());
			if(tx != null){
				tx.rollback();
			}
			if(e instanceof ConstraintViolationException){
				return createFailedObject(taxcode_dependent_message);
			}
			return createFailedObject(taxcode_unable_to_delete_message);
		} finally{
			/*if(tx != null){
				tx.commit();
			}*/
			if(session != null){
				session.close();
			}
		}
		
		logger.info("TaxCodeServiceImpl delete() ends.");
		return createSuccessObject(taxcode_deleted_message);
	}



	@Override
	public TaxCodeModel get(Long id) {
		
		logger.info("TaxCodeServiceImpl get() starts.");
		Session session = null;
		TaxCodeModel taxCodeModel = new TaxCodeModel();

		try {

			session = sessionFactory.openSession();
			TaxCode taxCode = taxCodeDao.findById(id, session);

			if (taxCode != null) {
				BeanUtils.copyProperties(taxCode, taxCodeModel);
				if(taxCode.getAccountForRevenue() != null){
					taxCodeModel.setGlAccountRevenueId(taxCode.getAccountForRevenue().getAccountId());
				}
				if(taxCode.getAccountForSale() != null){
					taxCodeModel.setGlAccountSaleId(taxCode.getAccountForSale().getAccountId());
				}
				List<AccountModel> accountList = accountService.getSpecificData();
				taxCodeModel.setGlAccountRevenueList(accountList);
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
		
		logger.info("TaxCodeServiceImpl get() ends.");
		return taxCodeModel;
	}

	@Override
	public TaxCodeModel getOpenAdd() {
		
		logger.info("TaxCodeServiceImpl getOpenAdd() starts ");
		
		TaxCodeModel taxcodeModel = new TaxCodeModel();
		List<AccountModel> accountList = accountService.getSpecificData();
		taxcodeModel.setGlAccountRevenueList(accountList);
		
		logger.info("TaxCodeServiceImpl getOpenAdd() ends ");
		return taxcodeModel;
	}

	@Override
	public List<TaxCodeModel> getTaxCodeByTaxCodeName(String taxCodeName) {
		
		logger.info("TaxCodeServiceImpl getTaxCodeByTaxCodeName() starts, taxCodeName :"+taxCodeName);
		Session session = null;
		List<TaxCodeModel> taxCodeList = new ArrayList<TaxCodeModel>();

		try {
			session = sessionFactory.openSession();
			List<TaxCode> taxCodes = taxCodeDao.getTaxCodesByTaxCodeNames(session, taxCodeName);
			if (taxCodes != null && !taxCodes.isEmpty()) {
				for (TaxCode taxCode : taxCodes) {
					TaxCodeModel taxCodeModel = new TaxCodeModel();
					BeanUtils.copyProperties(taxCode, taxCodeModel);
					if(taxCode.getAccountForRevenue() != null){
						taxCodeModel.setGlAccountRevenueName(taxCode.getAccountForRevenue().getAccountName());
					}
					if(taxCode.getAccountForSale() != null){
						taxCodeModel.setGlAccountSaleName(taxCode.getAccountForRevenue().getAccountName());
					}
					taxCodeList.add(taxCodeModel);
				}
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
		
		logger.info("TaxCodeServiceImpl getTaxCodeByTaxCodeName() ends, taxCodeName :"+taxCodeName);
		return taxCodeList;
	}

	@Override
	public List<TaxCodeModel> getSpecificData() {
		
		Session session = sessionFactory.openSession();
		List<TaxCodeModel> handlings = new ArrayList<TaxCodeModel>();
		try{
		List<Object[]> handlingData = taxCodeDao.getSpecificData(session,"TaxCode","taxCodeId", "taxCode");

		
		if (handlingData != null && !handlingData.isEmpty()) {
			for (Object[] row : handlingData) {
				TaxCodeModel taxCodeModel = new TaxCodeModel();
				taxCodeModel.setTaxCodeId((Long) row[0]);
				taxCodeModel.setTaxCode(String.valueOf(row[1]));
				handlings.add(taxCodeModel);
			}
		}
		}catch(Exception e){
			
		}finally{
			if(session != null){
				session.close();
			}
		}
		return handlings;
	}

	@Override
	public TaxCode getById(Long id, Session session) {
		return (TaxCode) session.get(TaxCode.class, id);
	}

}
