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
package com.nrifintech.olmsb.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nrifintech.olmsb.dao.MemberRegistrationDao;
import com.nrifintech.olmsb.dto.MemberRegistrationDto;
import com.nrifintech.olmsb.exception.OLMSException;

@Service
public class MemberRegistrationServiceImpl implements MemberRegistrationService {
	
	@Autowired
	private MemberRegistrationDao memberRegistrationDao;
	
	private final Logger log = LoggerFactory.getLogger(MemberRegistrationServiceImpl.class);
	private static final String USERID_REGEX ="^([a-zA-Z0-9]){5,20}$";
	private static final String CHARACTER_REGEX ="^[a-zA-Z]+$";
	private static final String CONTACT_NO_REGEX = "\\d{10}";
	private static final String EMAIL_PATTERN ="^[a-z][_A-Za-z0-9-\\+]*(\\.[_A-Za-z0-9-]+)*@"
											+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	@Override
	public  List<String> validateNewMemberDetails(MemberRegistrationDto memberRegistrationDto) {
		List<String> errors = new ArrayList<>();
		
	
		String userId = memberRegistrationDto.getUserId();
		// Check whether the User Id is blank or not.
		if(StringUtils.isBlank(userId)) {
			log.error("User_id Can not be empty.");
			errors.add("User_id Can not be empty.");
		} else {
			// User Id pattern and length check.
			if(!userId.matches(USERID_REGEX)) {
				log.error("User Id should contain Minimum 5 and Maximum 20 alpha numeric characters");
				errors.add("User Id should contain Minimum 5 and Maximum 20 alpha numeric characters");
			}
			try {
				// check whether the user_id exist or not.
				if(memberRegistrationDao.isUserExist(userId)) {
					log.error("Provided User Id already exists.");
					errors.add("Provided User Id already exists.");
				}
			} catch (OLMSException e) {
				log.error("Failed to validate the User Id. Please try again.", e);
				errors.add("Failed to validate the User Id. Please try again.");
			}
		}
		// Check whether the password is blank or not.
		if(StringUtils.isBlank(memberRegistrationDto.getPassword())){
			log.error("password should not be blank.");
			errors.add("password should not be blank.");
		} else {
			//check of password and confirm-password matching or not.
			if(!StringUtils.equals(memberRegistrationDto.getPassword(), memberRegistrationDto.getConfirmPassword())) {
				log.error("Provided Password does not matched");
				errors.add("Provided Password does not matched");
			}
			
			if(memberRegistrationDto.getPassword().length()>20){
				log.error("Password cannot be more than 20 characters");
				errors.add("Password cannot be more than 20 characters");
			}
				
		}
		// Check whether the First name is blank or not.
		if(StringUtils.isBlank(memberRegistrationDto.getFirstName())){
			log.error("First Name cannot be empty.");
			errors.add("First Name cannot be empty.");
		} else {
			// First name pattern check.
			if(!memberRegistrationDto.getFirstName().matches(CHARACTER_REGEX)) {
				log.error("First Name should contain only characters");
				errors.add("First Name should contain only characters");
			}
			// First name length check.
			if(memberRegistrationDto.getFirstName().length() >20) {
				log.error("Maximum length of First Name can be 20");
				errors.add("Maximum length of First Name can be 20");
			}
		}
		// Check whether the last name is blank or not.
		if(StringUtils.isNotBlank(memberRegistrationDto.getLastName())) {
			// Last name length check.
			if(memberRegistrationDto.getLastName().length() >20) {
				log.error("Maximum length of Last Name can be 20");
				errors.add("Maximum length of First Name can be 20");
			} 
			// last name pattern check.
			if(!memberRegistrationDto.getLastName().matches(CHARACTER_REGEX)){
				log.error("Last Name should contain only characters");
				errors.add("Last Name should contain only characters");
			}
		}
		// Check whether the contact number is blank or not.
		if(StringUtils.isNotBlank(memberRegistrationDto.getContactNo())) {
			//contact number pattern and length check.
			if(!memberRegistrationDto.getContactNo().matches(CONTACT_NO_REGEX)) {
				log.error("Contact Number Should contin only digits and the maximum length can be 10");
				errors.add("Contact Number Should contin only digits and the maximum length can be 10");
			}
		}
	   // Check whether the email id is blank or not.
		if(StringUtils.isNotBlank(memberRegistrationDto.getEmail())){
		    //Email id pattern check. 
			if(!memberRegistrationDto.getEmail().matches(EMAIL_PATTERN)) {
			     log.error("Please provide a valid Email Id");
			     errors.add("Please provide a valid Email Id");
		      }
		     // email id length check.
		     if(memberRegistrationDto.getEmail().length()>30){
		    	 log.error("Maximum length of email can be 30");
			     errors.add("Maximum length of email can be 30");
		     }
		}
		return errors;
	}
	
	@Override
	@Transactional
	public void addNewMember(MemberRegistrationDto memberRegistrationDto) throws OLMSException{
		 memberRegistrationDao.addNewMember(memberRegistrationDto);
	}
}
