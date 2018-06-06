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

import com.nrifintech.olmsb.dto.OLMSModelMap;
import com.nrifintech.olmsb.dto.UsersDto;
import com.nrifintech.olmsb.exception.OLMSException;
import com.nrifintech.olmsb.service.UsersService;
/**
 * This is the controller class  responsible for mapping all the requests sent by the Admin for Existing user Functionality to their corresponding APIs in the service layer
 * @author surajb
 *
 */
@Controller
@RequestMapping(value="/admin/users/existingUserQuery")
public class UsersExistingUserQueryController {
	
	@Autowired
	UsersService usersService;				
	
	private final Logger LOG = LoggerFactory.getLogger(UsersExistingUserQueryController.class);
	/**
	 * This API is responsible for mapping usersExistingUserQuery
	 * @param map
	 * @return String view name
	 */
	@RequestMapping(value="/page")
	public String entryPage(ModelMap map) {
		map.addAttribute("usersDto", new UsersDto());
		return "usersExistingUserQuery";
	}
	/**
	 * This API is responsible for fetching the details from Existing Users presenting the result of the query on the result screen
	 * @param usersDto
	 * @param map
	 * @return String view name
	 */
	@RequestMapping(value="/result", method =RequestMethod.POST)
	public String doQuery(@ModelAttribute UsersDto usersDto, ModelMap map) throws OLMSException {
		OLMSModelMap modelMap = new OLMSModelMap();
		modelMap.setData(usersService.findExistingUser(usersDto));
		map.addAttribute("data", modelMap.getData());
		map.addAttribute("isSuccess", modelMap.getSuccess());
		map.addAttribute("errors", modelMap.getErrors());
		map.addAttribute("queryparams", usersDto);
		return "usersExistingUserQueryResult";
	}
	/**
	 * this API is responsible for Removing Existing Users 
	 * @param users
	 * @return OLMSModelMap
	 */
	@RequestMapping(value="/remove", method = RequestMethod.POST)
	public @ResponseBody OLMSModelMap removeUsers(@RequestBody List<Long> userPks) {
		
		OLMSModelMap response = new OLMSModelMap();
		try {
			usersService.removeUsers(userPks);
		} catch (OLMSException e) {
			LOG.error("Unable to remove user data", e);
			response.setSuccess(false);
			response.setErrors(Arrays.asList(e.getMessage()));
		}
		return response;
	}
}
