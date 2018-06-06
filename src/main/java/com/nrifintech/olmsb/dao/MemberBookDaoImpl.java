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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.nrifintech.olmsb.Constants;
import com.nrifintech.olmsb.domain.MemberBook;
import com.nrifintech.olmsb.dto.BookDto;
import com.nrifintech.olmsb.exception.OLMSException;
import com.nrifintech.olmsb.mapper.MemberBookMapper;
import com.nrifintech.olmsb.service.MemberBookServiceImpl;

@Repository
public class MemberBookDaoImpl implements MemberBookDao {
	private final Logger log = LoggerFactory.getLogger(MemberBookServiceImpl.class);
	
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	@PostConstruct
	public void init() {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<MemberBook> findBook(BookDto bookDto) throws OLMSException {
		List<MemberBook> memberBook = new ArrayList<>();
		try {
			StringBuilder query = new StringBuilder();
			query.append(" select book_pk,book_name,author,publisher,isbn,book_category_name,available_for_issue");
			query.append(" from olms_book book, olms_book_category cat");
			query.append(" where cat.book_category_pk = book.book_category_pk");

			if (!(bookDto.getBookName().isEmpty())) {
				query.append(" and book.book_name like '%");
				query.append(bookDto.getBookName());
				query.append("%'");
			}

			if (!(bookDto.getCategory().isEmpty())) {
				query.append(" and cat.book_category_name like '%");
				query.append(bookDto.getCategory());
				query.append("%'");

			}

			if (!(bookDto.getAuthor().isEmpty())) {
				query.append(" and book.author like '%");
				query.append(bookDto.getAuthor());
				query.append("%'");
			}

			if (!(bookDto.getIsbn().isEmpty())) {
				query.append(" and book.isbn like '%");
				query.append(bookDto.getIsbn());
				query.append("%'");
			}

			query.append(" and book.status='");
			query.append(Constants.NORMAL_STATUS);
			query.append("'");
			query.append(" order by book.book_name asc, book.isbn asc");

			memberBook = jdbcTemplate.query(query.toString(), new MemberBookMapper());
		} catch (DataAccessException e) {
			log.error("Unable to query book", e);
			throw new OLMSException("Unable to query book");
		}
		return memberBook;

	}

	@Override
	public void issueBooksForParticularUser(List<Long> bookPks, String userId) throws OLMSException {
		try {

			String selectSqlForUser = "SELECT user_pk FROM olms_user where user_id=?";
			Long userPk = jdbcTemplate.queryForObject(selectSqlForUser, new Object[] { userId }, Long.class);

			long countForRequest = bookPks.size();
			String sqlForIssueStatus = "select count(issue_status) from olms_issue where issue_status in (?,?,?) and user_pk=? and status=?";
			long countForExistingStatus = jdbcTemplate
					.queryForList(sqlForIssueStatus, new Object[] { Constants.ISSUE_REQUESTED, Constants.ISSUED,
							Constants.RETURN_REQUESTED, userPk, Constants.NORMAL_STATUS }, Long.class)
					.get(0);
			if ((countForRequest + countForExistingStatus) <= 10) {
				for (Long bookPk : bookPks) {

					String selectSql = "SELECT available_for_issue FROM olms_book where book_pk=?";
					Long availableForIssue = jdbcTemplate.queryForObject(selectSql, new Object[] { bookPk },
							Long.class);

					if (availableForIssue != 0) {
						String sql = "select seq_olms_issue.nextval from dual";
						Long issuePk = jdbcTemplate.queryForList(sql, Long.class).get(0);
						String issueId = "I" + issuePk;
						StringBuilder insertSql = new StringBuilder();

						insertSql.append("INSERT INTO olms_issue ")
								.append("(issue_pk, issue_id, user_pk, book_inventory_pk, book_pk, request_date, issue_date,")
								.append("return_date, issue_status, status, created_by, creation_date, updated_by, update_date, cc_check)")
								.append("values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

						jdbcTemplate.update(insertSql.toString(), issuePk, issueId, userPk, null, bookPk, new Date(),
								null, null, Constants.ISSUE_REQUESTED, Constants.NORMAL_STATUS, Constants.SYSTEM,
								new Date(), Constants.SYSTEM, new Date(), 1);
					} else {
						log.error(" Unable to request for issuing book :");
						throw new OLMSException(" Unable to request for issuing book :");
					}
				}
			} else {
				log.error("You can not request for issuing book more than 10");
				throw new OLMSException("You can not request for issuing book more than 10");
			}
		} catch (DataAccessException e) {
			log.error(" Unable to request for issuing book :", e);
			throw new OLMSException(" Unable to request for issuing book :", e);
		}
	}
}
