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

import com.nrifintech.olmsb.domain.BookCategory;
import com.nrifintech.olmsb.dto.BookCategoryDto;
import com.nrifintech.olmsb.exception.OLMSException;

/**
 * Service layer contract for Book Category management functionality for admin
 * 
 * @author amols
 *
 */
public interface BookCategoryService {
	/**
	 * This API is responsible for validating the book category details submitted by the Admin.
	 * 
	 * @param bookCategoryDto
	 *                    BookCategoryDto
	 * @return OLMSModelMap
	 */
	public List<String> validateBookCategory(BookCategoryDto bookCategoryDto);
	/**
	 * This API is responsible for adding a book category to the system.
	 * @param bookCategoryDto
	 *                  BookCategoryDto
	 * @throws OLMSException
	 */
	public void addBookCategory(BookCategoryDto bookCategoryDto) throws OLMSException;
    /**
    * This API is responsible for fetching a list of book categories from the system.
    * @param bookCategoryDto
    *                    BookCategoryDto
    * @return OLMSModelMap
    * @throws OLMSException
    */
	public List<BookCategory> findBookCategory(BookCategoryDto bookCategoryDto) throws OLMSException;
	
	/**
	 * This API is responsible for removing a book category from the system.
	 * @param bookCategoryPks
	 * @throws OLMSException
	 */
	public void removeBookCategory(List<Long> bookCategoryPks) throws OLMSException;

}
