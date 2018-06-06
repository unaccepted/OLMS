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

import com.nrifintech.olmsb.domain.Book;
/**
 * This class implements the rowmapper for book
 * @author amanj
 *
 */
public class BookMapper implements RowMapper<Book> {

	@Override
	public Book mapRow(ResultSet rs, int rowNum) throws SQLException {

		Book book = new Book();
		book.setBookPk(rs.getLong("BOOK_PK"));
		book.setBookName(rs.getString("BOOK_NAME"));
		book.setAuthor(rs.getString("AUTHOR"));
		book.setIsbn(rs.getLong("ISBN"));
		book.setPublisher(rs.getString("PUBLISHER"));
		book.setNoOfBook(rs.getLong("NO_OF_COPIES"));
		book.setEdition(rs.getLong("EDITION"));
		book.setAvailableBook(rs.getLong("AVAILABLE_FOR_ISSUE"));
		book.setCcCheck(rs.getLong("CC_CHECK"));
		return book;
	}

}






