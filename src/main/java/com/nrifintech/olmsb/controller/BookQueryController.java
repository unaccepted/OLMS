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
package com.nrifintech.olmsb.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nrifintech.olmsb.domain.Book;
import com.nrifintech.olmsb.domain.BookCategory;
import com.nrifintech.olmsb.dto.BookDto;
import com.nrifintech.olmsb.dto.BookInventoryDto;
import com.nrifintech.olmsb.dto.OLMSModelMap;
import com.nrifintech.olmsb.exception.OLMSException;
import com.nrifintech.olmsb.service.BookService;

@Controller
@RequestMapping(value="/admin/book")
public class BookQueryController {
	/**
	 * This  is the controller class for book query
	 * @author amanj
	 *
	 */
	private final Logger log = LoggerFactory.getLogger(BookQueryController.class);
	@Autowired
	BookService bookService;
	/**
	 * This API is responsible for mapping bookQuery
	 * @param map
	 * @return String view name
	 */
	@RequestMapping(value="/query/page")
	public String queryBookPage(ModelMap map) {
		map.addAttribute("bookDto", new BookDto());
		List<BookCategory> bookCategories = new ArrayList<>();
		try {
			bookCategories = bookService.fetchAllCategories();
		} catch (OLMSException e) {
			log.error("Unable to fetch categories",e);
			// To do
		}
		map.addAttribute("categoryList", bookCategories);
		return "bookQuery";
	}
	
	/**
	 * This API is responsible for validation of book details
	 * @param bookDto
	 * @return OlmsModelMsp
	 */
	@RequestMapping(value="/query/validate", method = RequestMethod.POST)
	public @ResponseBody OLMSModelMap doValidateEntry(@RequestBody BookDto bookDto) {
		OLMSModelMap modelMap =new OLMSModelMap();
		modelMap.setErrors(bookService.validateIsbn(bookDto.getIsbn()));
		if(!((modelMap.getErrors()).isEmpty())){
			modelMap.setSuccess(false);
		}
		return modelMap;
	}
	/**
	 *  This API is responsible for following:
	 * displaying the details according to the entered values in bookQuery screen.
	 * @param bookDto
	 * @param map
	 * @return String view name
	 */
	@RequestMapping(value="/query/result", method = RequestMethod.POST)
	public String doQuery(@ModelAttribute BookDto bookDto, 
			ModelMap map) {
		OLMSModelMap modelMap = new OLMSModelMap();
		try {
			modelMap.setData( bookService.findBook(bookDto));
			map.addAttribute("data", modelMap.getData());
			map.addAttribute("queryparams", bookDto);
		} catch (Exception e) {
			log.error("Unable to fetch book ", e);
			modelMap.setSuccess(false);
			modelMap.setErrors(Arrays.asList(e.getMessage()));
			map.addAttribute("isSuccess", modelMap.getSuccess());
			map.addAttribute("errors", modelMap.getErrors());
		}
		return "bookQueryResult";
	}
	/**
	 * This API is responsible for deleting the books
	 * @param books
	 * @return OLMSModelMap
	 */
	@RequestMapping(value="/query/delete", method = RequestMethod.POST)
	public @ResponseBody OLMSModelMap deleteBooks(@RequestBody List<Long> bookPks) {
		OLMSModelMap response = new OLMSModelMap();
		try {
			bookService.deleteBooks(bookPks);
		} catch (Exception e) {
			log.error("Unable to remove book", e);
			response.setSuccess(false);
			response.setErrors(Arrays.asList(e.getMessage()));
		}
		return response;
	}
	/**
	 * This API is responsible for fetching the details of book inventories
	 * @param isbn
	 * @return OLMSModelMap
	 */
	@RequestMapping(value="/inventory/query", method = RequestMethod.POST)
	public @ResponseBody OLMSModelMap getBookInventory(@RequestBody String isbn) {
		OLMSModelMap response = new OLMSModelMap();
		try {
			List<BookInventoryDto> bookCopies = bookService.getBookInventory(isbn);
			response.setData(bookCopies);
		} catch (Exception e) {
			log.error("Unable to fetch book inventory for isbn {}", isbn, e);
			response.setSuccess(false);
			response.setErrors(Arrays.asList(e.getMessage()));
		}
		return response;
	}
	/**
	 * This API is responsible for deleting the book copies from book inventory
	 * @param bookCopy
	 * @return OLMSModelMap
	 */
	@RequestMapping(value="/inventory/query/delete", method = RequestMethod.POST)
	public @ResponseBody OLMSModelMap deleteBookCopies(@RequestBody List<Long> bookInventoryPks) {
		OLMSModelMap response = new OLMSModelMap();
		try {
			bookService.deleteBookCopies(bookInventoryPks);
		} catch (OLMSException e) {
			log.error("Unable to remove book copies", e);
			response.setSuccess(false);
			response.setErrors(Arrays.asList(e.getMessage()));
		}
		return response;
	}
	/**
	 * This API is responsible for adding the book copies in book inventory
	 * @param bookNo
	 * @return OLMSModelMap
	 */
	@RequestMapping(value="/inventory/query/add", method = RequestMethod.POST)
	public @ResponseBody OLMSModelMap addBookCopies(@RequestBody BookDto bookDto) { 
		OLMSModelMap response = new OLMSModelMap();
		try {
				bookService.addBookCopies(bookDto);
			} catch (Exception e) {
				log.error("Unable to add book copies", e);
				response.setSuccess(false);
				response.setErrors(Arrays.asList(e.getMessage()));
			}
			return response;
	}
	
	@RequestMapping(value="/query/amend/{isbn}", method = RequestMethod.GET)
	public String deleteBooks(@PathVariable("isbn") String isbn , ModelMap map) {
		OLMSModelMap modelMap = new OLMSModelMap();
		List<BookCategory> bookCategories = new ArrayList<>();
		BookDto bookDto =new BookDto();
		bookDto.setBookName("");
		bookDto.setAuthor("");
		bookDto.setIsbn(isbn);
		bookDto.setPublisher("");
		bookDto.setCategory("");
		bookDto.setEdition("");
		bookDto.setNoOfCopies("");
	
		//System.out.println(isbn);
		try {
			bookCategories = bookService.fetchAllCategories();
			List<Book> list = bookService.findBook(bookDto);
			bookDto.setBookPk(list.get(0).getBookPk());
			bookDto.setBookName(list.get(0).getBookName());
			bookDto.setAuthor(list.get(0).getAuthor());
			bookDto.setEdition(Long.toString(list.get(0).getEdition()));
			bookDto.setIsbn(Long.toString(list.get(0).getIsbn()));
			bookDto.setPublisher(list.get(0).getPublisher());
			bookDto.setNoOfCopies(Long.toString(list.get(0).getNoOfBook()));
			map.addAttribute("bookDto", bookDto);
			map.addAttribute("queryparams", bookDto);
		}
			catch (Exception e) {
			log.error("Unable to fetch book ", e);
			modelMap.setSuccess(false);
			modelMap.setErrors(Arrays.asList(e.getMessage()));
			map.addAttribute("isSuccess", modelMap.getSuccess());
			map.addAttribute("errors", modelMap.getErrors());
		}
		return "bookAmend";
	}
	
	@RequestMapping(value="/query/amend/submit", method = RequestMethod.POST)
	public String doSubmit(@ModelAttribute BookDto bookDto, ModelMap map) {
		map.addAttribute("data", bookDto);
	//	System.out.println(bookDto.getBookName());
		return "bookEntryConf";
	}
}
