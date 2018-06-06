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

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.nrifintech.olmsb.Constants;

/**
 * This controller is responsible for redirecting any user to their
 * corresponding dashboard
 * 
 * @author jayabratas
 *
 */
@Controller
@SessionAttributes("userID")
public class HomeController {
	/**
	 * This API is responsible for fetching the userID for a particular session
	 * @param request
	 * @return String
	 */
	@ModelAttribute("userID")
	public String getUserID(HttpServletRequest request) {
		String userID = request.getUserPrincipal().getName();
		return userID;
	}
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView homePage(HttpServletRequest request) {
		if(request.isUserInRole(Constants.ADMIN)) {
			return new ModelAndView("adminHome");
		} else if (request.isUserInRole(Constants.MEMBER)) {
			return new ModelAndView("memberHome");
		}
		return new ModelAndView("loginerror");
	}
}
