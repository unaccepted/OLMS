package com.nrifintech.olmsb.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.internal.runners.statements.RunBefores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.nrifintech.olmsb.AbstractTestCase;
import com.nrifintech.olmsb.domain.Book;
import com.nrifintech.olmsb.domain.BookCategory;
import com.nrifintech.olmsb.domain.BookInventory;
import com.nrifintech.olmsb.dto.BookCategoryDto;
import com.nrifintech.olmsb.dto.BookDto;
import com.nrifintech.olmsb.dto.BookInventoryDto;

public class BookServiceImplTestCase extends AbstractTestCase {

	@Autowired
	private BookService bookService;

	@Autowired
	private BookCategoryService bookCategoryService;
	
	
	@Test
	@Transactional
	public void testValidate() throws Exception {
		BookCategoryDto bookCategoryDto = new BookCategoryDto();
		bookCategoryDto.setBookCategoryName("Technical");
		bookCategoryDto.setDescription("sfdsa");
		bookCategoryService.addBookCategory(bookCategoryDto);
		BookDto bookDto = new BookDto();
		bookDto.setBookName(" ");
		bookDto.setAuthor(" ");
		bookDto.setIsbn(" ");
		bookDto.setPublisher(" ");
		bookDto.setCategory(" ");
		bookDto.setEdition(" ");
		bookDto.setNoOfCopies(" ");
		
		List<String> err = bookService.validateBook(bookDto);
		assertEquals(6, err.size());
		
		bookDto = new BookDto();
		bookDto.setBookName("jsakgfjksgfjsdgfbjkdgf,sdgfbksdgfjkf ");
		bookDto.setAuthor("dfgdsfgdfgvsdfvsdfbsdfvbsdfvbsdf ");
		bookDto.setIsbn("512345689142334sdfsd");
		bookDto.setPublisher(" gfbhdfghdfgdfghdfghfdghdfghfdh");
		bookDto.setCategory("ghdfghfdghfdghfdghfd ");
		bookDto.setEdition("4ghdfghdfgdfghdfgdgfhgh");
		bookDto.setNoOfCopies("44fgfdggghdfghfdghfdgdfghgh ");
		
		 err = bookService.validateBook(bookDto);
		assertEquals(8, err.size());
		
		bookDto = new BookDto();
		bookDto.setBookName("JAVA");
		bookDto.setAuthor("QQ");
		bookDto.setIsbn("1471234567894");
		bookDto.setPublisher("ABC");
		bookDto.setCategory("Technical");
		bookDto.setEdition("1");
		bookDto.setNoOfCopies("2");
		bookService.addBook(bookDto);
		
		bookDto = new BookDto();
		bookDto.setBookName("JAVA");
		bookDto.setAuthor("QQ");
		bookDto.setIsbn("1471234567894");
		bookDto.setPublisher("ABC");
		bookDto.setCategory("Technical");
		bookDto.setEdition("1");
		bookDto.setNoOfCopies("2");

	 err = bookService.validateBook(bookDto);
		assertEquals(1, err.size());
		//Assert.assertFalse(0, err.size());
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
		
		String isbn = bookDto.getIsbn();
		List<String> err = bookService.validateIsbn(isbn);
		assertEquals(0, err.size());
		
		bookDto = new BookDto();
		bookDto.setBookName("");
		bookDto.setAuthor("");
		bookDto.setIsbn("asdsdf1234546441331");
		bookDto.setPublisher("");
		bookDto.setCategory("");
		bookDto.setEdition("");
		bookDto.setNoOfCopies("");
		isbn= bookDto.getIsbn();
		err = bookService.validateIsbn(isbn);
		assertEquals(1, err.size());
	}
	
	@Test
	@Transactional
	public void testAddNewBook() throws Exception {
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
		
		List<Book> list = bookService.findBook(bookDto);
		
		assertEquals(1, list.size());
	}
	
	@Test 
	@Transactional
	public void testFetchAllCategories() throws Exception {
		BookCategoryDto bookCategoryDto = new BookCategoryDto();
		bookCategoryDto.setBookCategoryName("Technical");
		bookCategoryDto.setDescription("sfdsa");
		bookCategoryService.addBookCategory(bookCategoryDto);
		List<BookCategory> bookCategory = bookService.fetchAllCategories();
		assertEquals(1, bookCategory.size());
	}
	
