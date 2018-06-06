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
import javax.sql.DataSource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.nrifintech.olmsb.Constants;
import com.nrifintech.olmsb.domain.AdminIssue;
import com.nrifintech.olmsb.domain.Book;
import com.nrifintech.olmsb.domain.BookInventory;
import com.nrifintech.olmsb.domain.Issue;
import com.nrifintech.olmsb.dto.IssueDto;
import com.nrifintech.olmsb.exception.OLMSException;
import com.nrifintech.olmsb.mapper.AdminIssueMapper;
import com.nrifintech.olmsb.mapper.BookInventoryMapper;
import com.nrifintech.olmsb.mapper.BookMapper;
import com.nrifintech.olmsb.mapper.IssueMapper;
@Repository
public class IssueDaoImpl implements IssueDao{
	private final Logger log = LoggerFactory.getLogger(BookCategoryDaoImpl.class);
	@Autowired
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	@Override
	public List<AdminIssue> getAllIssues(IssueDto issueDto) throws OLMSException {
		List<AdminIssue> issues = new ArrayList<>();
		try {
			StringBuilder query = new StringBuilder();
			query.append(" SELECT issue_pk, user_id, issue_id, isbn, request_date FROM olms_book book, olms_issue issue, olms_user usr")
					.append(" WHERE usr.user_pk=issue.user_pk ")
				    .append(" AND book.book_pk=issue.book_pk ")
				    .append(" AND issue.status='")
				    .append(Constants.NORMAL_STATUS)
				    .append("' ");		    
		    if(!(StringUtils.isEmpty(issueDto.getIssueId()))){
		    	query.append(" AND lower(issue.issue_id) LIKE '%");
		        query.append(StringUtils.lowerCase(issueDto.getIssueId().trim()) + "%'");
		    }
		    if(!(StringUtils.isEmpty(issueDto.getUserId()))){
		    	query.append(" and lower(usr.user_id) like '%");
		        query.append(StringUtils.lowerCase(issueDto.getUserId().trim()) + "%'");
		    }    		       
		    if(!(StringUtils.isEmpty(issueDto.getFromDateStr()))){
		    	String date=issueDto.getFromDateStr();
		    	query.append(" and issue.request_date >= ");
		    	query.append(" TO_DATE('");
		    	query.append(date);
		    	query.append("', 'mm/dd/yyyy')");
		    }    
		    if(!(StringUtils.isEmpty(issueDto.getToDateStr()))){
		    	String date=issueDto.getToDateStr();
		    	query.append(" and issue.request_date <= ");
		    	query.append(" TO_DATE('");
		    	query.append(date);
		    	query.append("', 'mm/dd/yyyy')");
		    }
		    if(!(StringUtils.isEmpty(issueDto.getIsbnValue().trim()))){
		    	Long val=Long.parseLong(issueDto.getIsbnValue().trim());
		    	query.append(" and book.isbn like '%");
		    	query.append(val);
		    	query.append("%'");
		    	}
		    query.append(" and issue.issue_status="+issueDto.getStatus());
		    query.append(" order by issue.request_date desc, issue.user_pk asc");
		    JdbcTemplate jdbcTemplate =  new JdbcTemplate(dataSource);
		    issues = jdbcTemplate.query(query.toString(), new AdminIssueMapper());
		    }
			catch (Exception e) {
			log.error("Unable to query issues", e);
			throw new OLMSException("Unable to query issues");
			}
		return issues;
	}
	@Override
	public List<String> acceptIssues(List<Long> issues) throws OLMSException {
		List<String> errors=new ArrayList<String>();
		try{
		JdbcTemplate jdbcTemplate =  new JdbcTemplate(dataSource);
		for(int i=0; i< issues.size(); i++) {
			long issuePk = issues.get(i);
			String sql="select issue_status from olms_issue where issue_pk="+ issuePk;
			String issueStatus= jdbcTemplate.queryForList(sql, String.class).get(0);
			
			if(StringUtils.equals(issueStatus,"RETURN_REQUESTED")){
				acceptReturn(issuePk);
			}
			
			if(StringUtils.equals(issueStatus,"ISSUE_REQUESTED")){
				acceptRequest(issuePk);
			}
		}
		}
		catch(DataAccessException e)
		{
			log.error(e.getMessage(), e);
			throw new OLMSException(e.getMessage());
		}
		return errors;
	}
	@Override
	public void acceptReturn(long issuePk) throws OLMSException {
		JdbcTemplate jdbcTemplate =  new JdbcTemplate(dataSource);
		int success1=0,success2=0,success3=0;
		try{
			//fetch the concerned issue
			String sql=" select * from olms_issue where issue_pk=? and status=?";
			Issue issue=jdbcTemplate.queryForObject(sql, new Object[]{issuePk,Constants.NORMAL_STATUS}, new IssueMapper());
			//update the concerned issue
			sql="update olms_issue set issue_status='RETURNED',cc_check=?,return_date=? where issue_pk=? and cc_check=?";
			success1=jdbcTemplate.update(sql,new Object[]{(issue.getCcCheck()+1),new Date(),issue.getIssuePk(),issue.getCcCheck()});
			//fetch the particular book
			sql="select * from olms_book where book_pk=? and status=?";
			Book book=jdbcTemplate.queryForObject(sql, new Object[]{issue.getBookPk(),Constants.NORMAL_STATUS}, new BookMapper());
			//increase the no. of available copies for book to be returned
			sql="update olms_book set available_for_issue=?,cc_check=? where book_pk=? and cc_check=?";
			success2=jdbcTemplate.update(sql,new Object[]{(book.getAvailableBook()+1),(book.getCcCheck()+1),book.getBookPk(),book.getCcCheck()} );
			//fetch particular copy assigned to the issue
			sql="select * from olms_book_inventory where book_inventory_pk=?";
			BookInventory bookInventory= jdbcTemplate.queryForObject(sql, new Object[]{issue.getBookInventoryPk()}, new BookInventoryMapper());
			//set the copy as available in the database
			sql="update olms_book_inventory set available='y',cc_check=? where book_inventory_pk=? and cc_check=?";
			success3=jdbcTemplate.update(sql,new Object[]{(bookInventory.getCcCheck()+1),bookInventory.getBookInventoryPk(),bookInventory.getCcCheck()});
			
			if(success1==0||success2==0||success3==0)
			{
				throw new OLMSException("Unable to process requests, Concurrency failure");
			}
		}
		catch(DataAccessException e)
		{
			log.error("Unable to accept return", e);
			throw new OLMSException("Unable to accept return");
		}
	}
	@Override
	public void acceptRequest(long issuePk) throws OLMSException {
		JdbcTemplate jdbcTemplate =  new JdbcTemplate(dataSource);
		int success1=0,success2=0,success3=0;
		try{
			//fetch issue to accept
			String sql=" select * from olms_issue where issue_pk=? and status=?";
			Issue issue=jdbcTemplate.queryForObject(sql, new Object[]{issuePk,Constants.NORMAL_STATUS}, new IssueMapper());
			//fetch book to accept
			sql="select * from olms_book where book_pk=? and status=?";
			Book book=jdbcTemplate.queryForObject(sql, new Object[]{issue.getBookPk(),Constants.NORMAL_STATUS}, new BookMapper());
			if(book.getAvailableBook()==0)
			{
				throw new OLMSException("Error accepting issue:"+issue.getIssueId()+". No copies currently available for issue");
			}
			//reduce no. of copies available for corresponding book
			sql="update olms_book set available_for_issue=?,cc_check=? where book_pk=? and cc_check=?";
			success1=jdbcTemplate.update(sql,new Object[]{(book.getAvailableBook()-1),(book.getCcCheck()+1),book.getBookPk(),book.getCcCheck()} );
			//fetch particular copy to be assigned to issue
			sql="select * from olms_book_inventory where book_pk=? and status=? and available='y'";
			BookInventory bookInventory=(jdbcTemplate.query(sql, new Object[]{issue.getBookPk(),Constants.NORMAL_STATUS}, new BookInventoryMapper())).get(0);
			//update the particular copy
			sql="update olms_book_inventory set available='n',cc_check=? where book_inventory_pk=? and cc_check=?";
			success2=jdbcTemplate.update(sql,new Object[]{(bookInventory.getCcCheck()+1),bookInventory.getBookInventoryPk(),bookInventory.getCcCheck()});
			//update the issue concerned
			sql="update olms_issue set issue_status='ISSUED',book_inventory_pk=?,cc_check=?,issue_date=? where issue_pk=? and cc_check=?";
			success3=jdbcTemplate.update(sql,new Object[]{bookInventory.getBookInventoryPk(),(issue.getCcCheck()+1),new Date(),issue.getIssuePk(),issue.getCcCheck()});
			
			if(success1==0||success2==0||success3==0)
			{
				throw new OLMSException("Unable to process requests, Concurrency failure");
			}
		}
		catch(DataAccessException e)
		{
			log.error("Unable to accept request", e);
			throw new OLMSException("Unable to accept request"+(e.getMessage()));
		}
	}
}
