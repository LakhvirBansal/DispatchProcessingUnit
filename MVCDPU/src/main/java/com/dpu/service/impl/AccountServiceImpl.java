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

import com.dpu.dao.AccountDao;
import com.dpu.entity.Account;
import com.dpu.model.AccountModel;
import com.dpu.model.Failed;
import com.dpu.model.Success;
import com.dpu.service.AccountService;
import com.dpu.service.StatusService;
import com.dpu.service.TaxCodeService;
import com.dpu.service.TypeService;

@Component
public class AccountServiceImpl implements AccountService {

	Logger logger = Logger.getLogger(AccountServiceImpl.class);

	@Autowired
	AccountDao accountDao;

	@Autowired
	StatusService statusService;
	
	@Autowired
	TypeService typeService;
	
	@Autowired
	TaxCodeService taxCodeService;

	@Autowired
	SessionFactory sessionFactory;
	
	@Value("${account_added_message}")
	private String account_added_message;
	
	@Value("${account_unable_to_add_message}")
	private String account_unable_to_add_message;
	
	@Value("${account_deleted_message}")
	private String account_deleted_message;
	
	@Value("${account_unable_to_delete_message}")
	private String account_unable_to_delete_message;
	
	@Value("${account_updated_message}")
	private String account_updated_message;
	
	@Value("${account_unable_to_update_message}")
	private String account_unable_to_update_message;
	
	@Value("${account_dependent_message}")
	private String account_dependent_message;
	
