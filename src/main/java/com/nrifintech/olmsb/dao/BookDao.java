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
import java.util.List;

import com.nrifintech.olmsb.domain.Book;
import com.nrifintech.olmsb.domain.BookInventory;
import com.nrifintech.olmsb.dto.BookDto;
import com.nrifintech.olmsb.exception.OLMSException;
/**
 * This is a Data access layer contract for Book Management functionality for admin.
 * @author amanj
 *
 */
public interface BookDao {
	/**
	 * This API is responsible to add the New Books
	 * @param bookDto
	 * @throws OLMSException
	 */
	public void addBook(BookDto bookDto) throws OLMSException;
	/**
	 * This API is responsible to fetch the details of Books
	 * @param bookDto
	 * @return List<Book>
	 * @throws OLMSException
	 */
	public List<Book> findBook(BookDto bookDto)throws OLMSException;
	/**
	 * This API is responsible for deleting the books
	 * @param books
	 * @throws OLMSException
	 */
	public void deletebooks(List<Long> bookPks) throws OLMSException;
	/**
	 * This API is responsible for fetching the details of book inventory 
	 * @param isbn
	 * @return List<BookInventory>
	 * @throws OLMSException
	 */
	public List<BookInventory> getBookInventory(String isbn) throws OLMSException;
	/**
	 * This API is responsible for deleting the book copies from book inventory
	 * @param bookCopy
	 * @throws OLMSException
	 */
	public void deleteBookCopies(List<Long> bookInventoryPks) throws OLMSException;
	/**
	 * This API is responsible for validating the isbn for unique entry
	 * @param isbn
	 * @return boolean
	 * @throws OLMSException
	 */
	public boolean isIsbnExist(String isbn) throws OLMSException;
	/**
	 * This API is responsible for adding the book copies in book inventory
	 * @param bookNo
	 * @throws OLMSException
	 */
	public void addBookCopies(Long noOfCopies, Long isbn) throws OLMSException;
}
