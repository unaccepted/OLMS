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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nrifintech.olmsb.domain.BookCategory;
import com.nrifintech.olmsb.dto.BookDto;
import com.nrifintech.olmsb.dto.OLMSModelMap;
import com.nrifintech.olmsb.exception.OLMSException;
import com.nrifintech.olmsb.service.BookService;
/**
 * This is the controller class for book entry
 * @author amanj
 *
 */
@Controller
@RequestMapping(value="/admin/book/entry")
public class BookEntryController {
	
	private final Logger log = LoggerFactory.getLogger(BookQueryController.class);
	@Autowired
	BookService bookService;
	/**
	 * This API is responsible for mapping bookEntry
	 * @param map
	 * @return String view name
	 */
	@RequestMapping(value="/page")
	public String entryBookPage(ModelMap map) {
		map.addAttribute("bookDto", new BookDto());
		List<BookCategory> bookCategories = new ArrayList<>();
		try {
			bookCategories = bookService.fetchAllCategories();
		} catch (OLMSException e) {
			log.error("Unable to fetch categories",e);
			// To do
		}
		map.addAttribute("categoryList", bookCategories);
		return "bookEntry";
	}
	/**
	 * This API is responsible for validating the details
	 * @param bookDto
	 * @return OLMSModelMap
	 */
	@RequestMapping(value="/validate", method = RequestMethod.POST)
	public @ResponseBody OLMSModelMap doValidateEntry(@RequestBody BookDto bookDto) {
		//System.out.println(bookDto.getBookName());
		OLMSModelMap modelMap =new OLMSModelMap();
		modelMap.setErrors(bookService.validateBook(bookDto));
		if(!((modelMap.getErrors()).isEmpty())){
			modelMap.setSuccess(false);
		}
		return modelMap;
	}
	/**
	 * This API is responsible for the fetching the details from book Entry details
	 * @param bookDto
	 * @param map
	 * @return String view name
	 */
	@RequestMapping(value="/submit", method = RequestMethod.POST)
	public String doSubmit(@ModelAttribute BookDto bookDto, ModelMap map) {
		map.addAttribute("data", bookDto);
	//	System.out.println(bookDto.getBookName());
		return "bookEntryConf";
	}
	/**
	 * This API is responsible for rendering back to previous bookEntry Screen
	 * from current Screen that is UserConfirmationPage.
	 * @param bookDto
	 * @return String view name
	 */
	@RequestMapping(value="/back", method = RequestMethod.POST)
	public String doBack(@RequestBody BookDto bookDto) {
		return "bookEntry";
	}
	/**
	 * This API is responsible for fetching the details from bookEntryConF.
	 * @param bookDto
	 * @return OLMSModelMap
	 */
	@RequestMapping(value="/confirm", method = RequestMethod.POST)
	public @ResponseBody OLMSModelMap doConfirm(@RequestBody BookDto bookDto) {
		OLMSModelMap response = new OLMSModelMap();
		try {
			bookService.addBook(bookDto);
			} catch (Exception e) {
				log.error("Unable to add book", e);
				response.setSuccess(false);
				response.setErrors(Arrays.asList(e.getMessage()));
			}
			return response;
	}
}
