package com.dpu.service;

import java.util.List;

import org.hibernate.Session;

import com.dpu.model.CategoryModel;
import com.dpu.model.IssueModel;

public interface IssueService {
	Object update(Long id, IssueModel issueModel);

	Object delete(Long id);

	List<IssueModel> getAll();

	IssueModel getOpenAdd();

	IssueModel get(Long id);
	
	List<IssueModel> getSpecificData();

	IssueModel getUnitNo(Long categoryId, Long unitTypeId);

	Object addIssue(IssueModel issueModel);

	List<IssueModel> getIssueByIssueName(String issueName);

	List<IssueModel> getActiveAndIncompleteIssues();

	List<IssueModel> getIssueforCategoryAndUnitType(Long categoryId, Long unitTypeId, Session session);

	Object updateStatus(Long issueId, Long statusId);

	List<CategoryModel> getUnitCategories(String unitTypeName);

	List<IssueModel> getIssuesBasedOnUnitTypeAndNo(Long unitTypeId, Long unitNo, Session session);

	List<IssueModel> getIssuesBasedOnUnitTypeAndUnitNos(Long unitTypeId, List<String> unitNos, Session session);

}
