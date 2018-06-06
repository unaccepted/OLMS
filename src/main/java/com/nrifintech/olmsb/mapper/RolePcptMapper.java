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

import com.nrifintech.olmsb.domain.RolePcpt;

public class RolePcptMapper implements RowMapper<RolePcpt> {
   
	@Override
	public RolePcpt mapRow(ResultSet rs, int rowNum) throws SQLException {

		RolePcpt role = new RolePcpt();
		
		role.setUserRolePcptPk(rs.getLong("USER_ROLE_PCPT_PK"));
		role.setUserPk(rs.getLong("USER_PK"));
		role.setRolePk(rs.getLong("ROLE_PK"));
		role.setCreatedBy(rs.getString("CREATED_BY"));
		role.setUpdatedBy(rs.getString("UPDATED_BY"));
		role.setCreationDate(rs.getDate("CREATION_DATE"));
		role.setUpdateDate(rs.getDate("UPDATE_DATE"));
		role.setCcCheck(rs.getLong("CC_CHECK"));
		
		return role;
	}

}
