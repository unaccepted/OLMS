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
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.nrifintech.olmsb.AbstractTestCase;
import com.nrifintech.olmsb.domain.Book;
import com.nrifintech.olmsb.domain.BookCategory;
import com.nrifintech.olmsb.domain.User;
import com.nrifintech.olmsb.dto.BookCategoryDto;
import com.nrifintech.olmsb.dto.BookDto;
import com.nrifintech.olmsb.dto.IssueDto;
import com.nrifintech.olmsb.dto.MemberRegistrationDto;
import com.nrifintech.olmsb.dto.UsersDto;

public class UsersServiceImplTestCase extends AbstractTestCase {

	@Autowired
	private UsersService usersService;
	@Autowired
	private BookCategoryService bookCategoryService;
	@Autowired
	private BookService bookService;
	@Autowired
	private MemberBookService memberBookService;
	@Autowired
	private IssueService issueService;
	@Autowired
	private MemberRegistrationService memberRegistrationService;

	@Test
	@Transactional
	public void testGetNewRegistrationRequest() throws Exception {
		MemberRegistrationDto memberRegistrationDto = new MemberRegistrationDto();
		memberRegistrationDto.setUserId("ABCDEFGHIJJKL");
		memberRegistrationDto.setPassword("@Ss123456789");
		memberRegistrationDto.setConfirmPassword("@Ss123456789");
		memberRegistrationDto.setFirstName("SURAJ");
		memberRegistrationDto.setLastName("SAMAR");
		memberRegistrationDto.setEmail("abc@gmail");

		memberRegistrationService.addNewMember(memberRegistrationDto);

		UsersDto usersDto = new UsersDto();
		usersDto.setFirstName("SURAJ");
		usersDto.setLastName("Sam");
		usersDto.setUserId("ABC");
		usersDto.setEmail("@");

		List<User> list = usersService.getNewRegistrationRequest(usersDto);
		assertEquals(1, list.size());
	}
	@Test
	@Transactional
	public void testAddUsers() throws Exception {

		MemberRegistrationDto memberRegistrationDto = new MemberRegistrationDto();
		memberRegistrationDto.setUserId("ABCDEFGHIJJKL");
		memberRegistrationDto.setPassword("@Ss123456789");
		memberRegistrationDto.setConfirmPassword("@Ss123456789");
		memberRegistrationDto.setFirstName("SURAJ");
		memberRegistrationDto.setLastName("SAMAR");
		memberRegistrationDto.setEmail("abc@gmail");

		memberRegistrationService.addNewMember(memberRegistrationDto);

		UsersDto usersDto = new UsersDto();
		usersDto.setFirstName("SURAJ");
		usersDto.setLastName("Sa");
		usersDto.setUserId("ab");
		usersDto.setEmail("bc");

		List<User> list = usersService.getNewRegistrationRequest(usersDto);

		List<Long> userPkList = new ArrayList<>();
		userPkList.add(list.get(0).getUserPk());
		usersService.addUsers(userPkList);

		List<User> list2 = usersService.findExistingUser(usersDto);
		assertEquals(1, list2.size());
	}

