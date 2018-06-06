/*
 * +=============================================================================================+
 * |                                                                                             |
 * |                                Copyright (C) 2013-2018                                      |
 * |            Nomura Research Institute Financial Technologies India Pvt. Ltd.                 |
 * |                                  All Rights Reserved                                        |
 * |                                                                                             |
 * |  This document is the sole property of Nomura Research Institute Financial Technologies     |
 * |  India Pvt. Ltd. No part of this document may be reproduced in any form or by any           |
 * |  means - electronic, mechanical, photocopying, recording or otherwise - without the prior   |
 * |  written permission of Nomura Research Institute Financial Technologies India Pvt. Ltd.     |
 * |                                                                                             |
 * |  Unless required by applicable law or agreed to in writing, software distributed under      |
 * |  the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF        |
 * |  ANY KIND, either express or implied.                                                       |
 * |                                                                                             |
 * +=============================================================================================+
 */

package com.nrifintech.olmsb.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.nrifintech.olmsb.Constants;
import com.nrifintech.olmsb.domain.Issue;
import com.nrifintech.olmsb.domain.MemberIssue;
import com.nrifintech.olmsb.domain.RolePcpt;
import com.nrifintech.olmsb.domain.User;
import com.nrifintech.olmsb.dto.IssueDto;
import com.nrifintech.olmsb.dto.UsersDto;
import com.nrifintech.olmsb.exception.OLMSException;
import com.nrifintech.olmsb.mapper.IssueMapper;
import com.nrifintech.olmsb.mapper.RolePcptMapper;
import com.nrifintech.olmsb.mapper.UsersMapper;

@Repository
public class UsersDaoImpl implements UsersDao {
	
