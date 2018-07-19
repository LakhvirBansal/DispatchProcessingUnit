/**
 * 
 */
package com.dpu.dao.impl;

import org.springframework.stereotype.Repository;

import com.dpu.dao.ServiceDao;
import com.dpu.dao.StatusDao;
import com.dpu.entity.Service;
import com.dpu.entity.Status;

@Repository
public class StatusDaoImpl extends GenericDaoImpl<Status> implements
		StatusDao {

}
