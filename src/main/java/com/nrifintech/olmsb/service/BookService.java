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

import java.util.List;

import com.nrifintech.olmsb.domain.Book;
import com.nrifintech.olmsb.domain.BookCategory;
import com.nrifintech.olmsb.dto.BookDto;
import com.nrifintech.olmsb.dto.BookInventoryDto;
import com.nrifintech.olmsb.exception.OLMSException;
/**
 * This is a service layer contract for Book Management functionality for admin.
 * @author amanj
 *
 */
public interface BookService {
	/**
	 * This API is responsible to add the New book 
	 * @param bookDto
	 * @return OLMSModelMap
	 */
	public void addBook(BookDto bookDto) throws OLMSException;
	/**
	 * This API is responsible for validating the book details
	 * @param bookDto
	 * @return OLMSModelMap
	 */
	public List<String> validateBook(BookDto bookDto);
	/**
	 * This API is responsible for fetching categories from OLMS_BOOK_CATEGORY
	 * @return List
	 */
	public List<BookCategory> fetchAllCategories() throws OLMSException;
	/**
	 * This API is responsible for fetching book details
	 * @param bookDto
	 * @return OLMSModelMap
	 */
	public List<Book> findBook(BookDto bookDto) throws OLMSException;
	/**
	 * This API is responsible for validating the ISBN
	 * @param isbn
	 * @return OLMSModelMap
	 */
	public List<String> validateIsbn(String isbn);
	/**
	 * This API is responsible for deleting the books
	 * @param books
	 * @throws OLMSException
	 */
	public void deleteBooks(List<Long> bookPks) throws OLMSException;
	/**
	 * This API is responsible for fetching the details of book inventory
	 * @param isbn
	 * @return
	 * @throws OLMSException
	 */
	public List<BookInventoryDto> getBookInventory(String isbn) throws OLMSException;
	/**
	 * This API is responsible for deleting the book copies from book inventory
	 * @param bookCopy
	 * @throws OLMSException
	 */
	public void deleteBookCopies(List<Long> bookCopyPks) throws OLMSException;
	/**
	 * This API is responsible for adding the book copies in book inventory
	 * @param bookNo
	 * @throws OLMSException
	 */
	public void addBookCopies(BookDto bookDto) throws OLMSException;
}