	private final Logger log = LoggerFactory.getLogger(UsersDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Autowired
	MemberIssueDao memberIssueDao;

	@PostConstruct
	public void init() {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<User> getNewRegistrationRequest(UsersDto usersDto) throws OLMSException {
		List<User> newUsers = new ArrayList<>();
		try {
			StringBuilder query = new StringBuilder();
			query.append(" select * from olms_user ");
			query.append(" where status='");
			query.append(Constants.PENDING_STATUS);
			query.append("'");

			if (!StringUtils.isEmpty(usersDto.getFirstName())) {
				query.append(" and lower(first_name) like '%");
				query.append(StringUtils.lowerCase(usersDto.getFirstName().trim()));
				query.append("%'");
			}
			if (!StringUtils.isEmpty(usersDto.getLastName())) {
				query.append(" and  lower(last_name) like '%");
				query.append(StringUtils.lowerCase(usersDto.getLastName().trim()));
				query.append("%'");
			}
			if (!StringUtils.isEmpty(usersDto.getUserId())) {
				query.append(" and  lower(user_id) like '%");
				query.append(StringUtils.lowerCase(usersDto.getUserId().trim()));
				query.append("%'");
			}
			if (!StringUtils.isEmpty(usersDto.getEmail())) {
				query.append(" and  lower(email) like '%");
				query.append(StringUtils.lowerCase(usersDto.getEmail().trim()));
				query.append("%'");
			}
			query.append(" ORDER BY user_id asc, update_date desc");
			newUsers = jdbcTemplate.query(query.toString(), new UsersMapper());
		} catch (DataAccessException e) {
			log.error("Unable to query Existing users", e);
			throw new OLMSException("Unable to query Existing users", e);
		}
		return newUsers;
	}

	@Override
	public List<User> findExistingUser(UsersDto usersDto) throws OLMSException {
		List<User> existingUsers = new ArrayList<>();
		try {
			StringBuilder query = new StringBuilder();
			query.append(" SELECT * ");
			query.append(" FROM olms_user usr, olms_user_role_pcpt pcpt");
			query.append(" where usr.user_pk = pcpt.user_pk ");
			query.append(" AND pcpt.role_pk = 2 ");
			query.append(" AND usr.status='");
			query.append(Constants.NORMAL_STATUS);
			query.append("'");
			

			if (!StringUtils.isEmpty(usersDto.getFirstName())) {
				query.append(" and lower(usr.first_name) like '%");
				query.append(StringUtils.lowerCase(usersDto.getFirstName().trim()));
				query.append("%'");
			}
			if (!StringUtils.isEmpty(usersDto.getLastName())) {
				query.append(" and  lower(usr.last_name) like '%");
				query.append(StringUtils.lowerCase(usersDto.getLastName().trim()));
				query.append("%'");
			}
			if (!StringUtils.isEmpty(usersDto.getUserId())) {
				query.append(" and  lower(usr.user_id) like '%");
				query.append(StringUtils.lowerCase(usersDto.getUserId().trim()));
				query.append("%'");
			}
			if (!StringUtils.isEmpty(usersDto.getEmail())) {
				query.append(" and  lower(usr.email) like '%");
				query.append(StringUtils.lowerCase(usersDto.getEmail().trim()));
				query.append("%'");
			}
			query.append(" ORDER BY usr.user_id asc, usr.update_date desc");
			existingUsers = jdbcTemplate.query(query.toString(), new UsersMapper());
		} catch (Exception e) {
			log.error("Unable to query Existing users", e);
			throw new OLMSException("Unable to query Existing users");
		}
		return existingUsers;
	}

	@Override
	public void removeUsers(List<Long> userPks) throws OLMSException {
		try {
			for (Long userPk : userPks) {
				String selectSqlUser = "select * from olms_user where user_pk=?";
				User user = jdbcTemplate.queryForObject(selectSqlUser, new Object[] { userPk }, new UsersMapper());

				// check whether any issue belongs to the user or not
				String userID = user.getUserId();
				IssueDto issueDto = new IssueDto();
				issueDto.setUserId(userID);
				issueDto.setStatus(Constants.RETURN_REQUESTED);
				List<MemberIssue> returnIssues = memberIssueDao.getAllIssues(issueDto);
				issueDto.setStatus(Constants.ISSUED);
				List<MemberIssue> issued = memberIssueDao.getAllIssues(issueDto);

				// if no issue found
				if (returnIssues.isEmpty() && issued.isEmpty()) {

					// status update in user table as cancel
					String updateSqlUser = "update olms_user set status=?, cc_check=? where user_pk=? and cc_check=?";
					long ccCheckUser = user.getCcCheck() + 1;
					int usersUpdateCount = jdbcTemplate.update(updateSqlUser, Constants.CANCEL_STATUS, ccCheckUser,
							userPk, user.getCcCheck());
					if (usersUpdateCount <= 0) {
						log.error("Unable to remove user : " + user.getUserId());
						throw new OLMSException("Unable to remove user : " + user.getUserId());

					}

					// update in pcpt table
					String selectSqlpcpt = "select * from olms_user_role_pcpt where user_pk=?";
					RolePcpt role = jdbcTemplate.queryForObject(selectSqlpcpt, new Object[] { userPk },
							new RolePcptMapper());
					String updateSqlpcpt = "update olms_user_role_pcpt set status=?, cc_check=?  where user_pk=? and cc_check=?";
					long ccCheckRole = role.getCcCheck() + 1;
					int roleUpdateCount = jdbcTemplate.update(updateSqlpcpt, Constants.CANCEL_STATUS, ccCheckRole,
							userPk, role.getCcCheck());
					if (roleUpdateCount <= 0) {
						log.error("Unable to remove user : " + user.getUserId());
						throw new OLMSException(" Unable to remove user : " + user.getUserId());
					}

					// update in issue table
					String selectSqlIssue = "select * from olms_issue where user_pk=?";
					List<Issue> issues = jdbcTemplate.query(selectSqlIssue, new Object[] { userPk }, new IssueMapper());
					String updateSqlIssue = "update olms_issue set status=?, cc_check=?  where issue_pk=? and cc_check=?";
					for (int j = 0; j < issues.size(); j++) {
						Issue issue = issues.get(j);
						long ccCheckIssue = issue.getCcCheck() + 1;
						int issueUpdateCount = jdbcTemplate.update(updateSqlIssue, Constants.CANCEL_STATUS,
								ccCheckIssue, issue.getIssuePk(), issue.getCcCheck());
						if (issueUpdateCount == 0) {
							log.error(" Unable to remove user : " + user.getUserId());
							throw new OLMSException(" Unable to remove user : " + user.getUserId());
						}
					}
				} else {
					log.error("User has already issued a book, user id : {}", user.getUserId());
					throw new OLMSException("User has already issued a book, user id : " + user.getUserId());
				}
			}
		} catch (DataAccessException e) {
			log.error("Unable to remove users ", e);
			throw new OLMSException("Unable to remove users : ", e);
		}
	}

	@Override
	public void addUsers(List<Long> userPks) throws OLMSException {
		try {
			for (Long userPk : userPks) {
				String selectSqlUser = "select * from olms_user where user_pk=?";
				User user = jdbcTemplate.queryForObject(selectSqlUser, new Object[] { userPk }, new UsersMapper());
				String updateSqlUser = "update olms_user set status=?, cc_check=? where user_pk=? and cc_check=?";
				long ccCheck = user.getCcCheck() + 1;
				int count = jdbcTemplate.update(updateSqlUser, Constants.NORMAL_STATUS, ccCheck, userPk,
						user.getCcCheck());
				if (count <= 0) {
					log.error("Unable to add user : " + user.getUserId());
					throw new OLMSException("Unable to Add user : " + user.getUserId());
				}

				String selectSqlRole = "select role_pk from olms_role where role_name=?";
				long rolePk = jdbcTemplate.queryForObject(selectSqlRole, new Object[] { Constants.MEMBER }, Long.class);

				String insertSqlRole = "insert into olms_user_role_pcpt "
						+ "(user_role_pcpt_pk, user_pk, role_pk, status, created_by, creation_date, updated_by, update_date, cc_check) "
						+ "values(SEQ_OLMS_USER_ROLE_PCPT.nextval, ?, ?, ?, ?, ? ,?, ?, ?)";
				jdbcTemplate.update(insertSqlRole, userPk, rolePk, Constants.NORMAL_STATUS, Constants.SYSTEM,
						new Date(), Constants.SYSTEM, new Date(), 1);
			}
		} catch (DataAccessException e) {
			log.error("Unable to add users ", e);
			throw new OLMSException("Unable to add users : ", e);
		}
	}

	@Override
	public void rejectUsers(List<Long> userPks) throws OLMSException {
		try {
			for (Long userPk : userPks) {
				String selectSqlUser = "select * from olms_user where user_pk=?";
				User user = jdbcTemplate.queryForObject(selectSqlUser, new Object[] { userPk }, new UsersMapper());
				String updateSqlUser = "update olms_user set status=?, cc_check=? where user_pk=? and cc_check=?";
				long ccCheck = user.getCcCheck() + 1;
				int updateCount = jdbcTemplate.update(updateSqlUser, Constants.REJECTED_STATUS, ccCheck, userPk,
						user.getCcCheck());
				if (updateCount <= 0) {
					log.error("Unable to reject user request: " + user.getUserId());
					throw new OLMSException("Unable to reject user request:  " + user.getUserId());
				}
			}
		} catch (DataAccessException e) {
			log.error("Unable to reject users", e);
			throw new OLMSException("Unable to reject users", e);
		}
	}
}
