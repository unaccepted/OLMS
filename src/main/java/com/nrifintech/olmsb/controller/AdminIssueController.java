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
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.nrifintech.olmsb.dto.IssueDto;
import com.nrifintech.olmsb.dto.OLMSModelMap;
import com.nrifintech.olmsb.exception.OLMSException;
import com.nrifintech.olmsb.service.IssueService;
/**
 * This controller{IssueReturnController.Class} is responsible for the following :
 * </br>
 * <li> Validate the provided query criteria for issue returned query<li>
 * <li> Fetch the data for issue returned query<li> 
 * <li> Accept Issue/Return requests<li> 
 * @author jayabratas
 */
@Controller
@RequestMapping(value = "/admin/issue/query")
public class AdminIssueController {
	@Autowired
	IssueService issueService;
	/**
	 * This API is responsible for returning the query page
	 */
	@RequestMapping(value = "/page")
	protected String getQueryPage(ModelMap map) {
		map.addAttribute("issueDto", new IssueDto());
		return "issueQuery";
	}
	/**  
	 * This API is responsible for the following :
	 * </br>
	 * <li>Receive the query criteria from ajax call <li>
	 * <li>Validate the provided query criteria<li> 
	 * <li>Return the OLMSModelMap as response body<li> 
	 * @throws OLMSException 
	 */
	@RequestMapping(value="/validate", method = RequestMethod.POST)
	public @ResponseBody OLMSModelMap doValidateEntry(@RequestBody IssueDto issueDto) throws OLMSException { 
		OLMSModelMap modelMap =new OLMSModelMap();
		modelMap.setErrors(issueService.validateIssueQueryDetails(issueDto));
		if(!((modelMap.getErrors()).isEmpty()))
		{
			modelMap.setSuccess(false);
		}
		return modelMap;
	}
	/**
	 * This API is responsible for the following :
	 * </br>
	 * <li>Fetch the data against the provided query criteria<li>
	 * <li>Return the Result Screen<li> 
	 * @throws OLMSException 
	 */
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public String doQuery(@ModelAttribute IssueDto issueDto, ModelMap map) throws OLMSException {
		OLMSModelMap modelMap = new OLMSModelMap();
		modelMap.setData(issueService.getIssues(issueDto));
		map.addAttribute("data", modelMap.getData());
		map.addAttribute("isSuccess", modelMap.getSuccess());
		map.addAttribute("errors", modelMap.getErrors());
		map.addAttribute("queryparams", issueDto);
		return "issueResult";
	}
	/**
	 * This API is responsible for accepting Issue/Return requests from the user
	 * @param List<Long> issues
	 * @return OLMSModelMap as response body
	 * @throws OLMSException 
	 */
	@RequestMapping(value = "/accept", method = RequestMethod.POST)
	public @ResponseBody OLMSModelMap doAccept(@RequestBody List<Long> issues) throws OLMSException
	{
		OLMSModelMap response = new OLMSModelMap();
		response.setErrors(issueService.acceptIssue(issues));
		if(!((response.getErrors()).isEmpty()))
		{
			response.setSuccess(false);
		}
		return response;
	}
}
