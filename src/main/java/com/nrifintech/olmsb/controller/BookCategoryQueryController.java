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
 * Book Category Query and management functionality 
 * for the admin  
 * @author amols
 *
 */
@Controller
@RequestMapping(value="/admin/bookcategory")
public class BookCategoryQueryController {
	@Autowired
	BookCategoryService bookCategoryService;
	private final Logger LOG = LoggerFactory.getLogger(UsersExistingUserQueryController.class);
	/**
	 * This API is responsible for presenting the query page
	 * @param map
	 * @return view name
	 */
	@RequestMapping(value="/query/page")
	public String queryPage(ModelMap map) {
		map.addAttribute("bookCategoryDto", new BookCategoryDto());
		return "bookCategoryQuery";
	}
	/**
	 * This API is responsible for fetching and displaying a result page against a particular query
	 * @param bookCategoryDto
	 * @param map
	 * @return view name
	 */
	@RequestMapping(value="/query/result", method = RequestMethod.POST)
	public String doQuery(@ModelAttribute BookCategoryDto bookCategoryDto, 
			ModelMap map) throws OLMSException{
		OLMSModelMap modelMap = new OLMSModelMap();
		modelMap.setData(bookCategoryService.findBookCategory(bookCategoryDto));
		map.addAttribute("data", modelMap.getData());
		map.addAttribute("isSuccess", modelMap.getSuccess());
		map.addAttribute("errors", modelMap.getErrors());
		map.addAttribute("queryparams", bookCategoryDto);
		return "bookCategoryQueryResult";
	}
	/**
	 * This API is responsible for removing a book category.
	 * @param bookCategoryPks
	 * @return OLMSModelMap response object
	 */
	@RequestMapping(value="/remove", method = RequestMethod.POST)
	public @ResponseBody OLMSModelMap removeCategory(@RequestBody List<Long> bookCategoryPks) {
		
		OLMSModelMap response = new OLMSModelMap();
		try {
			bookCategoryService.removeBookCategory(bookCategoryPks);
		} catch (OLMSException e) {
			LOG.error("Unable to remove Book Category data", e);
			response.setSuccess(false);
			response.setErrors(Arrays.asList(e.getMessage()));
		}
		return response;
	}
}
