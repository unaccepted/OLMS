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

import com.nrifintech.olmsb.dto.MemberRegistrationDto;
import com.nrifintech.olmsb.dto.OLMSModelMap;
import com.nrifintech.olmsb.exception.OLMSException;
import com.nrifintech.olmsb.service.MemberRegistrationService;

/**
 * This controller is responsible for mapping all the details filled by the new member to their corresponding APIs in the service layer
 * Inserting operation of details of new member.
 * Validation processing of details of new member.
 * Submitting operation of details of new member.
 * @version $Revision: 1.11 $, $Date: 2016/09/13 13:10:50 $
 * @author Amols
 */
@Controller
public class MemberRegistrationController {
	@Autowired
	MemberRegistrationService memberRegistrationService;
	
	private final Logger LOGGER = LoggerFactory.getLogger(MemberRegistrationController.class);
	/**
	 * This API is responsible to return Home page view.
	 * @return view
	 */
	@RequestMapping(value="/")
	public String homePage() {
		return "index";
	}
	/**
	 * In this memberRegistrationEntry.jsp will be rendered.
	 * This API is responsible to return the view name.
	 * @param map
	 * @return view name
	 */
	@RequestMapping(value="/registration/page")
	public String entryPage(ModelMap map) {
		map.addAttribute("memberRegistrationDto", new MemberRegistrationDto());
		return "memberRegistrationEntry";
	}
	/**
	 * This API is responsible for validating the details filled by new member in the entry screen.
	 * @param memberRegistrationDto
	 * @return OLMSModelMap response object
	 */
	@RequestMapping(value="/registration/validate", method = RequestMethod.POST)
	public @ResponseBody OLMSModelMap doValidateEntry(@RequestBody MemberRegistrationDto memberRegistrationDto) {
		OLMSModelMap modelMap =new OLMSModelMap();
		modelMap.setErrors(memberRegistrationService.validateNewMemberDetails(memberRegistrationDto));
		if(!((modelMap.getErrors()).isEmpty())){
			modelMap.setSuccess(false);
		}
		return modelMap;
	}
	/**
	 * This API is responsible for accepting the validated details from the new member and presenting the details of the new member on the result screen.
	 * @param memberRegistrationDto
	 * @param map
	 * @return view name
	 */
	@RequestMapping(value="/registration/submit", method = RequestMethod.POST)
	public String doSubmit(@ModelAttribute MemberRegistrationDto memberRegistrationDto,ModelMap map) {
		map.addAttribute("data", memberRegistrationDto);
		return "memberRegistrationConf";
	}
	/**
	 * This API is responsible for rendering back to previous memberRegistrationEntry Screen
	 * from current Screen that is UserConfirmationPage.
	 * @param memberRegistrationDto
	 * @return view name
	 */
	@RequestMapping(value="/registration/back", method = RequestMethod.POST)
	public String doBack(@RequestBody MemberRegistrationDto memberRegistrationDto) {
		return "memberRegistrationEntry";
	}
	/**
	 * This API is responsible for following:
	 * fetching the details from memberRegistrationConF.
	 * Return the OLMSModelMap as response body.
	 * @param memberRegistrationDto
	 * @return OLMSModelMap response object
	 */
	@RequestMapping(value="/registration/confirm", method = RequestMethod.POST)
	public @ResponseBody OLMSModelMap doConfirm(@RequestBody MemberRegistrationDto memberRegistrationDto) {
		
		OLMSModelMap response = new OLMSModelMap();
		try {
			 memberRegistrationService.addNewMember(memberRegistrationDto);
		    } catch (OLMSException e) {
			LOGGER.error("Failed to add new user", e);
			response.setSuccess(false);
			response.setErrors(Arrays.asList(e.getMessage()));
		}
		return response;
	}
}