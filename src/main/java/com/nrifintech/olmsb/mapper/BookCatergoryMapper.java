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

import com.nrifintech.olmsb.domain.BookCategory;

/**
 * Used to map objects of Book Category from ResultSet.
 */
public class BookCatergoryMapper implements RowMapper<BookCategory> {

	@Override
	public BookCategory mapRow(ResultSet rs, int rowNum) throws SQLException {

		BookCategory bookCategory = new BookCategory();
		bookCategory.setBookCategoryPk(rs.getLong("BOOK_CATEGORY_PK"));
		bookCategory.setBookCategoryName(rs.getString("BOOK_CATEGORY_NAME"));
		bookCategory.setDescription(rs.getString("DESCRIPTION"));
		bookCategory.setCreatedBy(rs.getString("CREATED_BY"));
		bookCategory.setUpdatedBy(rs.getString("UPDATED_BY"));
		bookCategory.setCreationDate(rs.getDate("CREATION_DATE"));
		bookCategory.setUpdateDate(rs.getDate("UPDATE_DATE"));
		bookCategory.setCcCheck(rs.getLong("CC_CHECK"));
		
		return bookCategory;
	}

}
