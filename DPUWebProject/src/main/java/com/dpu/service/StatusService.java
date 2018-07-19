
package com.dpu.service;

import java.util.List;

import com.dpu.entity.Status;


public interface StatusService {

	List<Status> getAll();

	Status get(Long statusId);

	Status getByName(String name);

}
