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

package com.nrifintech.olmsb.dao;

import java.util.List;
import com.nrifintech.olmsb.domain.MemberIssue;
import com.nrifintech.olmsb.dto.IssueDto;
import com.nrifintech.olmsb.exception.OLMSException;
/**
 * Data access layer contract for Member Issue Status Display and also returning books
 * @author jayabratas
 */
    public interface MemberIssueDao {
    	/**
    	 * This API is responsible for fetching the list of issues for a particular user
    	 * @param IssueDto issueDto
    	 * @return List<MemberIssue>
    	 * @throws OLMSException
    	 */
    	public List<MemberIssue> getAllIssues(IssueDto issueDto) throws OLMSException;
    	/**
    	 * This API is responsible for submitting a return Request against a list of books issued to a used
    	 * @param List<Long> issues
    	 * @return List<String>
    	 * @throws OLMSException
    	 */
    	public List<String> returnBook(List<Long> issues) throws OLMSException;
    	
    }