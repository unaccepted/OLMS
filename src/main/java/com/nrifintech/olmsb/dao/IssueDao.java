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
import com.nrifintech.olmsb.domain.AdminIssue;
import com.nrifintech.olmsb.dto.IssueDto;
import com.nrifintech.olmsb.exception.OLMSException;

	/**
	 * Repository/Dao layer contract for Admin Issue Management
	 * @author jayabratas 
	 */
	public interface IssueDao {
	/**
	 * This API is responsible for returning a list of admin issues against a particular query
	 * @param IssueDto issueDto
	 * @return List<AdminIssue>
	 * @throws OLMSException
	 */
	public List<AdminIssue> getAllIssues(IssueDto issueDto) throws OLMSException;

	/**
	 * This API is responsible for determining the issueStatus of an issue
	 * @param List<Long> issues
	 * @return List<String>
	 * @throws OLMSException
	 */
	public List<String> acceptIssues(List<Long> issues) throws OLMSException;

	/**
	 * This API is responsible for accepting return requests
	 * @param long issuePk
	 * @return void
	 * @throws OLMSException
	 */
	public void acceptReturn(long issuePk) throws OLMSException;

	/**
	 * This API is responsible for accepting issue requests
	 * @param long issuePk
	 * @return void
	 * @throws OLMSException
	 */
	public void acceptRequest(long issuePk) throws OLMSException;

}