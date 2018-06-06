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

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.nrifintech.olmsb.domain.AdminIssue;
/**
 * 
 * @author jayabratas
 * Used to map objects of Admin issue from ResultSet
 */
public class AdminIssueMapper implements RowMapper<AdminIssue> {
	@Override
	public AdminIssue mapRow(ResultSet rs, int rowNum) throws SQLException {
		AdminIssue adminIssue = new AdminIssue();
		adminIssue.setIssuePk(rs.getLong("ISSUE_PK"));
		adminIssue.setUserId(rs.getString("USER_ID"));
		adminIssue.setIssueId(rs.getString("ISSUE_ID"));
		adminIssue.setIsbn(rs.getString("ISBN").toString());
		java.util.Date newDate = new Date(rs.getDate("REQUEST_DATE").getTime());
		adminIssue.setRequestDate(newDate);
		return adminIssue;
	}
}
