package com.dpu.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dpu.dao.ArrangedWithDao;
import com.dpu.entity.ArrangedWith;
import com.dpu.model.ArrangedWithModel;
import com.dpu.service.ArrangedWithService;

@Component
public class ArrangedWithServiceImpl implements ArrangedWithService {

	@Autowired
	ArrangedWithDao arrangedWithDao;

	@Override
	public List<ArrangedWithModel> getAllArrangedWith() {

		try {
			List<ArrangedWith> listOfArrangeWith = arrangedWithDao.findAll();
			List<ArrangedWithModel>  arrangedWithModelList = new ArrayList<ArrangedWithModel>();
			if (listOfArrangeWith != null) {
				for(ArrangedWith arrangedWith : listOfArrangeWith){
					ArrangedWithModel  arrangedWithModel = new ArrangedWithModel();
					BeanUtils.copyProperties(arrangedWith, arrangedWithModel);
					arrangedWithModelList.add(arrangedWithModel);
				}
				return arrangedWithModelList;
			}
		} catch (Exception e) {
			
		}
		return new ArrayList<ArrangedWithModel>();
	}

}
