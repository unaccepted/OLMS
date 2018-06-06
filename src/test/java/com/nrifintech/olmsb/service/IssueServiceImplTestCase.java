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
import com.nrifintech.olmsb.domain.AdminIssue;
import com.nrifintech.olmsb.domain.Book;
import com.nrifintech.olmsb.dto.BookCategoryDto;
import com.nrifintech.olmsb.dto.BookDto;
import com.nrifintech.olmsb.dto.IssueDto;

public class IssueServiceImplTestCase extends AbstractTestCase {

	@Autowired
	private IssueService issueService;
	@Autowired
	private BookService bookService;
	@Autowired
	private BookCategoryService bookCategoryService;
	@Autowired
	private MemberBookService memberBookService ;
	
	private BookCategoryDto bookCategoryDto = new BookCategoryDto();
	private BookDto bookDto = new BookDto();
	private IssueDto issueDto=new IssueDto();
	
	@Test
	@Transactional
	public void testGetIssues() throws Exception {
		bookCategoryDto.setBookCategoryName("Technical");
		bookCategoryDto.setDescription("Technology related books");
		bookCategoryService.addBookCategory(bookCategoryDto);
		bookDto.setBookName("JAVA");
		bookDto.setAuthor("JAVA");
		bookDto.setIsbn("1471234567894");
		bookDto.setPublisher("ABC");
		bookDto.setCategory("Technical");
		bookDto.setEdition("1");
		bookDto.setNoOfCopies("2");
		bookService.addBook(bookDto);
		List<Book> list = bookService.findBook(bookDto);
		Long bookPk=(list.get(0).getBookPk());
		List<Long> bookPkList=new ArrayList<>();
		bookPkList.add(bookPk);
		memberBookService.issueBooksForParticularUser(bookPkList, "member");
		issueDto.setUserId("member");
		issueDto.setStatus("'ISSUE_REQUESTED'");
		List<AdminIssue> issueRs=issueService.getIssues(issueDto);
		assertEquals(1,issueRs.size());
		issueDto.setUserId("ABC");
		issueRs=issueService.getIssues(issueDto);
		assertEquals(0,issueRs.size());
		
	}
		@Test
		@Transactional
		public void testAcceptIssue() throws Exception {
			bookCategoryDto.setBookCategoryName("Technical");
			bookCategoryDto.setDescription("Technology related books");
			bookCategoryService.addBookCategory(bookCategoryDto);
			bookDto.setBookName("JAVA");
			bookDto.setAuthor("JAVA");
			bookDto.setIsbn("1471234567894");
			bookDto.setPublisher("ABC");
			bookDto.setCategory("Technical");
			bookDto.setEdition("1");
			bookDto.setNoOfCopies("2");
			bookService.addBook(bookDto);
			List<Book> list = bookService.findBook(bookDto);
			Long bookPk=(list.get(0).getBookPk());
			List<Long> bookPkList=new ArrayList<>();
			bookPkList.add(bookPk);
			memberBookService.issueBooksForParticularUser(bookPkList, "member");
			issueDto.setUserId("member");
			issueDto.setStatus("'ISSUE_REQUESTED'");
			List<AdminIssue> issueRs=issueService.getIssues(issueDto);
			//String issuePk=new Long(issueRs.get(0).getIssuePk()).toString();
			List<Long> issuePkList=new ArrayList<>();
			issuePkList.add(issueRs.get(0).getIssuePk());
			issueService.acceptIssue(issuePkList);
			issueDto.setUserId("member");
			issueDto.setStatus("'ISSUED'");
			List<AdminIssue> newIssueRs=issueService.getIssues(issueDto);
			newIssueRs=issueService.getIssues(issueDto);
			assertEquals(1,newIssueRs.size());
		}
		@Test
		@Transactional
		public void testValidateIssueQueryDetails() throws Exception {
			issueDto.setIssueId("BLAH");
			issueDto.setUserId("member");
			issueDto.setIsbnValue("156    ");
			issueDto.setFromDateStr("02/02/2016");
			issueDto.setToDateStr("02/02/2016");
			List<String> errors=issueService.validateIssueQueryDetails(issueDto);
			assertEquals(0,errors.size());
			issueDto.setToDateStr("01/02/2016");
			errors=issueService.validateIssueQueryDetails(issueDto);
			assertEquals(1,errors.size());
			issueDto.setIsbnValue("ABC    ");
			errors=issueService.validateIssueQueryDetails(issueDto);
			assertEquals(2,errors.size());
			issueDto.setFromDateStr("123");
			issueDto.setToDateStr("123");
			errors=issueService.validateIssueQueryDetails(issueDto);
			assertEquals(4,errors.size());
		}
		}


