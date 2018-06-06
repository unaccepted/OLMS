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
import com.nrifintech.olmsb.domain.MemberBook;
import com.nrifintech.olmsb.dto.BookCategoryDto;
import com.nrifintech.olmsb.dto.BookDto;
import com.nrifintech.olmsb.dto.IssueDto;

public class MemberBookServiceImplTestCase extends AbstractTestCase {

	@Autowired
	private MemberBookService memberBookService;

	@Autowired
	private BookService bookService;
	
	@Autowired
	private BookCategoryService bookCategoryService;
	
	@Autowired
	private IssueService issueService;
	
	@Test
	@Transactional
	public void testFindBook() throws Exception {
		BookCategoryDto bookCategoryDto = new BookCategoryDto();
		bookCategoryDto.setBookCategoryName("Technical");
		bookCategoryDto.setDescription("sfdsa");
		bookCategoryService.addBookCategory(bookCategoryDto);

		BookDto bookDto = new BookDto();
		bookDto.setBookName("JAVA");
		bookDto.setAuthor("QQ");
		bookDto.setIsbn("1471234567894");
		bookDto.setPublisher("ABC");
		bookDto.setCategory("Technical");
		bookDto.setEdition("1");
		bookDto.setNoOfCopies("2");
		bookService.addBook(bookDto);
		
		bookDto = new BookDto();
		bookDto.setBookName("");
		bookDto.setAuthor("");
		bookDto.setIsbn("");
		bookDto.setPublisher("");
		bookDto.setCategory("");
		bookDto.setEdition("");
		bookDto.setNoOfCopies("");
		
		List<MemberBook> list = memberBookService.findBook(bookDto);
		
		assertEquals(1, list.size());
	}
	
	@Test
	@Transactional
	public void testValidateIsbn() throws Exception {
		BookDto bookDto = new BookDto();
		bookDto.setBookName("");
		bookDto.setAuthor("");
		bookDto.setIsbn("1");
		bookDto.setPublisher("");
		bookDto.setCategory("");
		bookDto.setEdition("");
		bookDto.setNoOfCopies("");
		List<String> err = memberBookService.validate(bookDto);
		assertEquals(0, err.size());
	}
	
	@Test
	@Transactional
	public void testIssueBooksForParticularUser() throws Exception {
		BookCategoryDto bookCategoryDto = new BookCategoryDto();
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
		
		List<Book> list = bookService.findBook(bookDto);
		Long bookPk=(list.get(0).getBookPk());
		List<Long> bookPkList=new ArrayList<>();
		bookPkList.add(bookPk);
		
		memberBookService.issueBooksForParticularUser(bookPkList, "member");
		IssueDto issueDto= new IssueDto();
		issueDto.setUserId("member");
		issueDto.setStatus("'ISSUE_REQUESTED'");
		List<AdminIssue> issueRs=issueService.getIssues(issueDto);
		assertEquals(1,issueRs.size());
	}
}