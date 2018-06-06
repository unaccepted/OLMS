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

import com.nrifintech.olmsb.domain.MemberBook;

/**
 * Used to map objects of Book from ResultSet.
 * @author amols
 *
 */
public class MemberBookMapper implements RowMapper<MemberBook> {

	@Override
	public MemberBook mapRow(ResultSet rs, int rowNum) throws SQLException {

		MemberBook memberBook = new MemberBook();
		
		memberBook.setBookPk(rs.getLong("BOOK_PK"));
		memberBook.setBookName(rs.getString("BOOK_NAME"));
		memberBook.setBookCategoryName(rs.getString("BOOK_CATEGORY_NAME"));
		memberBook.setAuthor(rs.getString("AUTHOR"));
		memberBook.setPublisher(rs.getString("PUBLISHER"));
		memberBook.setIsbn(rs.getLong("ISBN"));
		memberBook.setAvailableForIssue(rs.getString("AVAILABLE_FOR_ISSUE"));
		
		return memberBook;
	}
}
