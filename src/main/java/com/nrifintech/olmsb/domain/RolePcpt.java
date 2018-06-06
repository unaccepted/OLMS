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
package com.nrifintech.olmsb.domain;
public class RolePcpt extends BaseEntityWithVersion{
	private long  userRolePcptPk;
	private long  userPk;
	private long  rolePk;
	/**
	 * @return the userRolePcptPk
	 */
	public long getUserRolePcptPk() {
		return userRolePcptPk;
	}
	/**
	 * @param userRolePcptPk the userRolePcptPk to set
	 */
	public void setUserRolePcptPk(long userRolePcptPk) {
		this.userRolePcptPk = userRolePcptPk;
	}
	/**
	 * @return the userPk
	 */
	public long getUserPk() {
		return userPk;
	}
	/**
	 * @param userPk the userPk to set
	 */
	public void setUserPk(long userPk) {
		this.userPk = userPk;
	}
	/**
	 * @return the rolePk
	 */
	public long getRolePk() {
		return rolePk;
	}
	/**
	 * @param rolePk the rolePk to set
	 */
	public void setRolePk(long rolePk) {
		this.rolePk = rolePk;
	}
	
}
