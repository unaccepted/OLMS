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

import com.nrifintech.olmsb.dto.MemberRegistrationDto;
import com.nrifintech.olmsb.exception.OLMSException;

/**
 * This is data access layer contract for new member management.
 * 
 * @version $Revision: 1.6 $, $Date: 2016/09/12 15:40:09 $
 * @author Amols
 *
 */
public interface MemberRegistrationDao {

	/**
	 * This API is responsible to add the Member in the OLMS_USER table
	 * 
	 * @param memberRegistrationDto
	 *                   MemberRegistrationDto
	 * @throws OLMSException
	 */
	public void addNewMember(MemberRegistrationDto memberRegistrationDto) throws OLMSException;
	
	/**
	 * This API is responsible to check whether the user Id already exist in the
	 * database or not.
	 * 
	 * @param userId
	 *            User Id
	 * @throws OLMSException
	 */
	public boolean isUserExist(String userId) throws OLMSException;

}
