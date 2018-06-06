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

import com.nrifintech.olmsb.dto.MemberRegistrationDto;
import com.nrifintech.olmsb.exception.OLMSException;

/**
 * This is a service layer for new member management details.
 * 
 * @version $Revision: 1.7 $, $Date: 2016/09/13 13:10:50 $
 * @author Amols
 *
 */
public interface MemberRegistrationService {

	/**
	 * This API is responsible to validate the Member registration DTO 
	 * 
	 * @param memberRegistrationDto
	 * 					MemberRegistrationDto
	 * @return OLMSModelMap
	 */
	public  List<String> validateNewMemberDetails(MemberRegistrationDto memberRegistrationDto);

	/**
	 * This API is responsible to add the New Member registration DTO
	 *  
	 * @param memberRegistrationDto
	 *                  MemberRegistrationDto
	 * @throws OLMSException
	 */
	public void addNewMember(MemberRegistrationDto memberRegistrationDto) throws OLMSException;
}
