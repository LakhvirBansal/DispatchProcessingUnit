package com.dpu.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.dpu.dao.AccountDao;
import com.dpu.dao.TaxCodeDao;
import com.dpu.entity.Account;
import com.dpu.entity.TaxCode;

@Repository
public class AccountDaoImpl extends GenericDaoImpl<Account> implements AccountDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<Account> findAll(Session session) {
		
		StringBuilder sb = new StringBuilder(" select a from Account a left join fetch a.currency left join fetch a.accountType left join fetch a.parentAccount left join fetch a.taxCode ");
		Query query = session.createQuery(sb.toString());
		return query.list();
	}

	@Override
	public Account findById(Long id, Session session) {
		StringBuilder sb = new StringBuilder(" select a from Account a left join fetch a.currency left join fetch a.accountType left join fetch a.parentAccount left join fetch a.taxCode where a.accountId =:accountId ");
		Query query = session.createQuery(sb.toString());
		query.setParameter("accountId", id);
		return (Account) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Account> getAccountByAccountName(Session session, String accountName) {
		StringBuilder sb = new StringBuilder(" select a from Account a left join fetch a.currency left join fetch a.accountType left join fetch a.parentAccount left join fetch a.taxCode where a.accountName like :accountName ");
		Query query = session.createQuery(sb.toString());
		query.setParameter("accountName", "%"+accountName+"%");
		return query.list();
	}

}
