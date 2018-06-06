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

import java.util.List;

import com.nrifintech.olmsb.domain.MemberIssue;
import com.nrifintech.olmsb.dto.IssueDto;
import com.nrifintech.olmsb.exception.OLMSException;
/**
 * Service Layer contract for Member issue display functionality
 * @author jayabratas
 *
 */
public interface MemberIssueService {
	/**
	 * This API is responsible validating details of issue query submitted by the user
	 * @param IssueDto issueDto
	 * @return List<String> errors
	 */
	public List<String> validateIssueQueryDetails(IssueDto issueDto);
	/**
	 * This API is responsible for fetching the list of issues for a particular member
	 * @param IssueDto issueDto
	 * @return List<MemberIssue>
	 * @throws OLMSException
	 */
	public List<MemberIssue> getIssues (IssueDto issueDto) throws OLMSException;
	/**
	 * This API is responsible for submitting return request from user to admin
	 * @param List<Long> issues
	 * @return List<String> errors
	 * @throws OLMSException 
	 */
	public List<String> returnBook(List<Long> issues) throws OLMSException;
}
