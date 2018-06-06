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
package com.nrifintech.olmsb.service;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.nrifintech.olmsb.AbstractTestCase;
import com.nrifintech.olmsb.domain.BookCategory;
import com.nrifintech.olmsb.dto.BookCategoryDto;

public class BookCategoryServiceImplTestCase extends AbstractTestCase {

	@Autowired
	private BookCategoryService bookCategoryService;
	private BookCategoryDto bookCategoryDto = new BookCategoryDto();

	@Test
	@Transactional
	public void testAddBookCategory() throws Exception {
		bookCategoryDto.setBookCategoryName("Technical");
		bookCategoryDto.setDescription("Technology related books");
		bookCategoryService.addBookCategory(bookCategoryDto);

		bookCategoryDto = new BookCategoryDto();
		bookCategoryDto.setBookCategoryName("tech");
		List<BookCategory> bookcategoryList = bookCategoryService.findBookCategory(bookCategoryDto);
		assertEquals(1, bookcategoryList.size());
	}

	@Test
	@Transactional
	public void testremoveBookCategory() throws Exception {
		bookCategoryDto.setBookCategoryName("Technical");
		bookCategoryDto.setDescription("Technology related books");
		bookCategoryService.addBookCategory(bookCategoryDto);

		bookCategoryDto = new BookCategoryDto();
		bookCategoryDto.setBookCategoryName("tech");
		List<BookCategory> list = bookCategoryService.findBookCategory(bookCategoryDto);
		List<Long> bookCategoryPks = new ArrayList<>();
		bookCategoryPks.add(list.get(0).getBookCategoryPk());
		bookCategoryService.removeBookCategory(bookCategoryPks);

		List<BookCategory> bookcategoryList = bookCategoryService.findBookCategory(bookCategoryDto);
		assertEquals(0, bookcategoryList.size());
	}

	@Test
	@Transactional
	public void testvalidateBookCategory() throws Exception {
		bookCategoryDto.setBookCategoryName("");
		bookCategoryDto.setDescription("Technology related books");

		List<String> errorList = bookCategoryService.validateBookCategory(bookCategoryDto);
		assertEquals(1, errorList.size());

		bookCategoryDto = new BookCategoryDto();
		bookCategoryDto.setBookCategoryName("qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq");
		bookCategoryDto.setDescription(
				"qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq");
		errorList = bookCategoryService.validateBookCategory(bookCategoryDto);
		assertEquals(2, errorList.size());

		bookCategoryDto = new BookCategoryDto();
		bookCategoryDto.setBookCategoryName("Technical");
		bookCategoryDto.setDescription("Technology related books");
		bookCategoryService.addBookCategory(bookCategoryDto);
		errorList = bookCategoryService.validateBookCategory(bookCategoryDto);
		assertEquals(1, errorList.size());

	}
}
