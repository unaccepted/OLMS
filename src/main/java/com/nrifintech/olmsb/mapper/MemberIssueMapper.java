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
import com.nrifintech.olmsb.domain.MemberIssue;
/**
 * 
 * @author jayabratas
 * Used to map objects of Member issue from ResultSet
 */
public class MemberIssueMapper implements RowMapper<MemberIssue> {

	@Override
	public MemberIssue mapRow(ResultSet rs, int rowNum) throws SQLException {
		MemberIssue memberIssue = new MemberIssue();
		memberIssue.setIssuePk(rs.getLong("ISSUE_PK"));
		memberIssue.setBookName(rs.getString("BOOK_NAME"));
		memberIssue.setCategory(rs.getString("BOOK_CATEGORY_NAME"));
		memberIssue.setIsbn(rs.getString("ISBN").toString());
		memberIssue.setRequestDate(rs.getDate("REQUEST_DATE"));
		memberIssue.setIssueStatus(rs.getString("ISSUE_STATUS"));
		return memberIssue;
	}

}
