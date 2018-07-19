package com.dpu.dao;

import java.util.List;

import org.hibernate.Session;

import com.dpu.entity.Issue;
import com.dpu.entity.Type;

public interface IssueDao extends GenericDao<Issue> {

	List<Issue> findAll(Session session);

	Issue findById(Long id, Session session);

	List<Object> getUnitNos(Long categoryId, Long unitTypeId, Session session);

	List<Issue> getIssueByIssueName(Session session, String issueName);

	void saveIssue(Issue issue, Session session);

	void update(Issue issue, Session session);

	List<Issue> findAllActiveAndIncompleteIssues(Session session);

	List<Issue> issueforCategoryAndUnitType(Long categoryId, Long unitTypeId, Session session);

	void updateStatus(Issue issue, Type status, Session session);

	List<Issue> issuesforUnitTypeAndNo(Long unitTypeId, Long unitNo, Session session);

	List<Issue> issuesforUnitTypeAndUnitNos(Long unitTypeId, List<String> unitNos, Session session);
}