	@Test
	@Transactional
	public void testRemoveUsers() throws Exception {
		MemberRegistrationDto memberRegistrationDto = new MemberRegistrationDto();
		memberRegistrationDto.setUserId("ABCDEFGHIJJKL");
		memberRegistrationDto.setPassword("@Ss123456789");
		memberRegistrationDto.setConfirmPassword("@Ss123456789");
		memberRegistrationDto.setFirstName("SURAJ");
		memberRegistrationDto.setLastName("SAMAR");
		memberRegistrationDto.setEmail("abc@gmail");

		memberRegistrationService.addNewMember(memberRegistrationDto);

		UsersDto usersDto = new UsersDto();
		usersDto.setFirstName("SURAJ");
		usersDto.setLastName("amar");
		usersDto.setUserId("def");
		usersDto.setEmail("abc");

		List<User> list = usersService.getNewRegistrationRequest(usersDto);

		BookCategoryDto bookCategoryDto = new BookCategoryDto()  ;
		bookCategoryDto.setBookCategoryName("Technical");
		bookCategoryDto.setDescription("Technology related books");
		
		bookCategoryService.addBookCategory(bookCategoryDto);
		BookDto bookDto = new BookDto();
		bookDto.setBookName("JAVA");
		bookDto.setAuthor("JAVA");
		bookDto.setIsbn("1471234567894");
		bookDto.setPublisher("ABC");
		bookDto.setCategory("Technical");
		bookDto.setEdition("1");
		bookDto.setNoOfCopies("2");
		bookService.addBook(bookDto);
		List<Book> bookList = bookService.findBook(bookDto);
		Long bookPk=(bookList.get(0).getBookPk());
		List<Long> bookPkList=new ArrayList<>();
		bookPkList.add(bookPk);
		memberBookService.issueBooksForParticularUser(bookPkList, "ABCDEFGHIJJKL");
		IssueDto issueDto = new IssueDto();
		issueDto.setUserId("ABCDEFGHIJJKL");
		issueDto.setStatus("'ISSUE_REQUESTED'");
		issueService.getIssues(issueDto);     //
		
		List<Long> userPkList = new ArrayList<>();
		userPkList.add(list.get(0).getUserPk());
		usersService.addUsers(userPkList);
		usersService.removeUsers(userPkList);

		List<User> list2 = usersService.findExistingUser(usersDto);
		assertEquals(0, list2.size());
	}
	
	@Test
	@Transactional
	public void testRejectUsers() throws Exception  {
		
		MemberRegistrationDto memberRegistrationDto = new MemberRegistrationDto();
		memberRegistrationDto.setUserId("ABCDEFGHIJJKL");
		memberRegistrationDto.setPassword("@Ss123456789");
		memberRegistrationDto.setConfirmPassword("@Ss123456789");
		memberRegistrationDto.setFirstName("SURAJ");
		memberRegistrationDto.setLastName("SAMAR");
		memberRegistrationDto.setEmail("abc@gmail");

		memberRegistrationService.addNewMember(memberRegistrationDto);

		UsersDto usersDto = new UsersDto();
		usersDto.setFirstName("SURAJ");
		usersDto.setLastName("am");
		usersDto.setUserId("cde");
		usersDto.setEmail("@");

		List<User> list = usersService.getNewRegistrationRequest(usersDto);
		List<Long> userPkList = new ArrayList<>();
		userPkList.add(list.get(0).getUserPk());
		usersService.rejectUsers(userPkList);


		List<User> list2 = usersService.getNewRegistrationRequest(usersDto);
		assertEquals(0, list2.size());
	}
	
	@Test
	@Transactional
	public void testFindExistingUsers() throws Exception {

		MemberRegistrationDto memberRegistrationDto = new MemberRegistrationDto();
		memberRegistrationDto.setUserId("ABCDEFGHIJJKL");
		memberRegistrationDto.setPassword("@Ss123456789");
		memberRegistrationDto.setConfirmPassword("@Ss123456789");
		memberRegistrationDto.setFirstName("SURAJ");
		memberRegistrationDto.setLastName("SAMAR");
		memberRegistrationDto.setEmail("abc@gmail");

		memberRegistrationService.addNewMember(memberRegistrationDto);

		UsersDto usersDto = new UsersDto();
		usersDto.setFirstName("SURAJ");
		usersDto.setLastName("sam");
		usersDto.setUserId("bcd");
		usersDto.setEmail("gmail");

		List<User> list = usersService.getNewRegistrationRequest(usersDto);

		List<Long> userPkList = new ArrayList<>();
		// long userPk = list.get(0).getUserPk();
		// String userPkconv = Long.toString(userPk);
		// userPkList.add(userPkconv);
		userPkList.add(list.get(0).getUserPk());
		usersService.addUsers(userPkList);

		List<User> list2 = usersService.findExistingUser(usersDto);
		assertEquals(1, list2.size());
	}

}
