package com.dpu.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dpu.dao.SaleDao;
import com.dpu.entity.Sale;
import com.dpu.model.SaleReq;
import com.dpu.service.SaleService;

@Service
public class SaleServiceImpl implements SaleService {

	@Autowired
	private SaleDao saleDao;

	@Override
	public List<SaleReq> getAll() {

		List<SaleReq> saleListResponse = new ArrayList<SaleReq>();
		List<Sale> saleList = saleDao.findAll();

		if (saleList != null) {

			for (Sale Sale : saleList) {
				SaleReq saleResponse = new SaleReq();
				BeanUtils.copyProperties(Sale, saleResponse);
				saleResponse.setStatus(Sale.getStatus().getStatus());
				saleListResponse.add(saleResponse);
			}
		}
		return saleListResponse;
	}

}
