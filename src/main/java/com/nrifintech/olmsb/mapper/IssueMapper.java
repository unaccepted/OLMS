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
package com.nrifintech.olmsb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.nrifintech.olmsb.domain.Issue;
/**
 * 
 * @author jayabratas
 * Used to map objects of Admin issue from ResultSet
 */
public class IssueMapper implements RowMapper<Issue> {
	@Override
	public Issue mapRow(ResultSet rs, int rowNum) throws SQLException {
		Issue issue = new Issue();
		issue.setBookInventoryPk(rs.getLong("BOOK_INVENTORY_PK"));
		issue.setBookPk(rs.getLong("BOOK_PK"));
		issue.setIssueDate(rs.getDate("ISSUE_DATE"));
		issue.setIssueId(rs.getString("ISSUE_ID"));
		issue.setIssuePk(rs.getLong("ISSUE_PK"));
		issue.setIssueStatus(rs.getString("ISSUE_STATUS"));
		issue.setStatus(rs.getString("STATUS"));
		issue.setUserPk(rs.getLong("USER_PK"));
		issue.setRequestDate(rs.getDate("REQUEST_DATE"));
		issue.setReturnDate(rs.getDate("RETURN_DATE"));
		issue.setCcCheck(rs.getLong("CC_CHECK"));
		issue.setCreationDate(rs.getDate("CREATION_DATE"));
		issue.setCreatedBy(rs.getString("CREATED_BY"));
		issue.setUpdateDate(rs.getDate("UPDATE_DATE"));
		issue.setUpdatedBy(rs.getString("UPDATED_BY"));
		
		
		
		return issue;
	}
}
