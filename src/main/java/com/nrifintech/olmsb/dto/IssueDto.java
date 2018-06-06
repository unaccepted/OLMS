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
package com.nrifintech.olmsb.dto;

import java.util.Date;

public class IssueDto {
	private String issueId;
	private Date fromDate;
	private String fromDateStr;
	private Date toDate;
	private String toDateStr;
	private String isbnValue="";
	private String status;
	private String userId;
	private String bookName;
	private String bookCategory;
	
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getBookCategory() {
		return bookCategory;
	}
	public void setBookCategory(String bookCategory) {
		this.bookCategory = bookCategory;
	}
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the issueId
	 */
	public String getIssueId() {
		return issueId;
	}
	/**
	 * @param issueId the issueId to set
	 */
	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}
	/**
	 * @return the fromDate
	 */
	public Date getFromDate() {
		return fromDate;
	}
	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	/**
	 * @return the fromDateStr
	 */
	public String getFromDateStr() {
		return fromDateStr;
	}
	/**
	 * @param fromDateStr the fromDateStr to set
	 */
	public void setFromDateStr(String fromDateStr) {
		this.fromDateStr = fromDateStr;
	}
	/**
	 * @return the toDate
	 */
	public Date getToDate() {
		return toDate;
	}
	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	/**
	 * @return the toDateStr
	 */
	public String getToDateStr() {
		return toDateStr;
	}
	/**
	 * @param toDateStr the toDateStr to set
	 */
	public void setToDateStr(String toDateStr) {
		this.toDateStr = toDateStr;
	}
	/**
	 * @return the isbnValue
	 */
	public String getIsbnValue() {
		return isbnValue;
	}
	/**
	 * @param isbnValue the isbnValue to set
	 */
	public void setIsbnValue(String isbnValue) {
		this.isbnValue = isbnValue;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
}