	@Override
	public List<AccountModel> getAll() {
		
		logger.info("AccountServiceImpl getAll() starts ");
		Session session = null;
		List<AccountModel> accountModelList = new ArrayList<AccountModel>();

		try {
			session = sessionFactory.openSession();
			List<Account> accounts = accountDao.findAll(session);

			if (accounts != null && !accounts.isEmpty()) {
				for (Account account : accounts) {
					AccountModel accountModel = new AccountModel();
					BeanUtils.copyProperties(account, accountModel);
					
					if(account.getAccountType() != null){
						accountModel.setAccountTypeName(account.getAccountType().getTypeName());
					}
					
					if(account.getCurrency() != null){
						accountModel.setCurrencyName(account.getCurrency().getTypeName());
					}
					
					if(account.getParentAccount() != null){
						accountModel.setParentAccountName(account.getParentAccount().getAccountName());
					}
					
					if(account.getTaxCode() != null){
						accountModel.setTaxCodeName(account.getTaxCode().getTaxCode());
					}
					accountModelList.add(accountModel);
				}
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
	
		logger.info("AccountServiceImpl getAll() ends ");
		return accountModelList;
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
	public Object addAccount(AccountModel accountModel) {

		logger.info("AccountServiceImpl addAccount() starts ");
		Account account = null;
		Session session = null;
		Transaction tx = null;
		try {
			
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			account = setAccountValues(accountModel, session);
			session.save(account);

		} catch (Exception e) {
			if(tx != null){
				tx.rollback();
			}
			logger.info("Exception inside AccountServiceImpl addAccount() :"+ e.getMessage());
			return createFailedObject(account_unable_to_add_message);

		} finally{
			if(tx != null){
				tx.commit();
			} 
			if(session !=null){
				session.close();
			}
		}
		
		logger.info("AccountServiceImpl addAccount() ends ");
		return createSuccessObject(account_added_message);
	}

	private Account setAccountValues(AccountModel accountModel, Session session) {

		Account account = new Account();
		BeanUtils.copyProperties(accountModel, account);
		
		if(accountModel.getCurrencyId() != null){
			account.setCurrency(typeService.get(accountModel.getCurrencyId()));
		}
		
		if(accountModel.getAccountTypeId() != null){
			account.setAccountType(typeService.get(accountModel.getAccountTypeId()));
		}
		
		if(accountModel.getParentAccountId() != null){
			account.setParentAccount(getParentAccount(accountModel.getParentAccountId()));
		}
		
		if(accountModel.getTaxCodeId() != null){
			account.setTaxCode(taxCodeService.getById(accountModel.getTaxCodeId(), session));
		}
		return account;
	}

	private Account getParentAccount(Long parentAccountId) {

		Session session = null;
		Account account = null;
		try{
			session = sessionFactory.openSession();
			account = (Account) session.get(Account.class, parentAccountId);
		} finally{
			if(session != null){
				session.close();
			}
		}
		
		return account;
	}

	@Override
	public Object update(Long id, AccountModel accountModel) {

		logger.info("AccountServiceImpl update() starts, accountId :"+id);
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Account account = (Account) session.get(Account.class, id);
			
			if (account != null) {
				String[] ignorePro ={"accountId"};
				BeanUtils.copyProperties(accountModel, account, ignorePro);
				if(accountModel.getCurrencyId() != null){
					account.setCurrency(typeService.get(accountModel.getCurrencyId()));
				}
				
				if(accountModel.getAccountTypeId() != null){
					account.setAccountType(typeService.get(accountModel.getAccountTypeId()));
				}
				
				if(accountModel.getParentAccountId() != null){
					account.setParentAccount(getParentAccount(accountModel.getParentAccountId()));
				}
				if(accountModel.getTaxCodeId()!= null){
					account.setTaxCode(taxCodeService.getById(accountModel.getTaxCodeId(), session));
				}
				session.update(account);
				tx.commit();
			} else{
				return createFailedObject(account_unable_to_update_message);
			}

		} catch (Exception e) {
			if(tx != null){
				tx.rollback();
			}
			logger.info("Exception inside AccountServiceImpl update() :"+ e.getMessage());
			return createFailedObject(account_unable_to_update_message);
		} finally{
			if(session != null){
				session.close();
			}
		}
		
		logger.info("AccountServiceImpl update() ends, accountId :"+id);
		return createSuccessObject(account_updated_message);
	}

	@Override
	public Object delete(Long id) {
		
		logger.info("AccountServiceImpl delete() starts, accountId :"+id);
		Session session = null;
		Transaction tx = null;
		
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Account account = (Account) session.get(Account.class, id);
			if(account != null){
				session.delete(account);
				tx.commit();
			} else{
				return createFailedObject(account_unable_to_delete_message);
			}
			
		} catch (Exception e) {
			logger.info("Exception inside AccountServiceImpl delete() : " + e.getMessage());
			if(tx != null){
				tx.rollback();
			}
			if(e instanceof ConstraintViolationException){
				return createFailedObject(account_dependent_message);
			}
			return createFailedObject(account_unable_to_delete_message);
		} finally{
			/*if(tx != null){
				tx.commit();
			}*/
			if(session != null){
				session.close();
			}
		}
		
		logger.info("AccountServiceImpl delete() ends, accountId :"+id);
		return createSuccessObject(account_deleted_message);
	}



	@Override
	public AccountModel get(Long id) {
		
		logger.info("AccountServiceImpl get() starts, accountId :"+id);
		Session session = null;
		AccountModel accountModel = new AccountModel();

		try {

			session = sessionFactory.openSession();
			Account account = accountDao.findById(id, session);

			if (account != null) {
				BeanUtils.copyProperties(account, accountModel);
				if(account.getAccountType() != null){
					accountModel.setAccountTypeName(account.getAccountType().getTypeName());
					accountModel.setAccountTypeId(account.getAccountType().getTypeId());
				}
				
				if(account.getCurrency() != null){
					accountModel.setCurrencyName(account.getCurrency().getTypeName());
					accountModel.setCurrencyId(account.getCurrency().getTypeId());
				}
				
				if(account.getParentAccount() != null){
					accountModel.setParentAccountName(account.getParentAccount().getAccountName());
					accountModel.setParentAccountId(account.getParentAccount().getAccountId());
				}
				
				if(account.getTaxCode() != null){
					accountModel.setTaxCodeId(account.getTaxCode().getTaxCodeId());
				}
				
				accountModel.setTaxCodeList(taxCodeService.getSpecificData());
				accountModel.setAccountTypeList(typeService.getAll(22l));
				accountModel.setCurrencyList(typeService.getAll(21l));
				//accountModel.set
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
		
		logger.info("AccountServiceImpl get() ends, accountId :"+id);
		return accountModel;
	}

	@Override
	public AccountModel getOpenAdd() {
		
		logger.info("AccountServiceImpl getOpenAdd() starts ");
		AccountModel accountModel = new AccountModel();

		accountModel.setAccountTypeList(typeService.getAll(22l));
		accountModel.setCurrencyList(typeService.getAll(21l));
		
		accountModel.setParentAccountList(getSpecificData());
		accountModel.setTaxCodeList(taxCodeService.getSpecificData());
		logger.info("AccountServiceImpl getOpenAdd() ends ");
		
		return accountModel;
	}

	@Override
	public List<AccountModel> getAccountByAccountName(String accountName) {
		
		logger.info("AccountServiceImpl getAccountByAccountName() starts, accountName :"+accountName);
		Session session = null;
		List<AccountModel> taxCodeList = new ArrayList<AccountModel>();

		try {
			session = sessionFactory.openSession();
			List<Account> accounts = accountDao.getAccountByAccountName(session, accountName);
			if (accounts != null && !accounts.isEmpty()) {
				for (Account account : accounts) {
					AccountModel accountModel = new AccountModel();
					BeanUtils.copyProperties(account, accountModel);
					if(account.getAccountType() != null){
						accountModel.setAccountTypeName(account.getAccountType().getTypeName());
					}
					
					if(account.getCurrency() != null){
						accountModel.setCurrencyName(account.getCurrency().getTypeName());
					}
					
					if(account.getParentAccount() != null){
						accountModel.setParentAccountName(account.getParentAccount().getAccountName());
					}
					
					if(account.getTaxCode() != null){
						accountModel.setTaxCodeName(account.getTaxCode().getTaxCode());
					}
					taxCodeList.add(accountModel);
				}
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
		
		logger.info("AccountServiceImpl getAccountByAccountName() ends, accountName :"+accountName);
		return taxCodeList;
	}

	@Override
	public List<AccountModel> getSpecificData() {
		
		Session session = sessionFactory.openSession();
		List<AccountModel> accounts = new ArrayList<AccountModel>();
		
		try{
			List<Object[]> accountData = accountDao.getSpecificData(session,"Account","accountId", "accountName");
			if (accountData != null && !accountData.isEmpty()) {
				for (Object[] row : accountData) {
					AccountModel accountModel = new AccountModel();
					accountModel.setAccountId((Long) row[0]);
					accountModel.setAccountName(String.valueOf(row[1]));
					accounts.add(accountModel);
			}
		}
		}catch(Exception e){
			
		}finally{
			if(session != null){
				session.close();
			}
		}
		return accounts;
	}

}
