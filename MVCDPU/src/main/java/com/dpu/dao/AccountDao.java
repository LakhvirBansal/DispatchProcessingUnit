package com.dpu.dao;

import java.util.List;

import org.hibernate.Session;

import com.dpu.entity.Account;
import com.dpu.entity.TaxCode;

public interface AccountDao extends GenericDao<Account> {

	List<Account> findAll(Session session);

	Account findById(Long id, Session session);

	List<Account> getAccountByAccountName(Session session, String accountName);
}
