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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nrifintech.olmsb.dao.BookCategoryDao;
import com.nrifintech.olmsb.domain.BookCategory;
import com.nrifintech.olmsb.dto.BookCategoryDto;
import com.nrifintech.olmsb.exception.OLMSException;

@Service
public class BookCategoryServiceImpl implements BookCategoryService {

	private final Logger log = LoggerFactory.getLogger(BookCategoryServiceImpl.class);
	private static final String CATEGORY_NAME_REGEX = "^[a-zA-Z0-9]*$";

	@Autowired
	private BookCategoryDao bookCategoryDao;

	@Override
	public List<String> validateBookCategory(BookCategoryDto bookCategoryDto) {
		List<String> errors = new ArrayList<>();
		String bookCategoryName = bookCategoryDto.getBookCategoryName();
		String bookCategoryDescription = bookCategoryDto.getDescription();
		try {
			if (StringUtils.isBlank(bookCategoryName)) {
				log.error("Book category name cannot be empty.");
				errors.add("Book category name cannot be empty.");
			} else {
				if (bookCategoryDao.isBookCategoryExist(bookCategoryName)) {
					log.error("Provided book category name already exists.");
					errors.add("Provided book category name already exists.");
				}
				if(!bookCategoryName.matches(CATEGORY_NAME_REGEX)) {
					log.error("The Book Category name must contain only alphabets or/and numbers");
					errors.add("The Book Category name must contain only alphabets or/and numbers");
				}
				if (bookCategoryName.length() > 20) {
					log.error("Book category length large: must be within 20 characters");
					errors.add("Book category length large : must be within 20 characters");
				}
				if (bookCategoryDescription.length() > 50) {
					log.error("Book description length large  : must be within 50 characters");
					errors.add("Book description length large  : must be within 50 characters");
				}
			}
		} catch (OLMSException e) {
			log.error(e.getMessage(), e);
			errors.add(e.getMessage());
		}
		return errors;
	}

	@Override
	public void addBookCategory(BookCategoryDto bookCategoryDto) throws OLMSException {
		bookCategoryDao.addBookCategory(bookCategoryDto);
	}

	@Override
	public List<BookCategory> findBookCategory(BookCategoryDto bookCategoryDto) {
		List<BookCategory> bookCategories = new ArrayList<>();
		try {
			bookCategories = bookCategoryDao.findBookCategory(bookCategoryDto);
		} catch (OLMSException e) {
			log.error("Unable to fetch book category", e.getMessage());
		}
		return bookCategories;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void removeBookCategory(List<Long> bookCategoryPks) throws OLMSException {
		bookCategoryDao.removeBookCategory(bookCategoryPks);
	}

}
