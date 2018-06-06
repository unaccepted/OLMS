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
import javax.servlet.http.HttpSession;
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
import com.nrifintech.olmsb.dto.BookDto;
import com.nrifintech.olmsb.dto.OLMSModelMap;
import com.nrifintech.olmsb.exception.OLMSException;
import com.nrifintech.olmsb.service.MemberBookService;
/**
 * This controller is responsible for following:
 * Mapping all requests of member book search functionality to their corresponding API in the service layer.
 * Mapping all requests of member book issue request functionality to their corresponding API in the service layer.
 * @author amols
 *
 */
@Controller
@RequestMapping(value="/member/books/query")
public class MemberBookQueryController {
	@Autowired
	MemberBookService memberBookService;
	
	private final Logger LOGGER = LoggerFactory.getLogger(MemberBookQueryController.class);
	
	@RequestMapping(value="/page")
	public String queryPage(ModelMap map) {
		map.addAttribute("bookDto", new BookDto());
		return "memberBookQuery";
	}
	/**
	 * This API is responsible for mapping a query request and fetching a result set
	 * @param BookDto bookDto
	 * @param map
	 * @return view name
	 */
	@RequestMapping(value="/result", method = RequestMethod.POST)
	public String doQuery(@ModelAttribute BookDto bookDto, 
			ModelMap map) {
		OLMSModelMap modelMap = new OLMSModelMap();
		try{
		modelMap.setData(memberBookService.findBook(bookDto));
		}
		catch(OLMSException e) {
			LOGGER.error("Unable to fetch book", e);
			modelMap.setSuccess(false);
		}
		map.addAttribute("data", modelMap.getData());
		map.addAttribute("isSuccess", modelMap.getSuccess());
		map.addAttribute("errors", modelMap.getErrors());
		map.addAttribute("queryparams", bookDto);
		return "memberBookQueryResult";
	}
	/**
	 * This API is responsible for validation of book ISBN.
	 * @param BookDto bookDto
	 * @return OLMSModelMap response object
	 */
	@RequestMapping(value="/validate", method = RequestMethod.POST)
	public @ResponseBody OLMSModelMap doValidate(@RequestBody BookDto bookDto) {
		OLMSModelMap response=new OLMSModelMap();
		List<String> errors = memberBookService.validate(bookDto);
		if(!errors.isEmpty()) {
			response.setSuccess(false);
			response.setErrors(errors);
		}
		return response;
	}
	/**
	 * This API is responsible for making issue request against Existing books. 
	 * @param List<Long> bookPks
	 * @param session
	 * @return OLMSModelMap response object
	 */
	@RequestMapping(value="/issue", method = RequestMethod.POST)
	public @ResponseBody OLMSModelMap issueBooks(@RequestBody List<Long> bookPks, HttpSession session) {
		OLMSModelMap response = new OLMSModelMap();
		try
		{
			memberBookService.issueBooksForParticularUser(bookPks, session.getAttribute("userID").toString());
		} catch (OLMSException e) {
			LOGGER.error("Failed to process the issue book request.", e);
			response.setSuccess(false);
			response.setErrors(Arrays.asList(e.getMessage()));
		}
		return response;
	}
}