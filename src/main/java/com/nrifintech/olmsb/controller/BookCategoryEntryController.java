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

import com.nrifintech.olmsb.dto.BookCategoryDto;
import com.nrifintech.olmsb.dto.OLMSModelMap;
import com.nrifintech.olmsb.exception.OLMSException;
import com.nrifintech.olmsb.service.BookCategoryService;

/**
 * This Controller is responsible for mapping all requests for 
 * Book Category Entry and management functionality.
 * for the admin  
 * @author amols
 *
 */
@Controller
@RequestMapping(value="/admin/bookcategory/entry")
public class BookCategoryEntryController {
	
	private final Logger LOG = LoggerFactory.getLogger(UsersNewRegistrationQueryController.class);
	@Autowired
	BookCategoryService bookCategoryService;
	/**
	 * This API is responsible to return the view name.
	 * @param map
	 * @return view name
	 */
	@RequestMapping(value="/page")
	public String entryPage(ModelMap map) {
		map.addAttribute("bookCategoryDto", new BookCategoryDto());
		return "bookCategoryEntry";
	}
	/**
	 * This API is responsible for validating the data inserted by the Admin in the Entry screen
	 * @param  bookCategoryDto
	 * @return OLMSModelMap response object
	 */
	@RequestMapping(value="/validate", method = RequestMethod.POST)
	public @ResponseBody OLMSModelMap doValidateEntry(@RequestBody BookCategoryDto bookCategoryDto) throws OLMSException {
		OLMSModelMap modelMap =new OLMSModelMap();
		List<String> errors = bookCategoryService.validateBookCategory(bookCategoryDto);
		if(!errors.isEmpty())
		{
			modelMap.setErrors(errors);
			modelMap.setSuccess(false);
		}
		return modelMap;
	}
	/**
	 * This API is responsible for accepting the validated data from the Admin and presenting the result of the Entry on the result screen
	 * @param bookCategoryDto
	 * @param map
	 * @return view name
	 */
	@RequestMapping(value="/submit", method = RequestMethod.POST)
	public String doSubmit(@ModelAttribute BookCategoryDto bookCategoryDto, ModelMap map) {
		map.addAttribute("data", bookCategoryDto);
		return "bookCategoryConf";
	}
	/**
	 * This API is responsible for rendering back to previous book category Entry Screen
	 * from current Screen that is UserConfirmationPage.
	 * @param bookCategoryDto
	 * @return view
	 */
	@RequestMapping(value="/back", method = RequestMethod.POST)
	public String doBack(@RequestBody BookCategoryDto bookCategoryDto) {
		return "bookCategoryEntry";
	}
	/**
	 * This API is responsible for following:
	 * fetching the details from bookCategoryEntryConF.
	 * @param bookCategoryDto
	 * @return OLMSModelMap
	 */
	@RequestMapping(value="/confirm", method = RequestMethod.POST)
	public @ResponseBody OLMSModelMap doConfirm(@RequestBody BookCategoryDto bookCategoryDto) {
		OLMSModelMap response = new OLMSModelMap();
		try {
			 bookCategoryService.addBookCategory(bookCategoryDto);
		} catch (Exception e) {
			LOG.error("Unable add book category", e);
			response.setSuccess(false);
			response.setErrors(Arrays.asList(e.getMessage()));
		}
		return response;
	}
}
