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
 * This is the controller class responsible for mapping all the requests sent by the Admin for New Registered user functionality to their corresponding APIs in the service layer
 * @author surajb
 *
 */
@Controller
@RequestMapping(value = "/admin/users/newRegistrationQuery")
public class UsersNewRegistrationQueryController {
	@Autowired
	UsersService usersService;
	private final Logger LOG = LoggerFactory.getLogger(UsersNewRegistrationQueryController.class);
	/**
	 *  This API is responsible for mapping usersNewRegistrationQuery
	 * @param map
	 * @return String view name
	 */
	@RequestMapping(value = "/page")
	public String entryPage(ModelMap map) {
		map.addAttribute("usersDto", new UsersDto());
		return "usersNewRegistrationQuery";
	}
	/**
	 * This API is responsible for fetching the details from New registered Users 
	 * @param usersDto
	 * @param map
	 * @return String view name
	 */
	@RequestMapping(value = "/result", method = RequestMethod.POST)
	public String doQuery(@ModelAttribute UsersDto usersDto, ModelMap map) throws OLMSException{
		OLMSModelMap modelMap = new OLMSModelMap();
		modelMap.setData(usersService.getNewRegistrationRequest(usersDto)); 
		map.addAttribute("data", modelMap.getData());
		map.addAttribute("isSuccess", modelMap.getSuccess());
		map.addAttribute("errors", modelMap.getErrors());
		map.addAttribute("queryparams", usersDto);
		return "usersNewRegistrationQueryResult";
	}
	/**
	 * this API is responsible for Accepting Users requests and thus Adding users  
	 * @param users
	 * @return OLMSModelMap
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public @ResponseBody OLMSModelMap addUsers(@RequestBody List<Long> userPks) {
		OLMSModelMap response = new OLMSModelMap();
		try{
			usersService.addUsers(userPks);
		}catch (OLMSException e) {
			LOG.error("Unable add users", e);
			response.setSuccess(false);
			response.setErrors(Arrays.asList(e.getMessage()));
		}
		return response;
	}
	/**
	 * this API is responsible for Rejecting Users requests 
	 * @param users
	 * @return OLMSModelMap
	 */
	@RequestMapping(value = "/reject", method = RequestMethod.POST)
	public @ResponseBody OLMSModelMap rejectUsers(@RequestBody List<Long> userPks) {
		OLMSModelMap response = new OLMSModelMap();
		try {
			usersService.rejectUsers(userPks);
		} catch(OLMSException e){
			LOG.error("Unable reject user request/s", e);
			response.setSuccess(false);
			response.setErrors(Arrays.asList(e.getMessage()));
		}
		return response;
	}
}
