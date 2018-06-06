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

import com.nrifintech.olmsb.domain.BookInventory;
/**
 * This class implements the rowmapper for book inventory
 * @author amanj
 *
 */
public class BookInventoryMapper implements RowMapper<BookInventory> {

	@Override
	public BookInventory mapRow(ResultSet rs, int rowNum) throws SQLException {
		BookInventory bookCopy = new BookInventory();
		bookCopy.setBookInventoryPk(rs.getLong("BOOK_INVENTORY_PK"));
		bookCopy.setBookPk(rs.getLong("BOOK_PK"));
		bookCopy.setBookId(rs.getString("BOOK_ID"));
		bookCopy.setAvailable(rs.getString("AVAILABLE"));
		bookCopy.setStatus(rs.getString("STATUS"));
		bookCopy.setCreatedBy(rs.getString("CREATED_BY"));
		bookCopy.setUpdatedBy(rs.getString("UPDATED_BY"));
		bookCopy.setCreationDate(rs.getDate("CREATION_DATE"));
		bookCopy.setUpdateDate(rs.getDate("UPDATE_DATE"));
		bookCopy.setCcCheck(rs.getLong("CC_CHECK"));
		return bookCopy;
	}
}
