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

import com.nrifintech.olmsb.domain.BookCategory;
import com.nrifintech.olmsb.dto.IssueDto;
import com.nrifintech.olmsb.dto.OLMSModelMap;
import com.nrifintech.olmsb.exception.OLMSException;
import com.nrifintech.olmsb.service.BookService;
import com.nrifintech.olmsb.service.MemberIssueService;
/**
 * This controller is responsible for mapping all the requests sent by the user to their corresponding APIs in the service layer
 * <li>Validate the query details for Issue Query<li>
 * <li>Display all corresponding requests for a particular user<li>
 * @author jayabratas
 *
 */
@Controller
@RequestMapping(value = "/member/issue/query")
public class MemberIssueQueryController {
	
	private final Logger log = LoggerFactory.getLogger(MemberIssueQueryController.class);

	@Autowired
	MemberIssueService memberIssueService;
	@Autowired
	BookService bookService;
	/**
	 * This API is responsible to return the view name
	 */
	@RequestMapping(value = "/page")
	protected String getQueryPage(ModelMap map) {
		map.addAttribute("issueDto", new IssueDto());
		List<BookCategory> bookCategories = new ArrayList<>();
		try {
			bookCategories = bookService.fetchAllCategories();
		} catch (OLMSException e) {
			log.error("Unable to fetch categories",e);
			// To do
		}
		map.addAttribute("categoryList", bookCategories);
		return "memberIssueQuery";
	}
	/**
	 * This API is responsible for validating the data inserted by the user in the query screen
	 * @param issueDto
	 * @return OLMSModelMap response object
	 * @throws Throwable
	 */
	@RequestMapping(value="/validate", method = RequestMethod.POST)
	public @ResponseBody OLMSModelMap doValidateEntry(@RequestBody IssueDto issueDto) { 	
		OLMSModelMap response=new OLMSModelMap();
		response.setErrors(memberIssueService.validateIssueQueryDetails(issueDto));
		if (!((response.getErrors()).isEmpty())) {
			response.setSuccess(false);
		}
		return response;
	}
	/**
	 * This API is responsible for accepting the validated data from the user and presenting the result of the query on the result screen
	 * @param IssueDto issueDto
	 * @param ModelMap map
	 * @param HttpSession session
	 * @return view name
	 * @throws OLMSException
	 */
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public String doQuery(@ModelAttribute IssueDto issueDto, ModelMap map, HttpSession session){ 
		issueDto.setUserId(session.getAttribute("userID").toString());
		OLMSModelMap modelMap = new OLMSModelMap();
		try{
		modelMap.setData(memberIssueService.getIssues(issueDto));
		   }
		catch (OLMSException e) {
			modelMap.setSuccess(false);
			modelMap.setErrors(Arrays.asList(e.getMessage()));
		}
		map.addAttribute("data", modelMap.getData());
		map.addAttribute("isSuccess", modelMap.getSuccess());
		map.addAttribute("errors", modelMap.getErrors());
		map.addAttribute("queryparams", issueDto);
		return "memberIssueQueryResult";
	}
}
