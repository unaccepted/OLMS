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

import java.util.Date;


import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.nrifintech.olmsb.Constants;
import com.nrifintech.olmsb.dto.MemberRegistrationDto;
import com.nrifintech.olmsb.exception.OLMSException;

@Repository
public class MemberRegistrationDaoImpl  implements MemberRegistrationDao {
	
	private final Logger LOGGER = LoggerFactory.getLogger(MemberRegistrationDaoImpl.class);
	
	@Autowired
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	@Override
	public void addNewMember(MemberRegistrationDto memberRegistrationDto) throws OLMSException {
		try {
			// create the query
			StringBuilder insertSql = new StringBuilder()
					.append("INSERT INTO olms_user ")
					.append("(user_pk, user_id, ")
					.append("appl_password, first_name, last_name, ")
					.append("contact_no, email, ")
					.append("status, created_by, ")
					.append("creation_date, updated_by, update_date, cc_check) ")
					.append("values(seq_olms_user.nextval,?,?,?,?,?,?,?,?,?,?,?,?)");
			
			LOGGER.debug("Sql to insert the data in OLMS_USER table : " + insertSql.toString());
			
			// Instantiate the JDBCTemplate with the data source
			JdbcTemplate jdbcTemplate =  new JdbcTemplate(dataSource);
			// Execute the query
			jdbcTemplate.update(insertSql.toString(), 
					new Object[] {memberRegistrationDto.getUserId(),
							memberRegistrationDto.getPassword(),
							memberRegistrationDto.getFirstName(),
							memberRegistrationDto.getLastName(),
							memberRegistrationDto.getContactNo(),
							memberRegistrationDto.getEmail(),
							Constants.PENDING_STATUS,
							Constants.SYSTEM, 
							new Date(), 
							Constants.SYSTEM, 
							new Date(),
							1});
		} catch (DataAccessException e) {
			LOGGER.error("Failed to insert into user table", e);
			throw new OLMSException("Failed to insert into user table",e);
		}
	}

	@Override
	public boolean isUserExist(String userId) throws OLMSException {
		try {
			// create the query
			StringBuilder sql = new StringBuilder()
					.append("SELECT COUNT(*) FROM olms_user")
					.append(" WHERE user_id=? ");
			
			LOGGER.debug("Sql to fetch all the User Id : " + sql.toString());
			// Instantiate the JDBCTemplate with the data source
			JdbcTemplate jdbcTemplate =  new JdbcTemplate(dataSource);
			// Execute the query and fetch the count
			int count = jdbcTemplate.queryForObject(sql.toString(), new Object[] {userId}, Integer.class);
			
			// If count is greater then 0, that means user already exist with the same user id.
			// Hence return true. Otherwise, return false. 
			if(count>0) {
				return true;
			} else {
				return false;
			}
		} catch (DataAccessException e) {
			LOGGER.error("Failed to fetch the count of records from user table", e);
			throw new OLMSException("Failed to fetch the count of records from user table",e);
		}
	}
}
