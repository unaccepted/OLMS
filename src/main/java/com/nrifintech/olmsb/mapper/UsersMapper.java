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
package com.nrifintech.olmsb.mapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.nrifintech.olmsb.domain.User;

public class UsersMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {

		User users = new User();
		users.setUserPk(rs.getLong("USER_PK"));
		users.setFirstName(rs.getString("FIRST_NAME"));
		users.setLastName(rs.getString("LAST_NAME"));
		users.setUserId(rs.getString("USER_ID"));
		users.setEmail(rs.getString("EMAIL"));
		users.setCreatedBy(rs.getString("CREATED_BY"));
		users.setUpdatedBy(rs.getString("UPDATED_BY"));
		users.setCreationDate(rs.getDate("CREATION_DATE"));
		users.setUpdateDate(rs.getDate("UPDATE_DATE"));
		users.setCcCheck(rs.getLong("CC_CHECK"));
		
		return users;
	}

}
