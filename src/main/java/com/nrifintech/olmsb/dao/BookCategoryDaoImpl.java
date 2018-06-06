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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.nrifintech.olmsb.Constants;
import com.nrifintech.olmsb.domain.BookCategory;
import com.nrifintech.olmsb.dto.BookCategoryDto;
import com.nrifintech.olmsb.exception.OLMSException;
import com.nrifintech.olmsb.mapper.BookCatergoryMapper;

@Repository
public class BookCategoryDaoImpl implements BookCategoryDao {

	private final Logger log = LoggerFactory.getLogger(BookCategoryDaoImpl.class);

	private JdbcTemplate jdbcTemplate;

	@PostConstruct
	public void init() {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Autowired
	private DataSource dataSource;

	@Autowired
	private BookDao bookDao;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public void addBookCategory(BookCategoryDto bookCategoryDto) throws OLMSException {
		try {
			StringBuilder insertSql = new StringBuilder();
			insertSql.append("insert into olms_book_category ");
			insertSql.append("(book_category_pk, book_category_name, ");
			insertSql.append("description, status, created_by, ");
			insertSql.append("creation_date, updated_by, update_date, cc_check) ");
			insertSql.append("values(seq_olms_book_inventory.nextval,?,?,?,?,?,?,?,?)");

			jdbcTemplate.update(insertSql.toString(),
					new Object[] { bookCategoryDto.getBookCategoryName(), bookCategoryDto.getDescription(),
							Constants.NORMAL_STATUS, "SYSTEM", new Date(), "SYSTEM", new Date(), 1 });
		} catch (Exception e) {
			log.error("Unable to insert book category", e);
			throw new OLMSException("Unable to insert book category", e);
		}
	}

	@Override
	public void amendBookCategory() {
		// TODO Auto-generated method stub
	}

	@Override
	public List<BookCategory> findBookCategory(BookCategoryDto bookCategoryDto) throws OLMSException {
		try {
			List<BookCategory> bookCategories = new ArrayList<>();
			StringBuilder query = new StringBuilder();
			query.append("select * from olms_book_category ");
			query.append("where lower(book_category_name) like '%");

			if (!StringUtils.isEmpty(bookCategoryDto.getBookCategoryName())) {
				query.append(StringUtils.lowerCase(bookCategoryDto.getBookCategoryName().trim()));
			}
			query.append("%' and status='");
			query.append(Constants.NORMAL_STATUS);
			query.append("'");
			query.append(" order by book_category_name asc,update_date desc");

			bookCategories = jdbcTemplate.query(query.toString(), new BookCatergoryMapper());
			return bookCategories;
		} catch (DataAccessException e) {
			log.error("Unable to query book category", e);
			throw new OLMSException("Unable to query book category", e);
		}
	}

	@Override
	public void removeBookCategory(List<Long> bookCategoryPks) throws OLMSException {
		
		String categoryName = StringUtils.EMPTY;
		try {
			for (Long categoryPk : bookCategoryPks) {
				String selectSqlCategory = "select * from olms_book_category where book_category_pk=? ";
				BookCategory bookCategory = jdbcTemplate.queryForObject(selectSqlCategory, new Object[] { categoryPk },
						new BookCatergoryMapper());
				categoryName = bookCategory.getBookCategoryName();
				String selectSqlBook = "select book_pk from olms_book where book_category_pk=?";
				List<Long> bookPks = jdbcTemplate.queryForList(selectSqlBook, Long.class, categoryPk);
				bookDao.deletebooks(bookPks);
				
				String updateSqlCategory = " update olms_book_category set status=? , cc_check=? where  book_category_pk=? and cc_check=?";
				long ccCheckCategory = bookCategory.getCcCheck() + 1;
				int categoryUpdateCount = jdbcTemplate.update(updateSqlCategory, Constants.CANCEL_STATUS,
						ccCheckCategory, categoryPk, bookCategory.getCcCheck());
				if (categoryUpdateCount <= 0) {
					log.error("Failed to update book category");
					throw new OLMSException("Failed to update book category");
				}
			}
		} catch (DataAccessException e) {
			log.error("Unable to remove book category",e);
			throw new OLMSException("Unable to remove book category",e);
		} catch (OLMSException e) {
			log.error(" Unable to delete category : " + categoryName ,e);
			throw new OLMSException(" Unable to delete category : " + categoryName ,e);
		}
		

	}

	@Override
	public boolean isBookCategoryExist(String bookCategoryName) throws OLMSException {

		try {
			StringBuilder sql = new StringBuilder().append("SELECT COUNT(*) FROM olms_book_category")
					.append(" WHERE lower(book_category_name)=? ")
					.append(" and status=? ");
			log.debug("Sql to fetch all the book category : " + sql.toString());
			int count = jdbcTemplate.queryForObject(sql.toString(),
					new Object[] { StringUtils.lowerCase(bookCategoryName.trim()), Constants.NORMAL_STATUS }, 
					Integer.class);
			// If count is greater then 0, that means book category name already
			// exist.
			if (count > 0) {
				return true;
			} else {
				return false;
			}
		} catch (DataAccessException e) {
			log.error("Failed to fetch the count of records from book category table", e);
			throw new OLMSException("Failed to fetch the count of records from book category table");
		}

	}

}
