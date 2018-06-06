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

public class MemberBook extends BaseEntityWithVersion{
	private long  bookPk;
	private String bookName;
	private String author;
	private String publisher;
	private long isbn;
	private String bookCategoryName;
	private String availableForIssue;
	/**
	 * @return the bookPk
	 */
	public long getBookPk() {
		return bookPk;
	}
	/**
	 * @param bookPk the bookPk to set
	 */
	public void setBookPk(long bookPk) {
		this.bookPk = bookPk;
	}
	/**
	 * @return the bookName
	 */
	public String getBookName() {
		return bookName;
	}
	/**
	 * @param bookName the bookName to set
	 */
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}
	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	/**
	 * @return the publisher
	 */
	public String getPublisher() {
		return publisher;
	}
	/**
	 * @param publisher the publisher to set
	 */
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	/**
	 * @return the isbn
	 */
	public long getIsbn() {
		return isbn;
	}
	/**
	 * @param isbn the isbn to set
	 */
	public void setIsbn(long isbn) {
		this.isbn = isbn;
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
	 * @return the availableForIssue
	 */
	public String getAvailableForIssue() {
		return availableForIssue;
	}
	/**
	 * @param availableForIssue the availableForIssue to set
	 */
	public void setAvailableForIssue(String availableForIssue) {
		this.availableForIssue = availableForIssue;
	}
	
}
