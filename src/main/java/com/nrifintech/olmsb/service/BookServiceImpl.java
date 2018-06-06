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
import com.nrifintech.olmsb.dao.BookDao;
import com.nrifintech.olmsb.domain.Book;
import com.nrifintech.olmsb.domain.BookCategory;
import com.nrifintech.olmsb.domain.BookInventory;
import com.nrifintech.olmsb.dto.BookCategoryDto;
import com.nrifintech.olmsb.dto.BookDto;
import com.nrifintech.olmsb.dto.BookInventoryDto;
import com.nrifintech.olmsb.exception.OLMSException;

/**
 * This class implements the BookService
 * 
 * @author amanj
 *
 */
@Service
public class BookServiceImpl implements BookService {

	private final Logger log = LoggerFactory.getLogger(BookServiceImpl.class);
	@Autowired
	private BookDao bookDao;

	@Autowired
	private BookCategoryDao bookCategoryDao;

	@Override
	public List<String> validateIsbn(String isbn) {
		List<String> errors = new ArrayList<>();
		if (!StringUtils.isBlank(isbn)) {
			if (!StringUtils.isNumeric(isbn.trim())) {
				log.error("ISBN must be a number");
				errors.add("ISBN must be a number");
			}
		}
		return errors;
	}

	@Override
	public List<String> validateBook(BookDto bookDto) {
		List<String> errors = new ArrayList<>();

		if (StringUtils.isBlank((bookDto.getBookName().trim()))) {
			log.error("Book  name can not be blank");
			errors.add("Book  name can not be blank");
		} else {
			if (StringUtils.length(bookDto.getBookName().trim()) > 20) {
				log.error("The maximum length of Book Name can be 20");
				errors.add("The maximum length of Book Name can be 20");
			}
		}

		if (StringUtils.isBlank((bookDto.getAuthor().trim()))) {
			log.error("Author can not be blank");
			errors.add("Author can not be blank");
		} else {
			if (StringUtils.length(bookDto.getAuthor().trim()) > 20) {
				log.error("The maximum length of Author Name can be 20");
				errors.add("The maximum length of Author Name can be 20");
			}
		}
		if (StringUtils.isBlank((bookDto.getIsbn().trim()))) {
			log.error("ISBN cannot be blank");
			errors.add("ISBN cannot be blank");
		} else {
			if (!StringUtils.isNumeric(bookDto.getIsbn().trim())) {
				log.error("ISBN must be a number");
				errors.add("ISBN must be a number");
			}else 
				{
				if (StringUtils.length(bookDto.getIsbn().trim()) != 13) {
				
				log.error("ISBN length must be of 13 digits");
				errors.add("ISBN length must be of 13 digits");
				} 
				else  {
				try {
					// check whether the isbns exist or not.
					if (bookDao.isIsbnExist(bookDto.getIsbn().trim())) {
						log.error("Provided ISBN already exists.");
						errors.add("Provided ISBN already exists.");
					}
				} catch (OLMSException e) {
					log.error("Failed to validate the Book Isbn. Please try again.", e);
					errors.add("Failed to validate the Book Isbn. Please try again.");
				}
			}
			
				}
		}
		if (StringUtils.isBlank((bookDto.getPublisher().trim()))) {
			log.error("Publisher can not be blank");
			errors.add("Publisher can not be blank");
		} else {
			if (StringUtils.length(bookDto.getPublisher().trim()) > 20) {
				log.error("The maximum length of Publisher can be 20");
				errors.add("The maximum length of Publisher can be 20");
			}
		}
		if (StringUtils.isBlank(bookDto.getEdition().trim())) {
			log.error("Edition can not be blank");
			errors.add("Edition can not be blank");
		} else {
			if (!StringUtils.isNumeric(bookDto.getEdition().trim())) {
				log.error("Edition must be a number");
				errors.add("Edition must be a number");
			}
			if (StringUtils.length(bookDto.getEdition().trim()) > 10) {
				log.error("The maximum length of Edition can be 10");
				errors.add("The maximum length of Edition can be 10");
			}
		}
		if (StringUtils.isBlank(bookDto.getNoOfCopies().trim())) {
			log.error("No of Copies can not be blank");
			errors.add("No of Copies can not be blank");
		} else {
			if (!StringUtils.isNumeric(bookDto.getNoOfCopies().trim())) {
				log.error("No of Copies must be a number");
				errors.add("No of Copies be a number");
			}
			if (StringUtils.length(bookDto.getNoOfCopies().trim()) > 10) {
				log.error("The maximum length of No Of Copies can be 10");
				errors.add("The maximum length of No Of Copies can be 10");
			}
		}
		return errors;
	}

	@Override
	public void addBook(BookDto bookDto) throws OLMSException {
		
			bookDao.addBook(bookDto);
		
	}

	@Override
	public List<BookCategory> fetchAllCategories() throws OLMSException {

		BookCategoryDto bookCategoryDto = new BookCategoryDto();
		return bookCategoryDao.findBookCategory(bookCategoryDto);
	}

	@Override
	@Transactional
	public List<Book> findBook(BookDto bookDto) throws OLMSException {
		return bookDao.findBook(bookDto);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteBooks(List<Long> bookPks) throws OLMSException {
		bookDao.deletebooks(bookPks);
	}

	@Override
	public List<BookInventoryDto> getBookInventory(String isbn) throws OLMSException {
		List<BookInventoryDto> bookInventoryDtos = new ArrayList<>();
		List<BookInventory> bookCopies = bookDao.getBookInventory(isbn);
		if (!bookCopies.isEmpty()) {

			for (int i = 0; i < bookCopies.size(); i++) {
				BookInventoryDto dto = new BookInventoryDto();
				dto.setAvailable(bookCopies.get(i).getAvailable());
				dto.setBookId(bookCopies.get(i).getBookId());
				dto.setBookInventoryPk(bookCopies.get(i).getBookInventoryPk());
				dto.setBookPk(bookCopies.get(i).getBookPk());
				dto.setStatus(bookCopies.get(i).getStatus());
				bookInventoryDtos.add(dto);
			}
		}
		return bookInventoryDtos;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteBookCopies(List<Long> bookInventoryPks) throws OLMSException {
		bookDao.deleteBookCopies(bookInventoryPks);
	}

	@Override
	public void addBookCopies(BookDto bookDto) throws OLMSException {
		bookDao.addBookCopies(Long.parseLong(bookDto.getNoOfCopies()), Long.parseLong(bookDto.getIsbn()));
	}
}