	@Test
	@Transactional
	public void testFindNewBook() throws Exception {
		BookCategoryDto bookCategoryDto = new BookCategoryDto();
		bookCategoryDto.setBookCategoryName("Technical");
		bookCategoryDto.setDescription("sfdsa");
		bookCategoryService.addBookCategory(bookCategoryDto);

		BookDto bookDto = new BookDto();
		bookDto.setBookName("JAVA");
		bookDto.setAuthor("QQ");
		bookDto.setIsbn("1234567891478");
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
		List<Book> list= bookService.findBook(bookDto);
		assertEquals(1, list.size());
	}
	
	@Test
	@Transactional
	public void testDeleteBooks() throws Exception {
		BookCategoryDto bookCategoryDto = new BookCategoryDto();
		bookCategoryDto.setBookCategoryName("Technical");
		bookCategoryDto.setDescription("sfdsa");
		bookCategoryService.addBookCategory(bookCategoryDto);

		BookDto bookDto = new BookDto();
		bookDto.setBookName("JAVA");
		bookDto.setAuthor("QQ");
		bookDto.setIsbn("1234567891478");
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
		List<Book> list=bookService.findBook(bookDto);
		long bookSize=list.size();
		
		List<Long> bookPks= new ArrayList<>();
		bookPks.add(list.get(0).getBookPk());
		bookService.deleteBooks(bookPks);
		bookDto.setAuthor("Q");
		list=bookService.findBook(bookDto);
		
		assertEquals(0, list.size());
	}
	
	@Test
	@Transactional
	public void testGetBookInventory() throws Exception {
		BookCategoryDto bookCategoryDto = new BookCategoryDto();
		bookCategoryDto.setBookCategoryName("Technical");
		bookCategoryDto.setDescription("sfdsa");
		bookCategoryService.addBookCategory(bookCategoryDto);

		BookDto bookDto = new BookDto();
		bookDto.setBookName("JAVA");
		bookDto.setAuthor("QQ");
		bookDto.setIsbn("1234567891478");
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
		List<Book> list=bookService.findBook(bookDto);
		
		String isbn=Long.toString(list.get(0).getIsbn());
		List<BookInventoryDto> inventoryList = bookService.getBookInventory(isbn);
		assertEquals(2, inventoryList.size());
	}
	
	@Test
	@Transactional
	public void testDeleteBookCopies() throws Exception{
		BookCategoryDto bookCategoryDto = new BookCategoryDto();
		bookCategoryDto.setBookCategoryName("Technical");
		bookCategoryDto.setDescription("sfdsa");
		bookCategoryService.addBookCategory(bookCategoryDto);

		BookDto bookDto = new BookDto();
		bookDto.setBookName("JAVA");
		bookDto.setAuthor("QQ");
		bookDto.setIsbn("1234567891478");
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
		List<Book> list=bookService.findBook(bookDto);
		
		
		String isbn=Long.toString(list.get(0).getIsbn());
		List<BookInventoryDto> inventoryList = bookService.getBookInventory(isbn);
		
		assertEquals(2, inventoryList.size());
		
		List<Long> inventoryPks = new ArrayList<>();
		inventoryPks.add(inventoryList.get(0).getBookInventoryPk());
		
		bookService.deleteBookCopies(inventoryPks);
		inventoryList = bookService.getBookInventory(isbn);
		assertEquals(1, inventoryList.size());
	}
	
	@Test
	@Transactional
	public void testAddBookCopies() throws Exception {
		BookCategoryDto bookCategoryDto = new BookCategoryDto();
		bookCategoryDto.setBookCategoryName("Technical");
		bookCategoryDto.setDescription("sfdsa");
		bookCategoryService.addBookCategory(bookCategoryDto);

		BookDto bookDto = new BookDto();
		bookDto.setBookName("JAVA");
		bookDto.setAuthor("QQ");
		bookDto.setIsbn("1234567891478");
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
		List<Book> list=bookService.findBook(bookDto);
		
		bookDto = new BookDto();
		bookDto.setBookName("");
		bookDto.setAuthor("");
		bookDto.setIsbn("1234567891478");
		bookDto.setPublisher("");
		bookDto.setCategory("");
		bookDto.setEdition("");
		bookDto.setNoOfCopies("10");
		String isbn=Long.toString(list.get(0).getIsbn());
		List<BookInventoryDto> inventoryList = bookService.getBookInventory(isbn);

		bookService.addBookCopies(bookDto);
		inventoryList = bookService.getBookInventory(isbn);
		assertEquals(12, inventoryList.size());
	}
}
