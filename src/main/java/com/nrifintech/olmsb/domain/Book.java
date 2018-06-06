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

public class Book extends BaseEntityWithVersion{

	private Long bookPk;
	private String bookName;
	private String author;
	private String publisher;
	private Long isbn;
	private Long edition;
	private Long categoryPk;
	private Long noOfBook;
	private Long availableBook;
	private String status;
	
	/**
	 * @return the categoryPk
	 */
	public Long getCategoryPk() {
		return categoryPk;
	}
	/**
	 * @param categoryPk the categoryPk to set
	 */
	public void setCategoryPk(Long categoryPk) {
		this.categoryPk = categoryPk;
	}
	/**
	 * @return the bookPk
	 */
	public Long getBookPk() {
		return bookPk;
	}
	/**
	 * @param bookPk the bookPk to set
	 */
	public void setBookPk(Long bookPk) {
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
	public Long getIsbn() {
		return isbn;
	}
	/**
	 * @param isbn the isbn to set
	 */
	public void setIsbn(Long isbn) {
		this.isbn = isbn;
	}
	/**
	 * @return the edition
	 */
	public Long getEdition() {
		return edition;
	}
	/**
	 * @param edition the edition to set
	 */
	public void setEdition(Long edition) {
		this.edition = edition;
	}
	/**
	 * @return the noOfBook
	 */
	public Long getNoOfBook() {
		return noOfBook;
	}
	/**
	 * @param noOfBook the noOfBook to set
	 */
	public void setNoOfBook(Long noOfBook) {
		this.noOfBook = noOfBook;
	}
	/**
	 * @return the availableBook
	 */
	public Long getAvailableBook() {
		return availableBook;
	}
	/**
	 * @param availableBook the availableBook to set
	 */
	public void setAvailableBook(Long availableBook) {
		this.availableBook = availableBook;
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
	