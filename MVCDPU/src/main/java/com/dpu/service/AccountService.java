package com.dpu.service;

import java.util.List;

import com.dpu.model.AccountModel;

public interface AccountService {
	Object update(Long id, AccountModel accountModel);

	Object delete(Long id);

	List<AccountModel> getAll();

	AccountModel getOpenAdd();

	AccountModel get(Long id);
	
	List<AccountModel> getSpecificData();

	Object addAccount(AccountModel accountModel);

	List<AccountModel> getAccountByAccountName(String accountName);

}
