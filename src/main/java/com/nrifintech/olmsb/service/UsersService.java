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

import com.nrifintech.olmsb.domain.User;
import com.nrifintech.olmsb.dto.UsersDto;
import com.nrifintech.olmsb.exception.OLMSException;
/**
 * This is a Service layer contract for Member Management functionality for Admin.
 * @author surajb
 *
 */
public interface UsersService {
	/**
	 * This API is responsible for to get all the Existing Users
	 * @param usersDto
	 * @return
	 */
	public List<User>  findExistingUser(UsersDto usersDto) throws OLMSException;
	/**
	 * This API is responsible for to get all the new registration requests
	 * @param usersDto
	 * @return
	 */
	public List<User> getNewRegistrationRequest(UsersDto usersDto) throws OLMSException;
	/**
	 * This API is responsible to remove the Existing Users
	 * @param users
	 * @return
	 * @throws OLMSException 
	 */
	public void removeUsers(List<Long> userPks) throws OLMSException;
	/**
	 * This API is responsible to Accept the New Registered Users Requests and thus to Add the users
	 * @param users
	 * @return
	 */
	public void addUsers(List<Long> userPks) throws OLMSException;
	/**
	 * This API is responsible to Reject the New Registered Users requests
	 * @param users
	 * @return
	 */
	public void rejectUsers(List<Long> userPks) throws OLMSException;
}
