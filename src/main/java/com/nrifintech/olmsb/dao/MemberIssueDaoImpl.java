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
import java.util.List;

import javax.sql.DataSource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.nrifintech.olmsb.Constants;
import com.nrifintech.olmsb.domain.MemberIssue;
import com.nrifintech.olmsb.dto.IssueDto;
import com.nrifintech.olmsb.exception.OLMSException;
import com.nrifintech.olmsb.mapper.MemberIssueMapper;
@Repository
public class MemberIssueDaoImpl implements MemberIssueDao{
	private final Logger log = LoggerFactory.getLogger(BookCategoryDaoImpl.class);
	@Autowired
	private DataSource dataSource;
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	@Override
	public List<MemberIssue> getAllIssues(IssueDto issueDto) throws OLMSException {
    List<MemberIssue> issues = new ArrayList<>();
	try {
		StringBuilder query = new StringBuilder();
		query.append(" SELECT issue_pk, book_name, book_category_name, isbn, request_date, issue_status")
				.append(" FROM olms_book book, olms_issue issue, olms_book_category cat, OLMS_USER user_table")
				.append(" WHERE issue.book_pk =  book.book_pk AND book.book_category_pk = cat.book_category_pk")
				.append(" AND issue.user_pk = user_table.user_pk")
				.append(" AND issue.status='")
			    .append(Constants.NORMAL_STATUS)
			    .append("' ");
		//Check to see if fromDate isEmpty, if not: the query criteria is appended to the query string
		if(!(StringUtils.isEmpty(issueDto.getFromDateStr()))){
	    	String date=issueDto.getFromDateStr();
	    	query.append(" and issue.request_date >= ");
	    	query.append(" TO_DATE('");
	    	query.append(date);
	    	query.append("', 'mm/dd/yyyy')");
	    }  
		//Check to see if toDate isEmpty, if not: the query criteria is appended to the query string
	    if(!(StringUtils.isEmpty(issueDto.getToDateStr()))){
	    	String date=issueDto.getToDateStr();
	    	query.append(" and issue.request_date <= ");
	    	query.append(" TO_DATE('");
	    	query.append(date);
	    	query.append("', 'mm/dd/yyyy')");
	    }
	    //Check to see if isbn isEmpty, if not: the query criteria is appended to the query string
	    if(!(StringUtils.isEmpty(issueDto.getIsbnValue().trim()))){
	    	Long val=Long.parseLong(issueDto.getIsbnValue().trim());
	    	query.append(" and book.isbn like '%");
	    	query.append(val);
	    	query.append("%'");
	    	}
	    //Check to see if bookName isEmpty, if not: the query criteria is appended to the query string
	    if(!(StringUtils.isEmpty(issueDto.getBookName()))){
	    	query.append(" and lower(book.book_name) like '%");
	    	query.append(StringUtils.lowerCase(issueDto.getBookName().trim()));
	    	query.append("%'");
	    	}
	    //Check to see if bookName isEmpty, if not: the query criteria is appended to the query string
	    if(!(StringUtils.isEmpty(issueDto.getBookCategory()))){
	    	query.append(" and lower(cat.book_category_name) like '%");
	    	query.append(StringUtils.lowerCase(issueDto.getBookCategory().trim()));
	    	query.append("%'");
	    	}
	    query.append(" and issue.issue_status='"+issueDto.getStatus()+"'");
	    query.append(" and user_table.user_id='"+issueDto.getUserId()+"'");
	    query.append(" order by issue.request_date desc, issue.book_pk asc");
	    JdbcTemplate jdbcTemplate =  new JdbcTemplate(dataSource);
	    issues = jdbcTemplate.query(query.toString(), new MemberIssueMapper());
	}
	catch(Exception e){
		log.error("Unable to query issues", e);
		throw new OLMSException("Unable to query issues");
		}
	return issues;
	}
	@Override
	public List<String> returnBook(List<Long> issues) throws OLMSException {
		List<String> errors=new ArrayList<String>();
		try{
			JdbcTemplate jdbcTemplate =  new JdbcTemplate(dataSource);
			for(int i=0; i< issues.size(); i++) {
				long issuePk = issues.get(i);
				String status="'RETURN_REQUESTED'";
				String sql=" select cc_check from olms_issue where issue_pk="+issuePk;
				Long ccIssue=jdbcTemplate.queryForList(sql, Long.class).get(0);
				sql=" update olms_issue set issue_status="+status+" ,cc_check="+(ccIssue+1)+" where issue_pk="+issuePk+" and cc_check="+ccIssue;
				int success=jdbcTemplate.update(sql);
				
				if(success==0){
					throw new OLMSException("Unable to process request, Concurrency failure");
				}
					
			}
		}
			catch(DataAccessException e)
			{
				log.error("Unable to return books", e);
				throw new OLMSException("Unable to return books");
			}
		return errors;
	}
}
