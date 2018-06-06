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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nrifintech.olmsb.dao.UsersDao;
import com.nrifintech.olmsb.domain.User;
import com.nrifintech.olmsb.dto.UsersDto;
import com.nrifintech.olmsb.exception.OLMSException;

@Service
public class UsersServiceImpl implements UsersService {
	private final Logger log = LoggerFactory.getLogger(UsersServiceImpl.class);
	@Autowired
	private UsersDao usersDao;

	@Override
	public List<User> getNewRegistrationRequest(UsersDto usersDto) throws OLMSException {
		List<User> users = new ArrayList<>();
		try {
			users = usersDao.getNewRegistrationRequest(usersDto);
		} catch (OLMSException e) {
			log.error("Unable to fetch user data", e);
		}
		return users;
	}

	@Override
	public List<User> findExistingUser(UsersDto usersDto) {
		List<User> users = new ArrayList<>();
		try {
			users = usersDao.findExistingUser(usersDto);
		} catch (OLMSException e) {
			log.error("Unable to fetch user data", e);
		}
		return users;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void removeUsers(List<Long> userPks) throws OLMSException {
			usersDao.removeUsers(userPks);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addUsers(List<Long> userPks) throws OLMSException {
			usersDao.addUsers(userPks);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void rejectUsers(List<Long> userPks) throws OLMSException {
			usersDao.rejectUsers(userPks);
	}

}
