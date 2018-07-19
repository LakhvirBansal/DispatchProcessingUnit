package com.dpu.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dpu.dao.DispatcherDao;
import com.dpu.entity.Dispatcher;
import com.dpu.model.DispatcherModel;
import com.dpu.service.DispatcherService;

@Component
public class DispatcherServiceImpl implements DispatcherService {

	@Autowired
	DispatcherDao dispatcherDao;

	@Override
	public List<DispatcherModel> getAllDispatcher() {

		List<DispatcherModel> response = new ArrayList<DispatcherModel>();
		List<Dispatcher> listOfDispatcher = dispatcherDao.findAll();

		if (listOfDispatcher != null) {
			for (Dispatcher dispatcher : listOfDispatcher) {
				DispatcherModel dispatcherModel = new DispatcherModel();
				try {
					BeanUtils.copyProperties(dispatcherModel, dispatcher);
				} catch (IllegalAccessException | InvocationTargetException e) {

					// e.printStackTrace();
				}
				response.add(dispatcherModel);
			}
		}
		return response;
	}

}
