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

public class BookCategory extends BaseEntityWithVersion {

	private long bookCategoryPk;
	private String bookCategoryName;
	private String description;
	private String status;
	/**
	 * @return the bookCategoryPk
	 */
	public long getBookCategoryPk() {
		return bookCategoryPk;
	}
	/**
	 * @param bookCategoryPk the bookCategoryPk to set
	 */
	public void setBookCategoryPk(long bookCategoryPk) {
		this.bookCategoryPk = bookCategoryPk;
	}
	/**
	 * @return the bookCategoryName
	 */
	public String getBookCategoryName() {
		return bookCategoryName;
	}
	/**
	 * @param bookCategoryName the bookCategoryName to set
	 */
	public void setBookCategoryName(String bookCategoryName) {
		this.bookCategoryName = bookCategoryName;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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
