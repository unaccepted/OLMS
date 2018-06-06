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

import java.util.Date;

public class Issue extends BaseEntityWithVersion{
	
	private long issuePk;
	private String issueId;
	private long userPk;
	private long bookPk;
	private long bookInventoryPk;
	private Date requestDate;
	private Date issueDate;
	private Date returnDate;
	private String issueStatus;
	private String status;
	
	
	public long getIssuePk() {
		return issuePk;
	}
	public void setIssuePk(long issuePk) {
		this.issuePk = issuePk;
	}
	public String getIssueId() {
		return issueId;
	}
	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}
	public long getUserPk() {
		return userPk;
	}
	public void setUserPk(long userPk) {
		this.userPk = userPk;
	}
	public long getBookInventoryPk() {
		return bookInventoryPk;
	}
	public void setBookInventoryPk(long bookInventoryPk) {
		this.bookInventoryPk = bookInventoryPk;
	}
	public Date getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
	public Date getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}
	public Date getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
	public String getIssueStatus() {
		return issueStatus;
	}
	public void setIssueStatus(String issueStatus) {
		this.issueStatus = issueStatus;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public long getBookPk() {
		return bookPk;
	}
	public void setBookPk(long bookPk) {
		this.bookPk = bookPk;
	}
	
}
