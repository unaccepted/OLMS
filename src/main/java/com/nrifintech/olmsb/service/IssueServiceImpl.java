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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nrifintech.olmsb.dao.IssueDao;
import com.nrifintech.olmsb.domain.AdminIssue;
import com.nrifintech.olmsb.dto.IssueDto;
import com.nrifintech.olmsb.exception.OLMSException;

@Service
public class IssueServiceImpl implements IssueService {
	private final Logger log = LoggerFactory.getLogger(IssueServiceImpl.class);
	@Autowired
	private IssueDao issueDao;
	
	@Override
	public List<String> validateIssueQueryDetails(IssueDto issueDto) {
		List<String> errors = new ArrayList<>();

		String fromDateStr = issueDto.getFromDateStr();
		String toDateStr = issueDto.getToDateStr();
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		//Validation for Date formatting
		if (!(StringUtils.isEmpty(fromDateStr))) {
			try {
				issueDto.setFromDate(df.parse(fromDateStr));
			} catch (ParseException e) {
				log.error("Invalid from date provided", e);
				errors.add("Invalid from date provided");
			}
		}
		//Validation for Date formatting
		if (!(StringUtils.isEmpty(toDateStr))) {
			try {
				issueDto.setToDate(df.parse(toDateStr));
			} catch (ParseException e) {
				log.error("Invalid to date provided", e);
				errors.add("Invalid to date provided");
			}
		}
		//Validation to check that to date is not before from date
		if (!(StringUtils.isEmpty(toDateStr)) && (!StringUtils.isEmpty(fromDateStr))) {
			if (issueDto.getFromDate().after(issueDto.getToDate())) {
				log.error("From date cannot be after to date");
				errors.add("From date cannot be after to date");
			}
		}
		//Validation to check that isbn is a number
		if (!(StringUtils.isEmpty(issueDto.getIsbnValue().trim()))) {
			try {
				Long.valueOf(issueDto.getIsbnValue().trim());
			} catch (NumberFormatException e) {
				log.error("ISBN must be a number", e);
				errors.add("ISBN must be a number");
			}
		}
		
		return errors;
	}
	public List<AdminIssue> getIssues(IssueDto issueDto) throws OLMSException {
		List<AdminIssue> issues=new ArrayList<>();
		try {
			issues = issueDao.getAllIssues(issueDto);
			
			}
		catch (OLMSException e) {
			log.error("Unable to fetch book issues", e);
		}
		return issues;
	}
	@Override
	@Transactional
	public List<String> acceptIssue(List<Long> issues) throws OLMSException {
		List<String> errors =new ArrayList<>();
		try{
		errors = issueDao.acceptIssues(issues);
		}
		catch(OLMSException e)
		{
			log.error("Error accepting request", e);
			errors.add(e.getMessage());	
		}
		return errors;
	}
}