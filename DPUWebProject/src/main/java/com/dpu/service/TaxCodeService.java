package com.dpu.service;

import java.util.List;

import org.hibernate.Session;

import com.dpu.entity.TaxCode;
import com.dpu.model.TaxCodeModel;

public interface TaxCodeService {
	Object update(Long id, TaxCodeModel taxCodeModel);

	Object delete(Long id);

	List<TaxCodeModel> getAll();

	TaxCodeModel getOpenAdd();

	TaxCodeModel get(Long id);
	
	List<TaxCodeModel> getSpecificData();

	Object addTaxCode(TaxCodeModel taxCodeModel);

	List<TaxCodeModel> getTaxCodeByTaxCodeName(String taxCodeName);
	
	TaxCode getById(Long id,Session session);

}
