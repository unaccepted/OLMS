
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
import com.nrifintech.olmsb.domain.Book;
import com.nrifintech.olmsb.domain.BookInventory;
import com.nrifintech.olmsb.domain.Issue;
import com.nrifintech.olmsb.dto.BookDto;
import com.nrifintech.olmsb.exception.OLMSException;
import com.nrifintech.olmsb.mapper.BookInventoryMapper;
import com.nrifintech.olmsb.mapper.BookMapper;
import com.nrifintech.olmsb.mapper.IssueMapper;

@Repository
public class BookDaoImpl implements BookDao {

	private final Logger log = LoggerFactory.getLogger(BookDaoImpl.class);

	private JdbcTemplate jdbcTemplate;

	@Autowired
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@PostConstruct
	public void init() {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void addBook(BookDto bookDto) throws OLMSException {
		try {
			String sql = "select seq_olms_book.nextval from dual";
			StringBuilder getCategoyPk = new StringBuilder();
			getCategoyPk.append(" select book_category_pk from olms_book_category");
			getCategoyPk.append(" where book_category_name =?");

			Long bookPk = jdbcTemplate.queryForList(sql, Long.class).get(0);
			Long categoryPk = jdbcTemplate
					.queryForList(getCategoyPk.toString(), new Object[] { bookDto.getCategory().trim() }, Long.class)
					.get(0);

			StringBuilder insertSql = new StringBuilder();
			insertSql.append(" insert into olms_book ");
			insertSql.append(" (book_pk, book_name, ");
			insertSql.append(" author, publisher, isbn,edition, book_category_pk,");
			insertSql.append(" no_of_copies, available_for_issue,status, created_by, ");
			insertSql.append(" creation_date, updated_by, update_date, cc_check) ");
			insertSql.append(" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			jdbcTemplate.update(insertSql.toString(),
					new Object[] { bookPk, bookDto.getBookName().trim(), bookDto.getAuthor().trim(),
							bookDto.getPublisher().trim(), Long.parseLong(bookDto.getIsbn().trim()),
							Long.parseLong(bookDto.getEdition().trim()), categoryPk,
							Long.parseLong(bookDto.getNoOfCopies().trim()),
							Long.parseLong(bookDto.getNoOfCopies().trim()), Constants.NORMAL_STATUS, Constants.SYSTEM,
							new Date(), Constants.SYSTEM, new Date(), 1 });

			String selectSql = "select seq_olms_book_inventory.nextval from dual";
			StringBuilder insertSql1 = new StringBuilder();
			insertSql1.append(" insert into olms_book_inventory ");
			insertSql1.append(" (book_inventory_pk, book_id, ");
			insertSql1.append(" book_pk, ");
			insertSql1.append(" status, available ,created_by, ");
			insertSql1.append(" creation_date, updated_by, update_date, cc_check) ");
			insertSql1.append(" values(?,?,?,?,'y',?,?,?,?,?)");

			for (int i = 0; i < Long.parseLong(bookDto.getNoOfCopies().trim()); i++) {
				Long inventoryPk = jdbcTemplate.queryForList(selectSql, Long.class).get(0);
				String bookId = bookDto.getIsbn() + "-" + inventoryPk;
				jdbcTemplate.update(insertSql1.toString(), new Object[] { inventoryPk, bookId, bookPk,
						Constants.NORMAL_STATUS, Constants.SYSTEM, new Date(), Constants.SYSTEM, new Date(), 1 });
			}


		} catch (DataAccessException | NumberFormatException e) {

			log.error("Unable to insert book ", e);
			throw new OLMSException("Unable to insert book ", e);
		}
	}

	@Override
	public List<Book> findBook(BookDto bookDto) throws OLMSException {
		List<Book> book = new ArrayList<>();
		try {
			StringBuilder query = new StringBuilder();
			query.append(" select *");
			query.append(" from olms_book book, olms_book_category cat");
			query.append(" where cat.book_category_pk = book.book_category_pk");
			query.append(" and lower(book.book_name) like '%");
			query.append(StringUtils.lowerCase(bookDto.getBookName().trim()));
			query.append("%'");
			query.append(" and cat.book_category_name like '%");
			query.append(bookDto.getCategory().trim());
			query.append("%'");
			query.append(" and lower(book.author) like '%");
			query.append(StringUtils.lowerCase(bookDto.getAuthor().trim()));
			query.append("%'");
			query.append(" and lower(book.isbn) like '%");
			query.append(StringUtils.lowerCase(bookDto.getIsbn().trim()));
			query.append("%'");
			query.append(" and lower(book.publisher) like '%");
			query.append(StringUtils.lowerCase(bookDto.getPublisher().trim()));
			query.append("%'");
			query.append(" and book.status='");
			query.append(Constants.NORMAL_STATUS);
			query.append("'");
			query.append(" order by book.book_name asc, book.isbn asc");
			book = jdbcTemplate.query(query.toString(), new BookMapper());
		} catch (DataAccessException e) {
			log.error("Unable to query book category", e);
			throw new OLMSException("Unable to query book category", e);
		}
		return book;
	}

	// long pk | try-catch
	@Override
	public void deletebooks(List<Long> bookPks) throws OLMSException {
		try {
			for (Long bookPk : bookPks) {
				int count = 0;
				int countIsuue = 0;
				long ccCheck;
				String selectSql = "select * from olms_book where book_pk=?";
				Book book = jdbcTemplate.queryForObject(selectSql, new Object[] { bookPk }, new BookMapper());
				if (book.getAvailableBook() == book.getNoOfBook()) {
					String updateSql = "update olms_book set status=?, cc_check=? where book_pk=? and cc_check=?";
					ccCheck = book.getCcCheck() + 1;
					count = jdbcTemplate.update(updateSql, Constants.CANCEL_STATUS, ccCheck, bookPk, book.getCcCheck());
				}
				if (count == 0) {
					log.error("Unable to remove book : " + book.getBookName());
					throw new OLMSException("Unable to remove book : " + book.getBookName());
				} else {
					selectSql = "select * from olms_book_inventory where book_pk=?";
					List<BookInventory> inventory = jdbcTemplate.query(selectSql, new Object[] { bookPk },
							new BookInventoryMapper());
					String updateSql = "update olms_book_inventory set status=?, cc_check=? where book_inventory_pk=? and cc_check=?";
					for (int i = 0; i < inventory.size(); i++) {
						int countInventory = 0;
						ccCheck = inventory.get(i).getCcCheck() + 1;
						countInventory = jdbcTemplate.update(updateSql, Constants.CANCEL_STATUS, ccCheck,
								inventory.get(i).getBookInventoryPk(), inventory.get(i).getCcCheck());
						if (countInventory == 0) {
							log.error("Unable to remove book : " + book.getBookName());
							throw new OLMSException("Unable to remove book : " + book.getBookName());
						}
					}

					selectSql = "select * from olms_issue where book_pk=?";
					List<Issue> issue = jdbcTemplate.query(selectSql, new Object[] { bookPk }, new IssueMapper());
					for (int i = 0; i < issue.size(); i++) {
						ccCheck = issue.get(i).getCcCheck() + 1;
						updateSql = "update olms_issue set status=?, cc_check=? where issue_pk=? and cc_check=?";
						countIsuue = jdbcTemplate.update(updateSql, Constants.CANCEL_STATUS, ccCheck,
								issue.get(i).getIssuePk(), issue.get(i).getCcCheck());
						if (countIsuue == 0) {
							log.error("Unable to remove book : " + book.getBookName());
							throw new OLMSException("Unable to remove book : " + book.getBookName());
						}
					}

				}
			}
		} catch (DataAccessException e) {
			log.error("Error while deleting book", e);
			throw new OLMSException("Error while deleting book ", e);
		}
	}

	@Override
	public List<BookInventory> getBookInventory(String isbn) throws OLMSException {
		List<BookInventory> bookInventories = new ArrayList<>();
		try {
			StringBuilder selectSql = new StringBuilder();
			selectSql.append("select inventory.* from olms_book_inventory inventory, olms_book book ");
			selectSql.append("where book.book_pk = inventory.book_pk and book.status=? ");
			selectSql.append("and inventory.status=? and book.isbn=?");
			bookInventories = jdbcTemplate.query(selectSql.toString(),
					new Object[] { Constants.NORMAL_STATUS, Constants.NORMAL_STATUS, Long.parseLong(isbn) },
					new BookInventoryMapper());
		} catch (DataAccessException e) {
			log.error("Error while fetching book inventory for isbn: {}", isbn, e);
			throw new OLMSException("Error while fetching book inventory for isbn : " + isbn, e);
		}
		return bookInventories;
	}

	//try-catch, long pk
	@Override
	public void deleteBookCopies(List<Long> bookInventoryPks) throws OLMSException {
		try {
			for (Long bookInventoryPk : bookInventoryPks) {
				int count = 0;
				int inventoryCount = 0;
				String selectSql = "select * from olms_book_inventory where book_inventory_pk=?";
				BookInventory bookInventory = jdbcTemplate.queryForObject(selectSql, new Object[] { bookInventoryPk },
						new BookInventoryMapper());
				if (bookInventory.getAvailable().equals("n")) {
					log.error("Unable to remove book copy : " + bookInventory.getBookId());
					throw new OLMSException("Unable to remove book copy : " + bookInventory.getBookId());
				} else {
					String updateSql = "update olms_book_inventory set status=?, cc_check=? where book_inventory_pk=? and cc_check=?";
					long ccCheck = bookInventory.getCcCheck() + 1;
					count = jdbcTemplate.update(updateSql, Constants.CANCEL_STATUS, ccCheck, bookInventoryPk,
							bookInventory.getCcCheck());

					if (count == 0) {
						log.error("Unable to remove book copy : " + bookInventory.getBookId());
						throw new OLMSException("Unable to remove book copy : " + bookInventory.getBookId());
					} else {
						selectSql = "select * from olms_book where book_pk=?";
						Book book = jdbcTemplate.queryForObject(selectSql, new Object[] { bookInventory.getBookPk() },
								new BookMapper());

						selectSql = "update olms_book set available_for_issue =? ,no_of_copies=?,cc_check=? where book_pk=? and cc_check=?";
						inventoryCount = jdbcTemplate.update(selectSql, book.getAvailableBook() - 1,
								book.getNoOfBook() - 1, book.getCcCheck() + 1, book.getBookPk(), book.getCcCheck());
						if (inventoryCount == 0) {
							log.error("Unable to remove book copy : " + bookInventory.getBookId());
							throw new OLMSException("Unable to remove book copy : " + bookInventory.getBookId());
						}
					}
				}
			}
		} catch (DataAccessException e) {
			log.error("Error while deleting copies of a book", e);
			throw new OLMSException("Error while deleting copies of a book", e);
		}
	}

	
	//excepion
	//change
	@Override
	public void addBookCopies(Long noOfCopies, Long isbn) throws OLMSException {
		try {
			String selectSqlBook = "select * from olms_book where isbn=? and status=?";
			Book book = jdbcTemplate.queryForObject(selectSqlBook, new Object[] { isbn, Constants.NORMAL_STATUS },
					new BookMapper());
			int count = 0, countBook = 0;
			String seqSelectSql = "select seq_olms_book_inventory.nextval from dual";
			StringBuilder insertSql = new StringBuilder();
			insertSql.append(" insert into olms_book_inventory ");
			insertSql.append(" (book_inventory_pk, book_id, ");
			insertSql.append(" book_pk, ");
			insertSql.append(" status, available ,created_by, ");
			insertSql.append(" creation_date, updated_by, update_date, cc_check) ");
			insertSql.append(" values(?,?,?,?,'y',?,?,?,?,?)");

			String updateSqlBook = "update olms_book set available_for_issue =? ,no_of_copies=?,cc_check=? where book_pk=?";
			for (int i = 0; i < noOfCopies; i++) {
				Long inventoryPk = jdbcTemplate.queryForList(seqSelectSql, Long.class).get(0);
				String bookId = isbn + "-" + inventoryPk;
				count = jdbcTemplate.update(insertSql.toString(), new Object[] { inventoryPk, bookId, book.getBookPk(),
						Constants.NORMAL_STATUS, Constants.SYSTEM, new Date(), Constants.SYSTEM, new Date(), 1 });
				if (count == 0) {
					log.error("Unable to insert book copies ");
					throw new OLMSException("Unable to insert book copies ");
				} else {
					book = jdbcTemplate.queryForObject(selectSqlBook, new Object[] { isbn, Constants.NORMAL_STATUS },
							new BookMapper());
					countBook = jdbcTemplate.update(updateSqlBook, book.getAvailableBook() + 1, book.getNoOfBook() + 1,
							book.getCcCheck() + 1, book.getBookPk());
					if (countBook == 0) {
						log.error("Unable to insert book copies ");
						throw new OLMSException("Unable to insert book copies ");
					}
				}
			}
		} catch (DataAccessException e) {
			log.error("Unable to insert book copies ", e);
			throw new OLMSException("Unable to insert book copies ", e);
		}
	}

	//change
	@Override
	public boolean isIsbnExist(String isbn) throws OLMSException {
		try {
			// create the query
			StringBuilder sql = new StringBuilder().append("SELECT COUNT(*) FROM olms_book")
					.append(" WHERE isbn=? and status=?");

			log.debug("Sql to fetch all the User Id : " + sql.toString());
			// Instantiate the JDBCTemplate with the data source

			// Execute the query and fetch the count
			int count = jdbcTemplate.queryForObject(sql.toString(),
					new Object[] { Long.parseLong(isbn), Constants.NORMAL_STATUS }, Integer.class);

			// If count is greater then 0, that means ISBN already exist with
			// the same user id.
			// Hence return true. Otherwise, return false.
			if (count > 0) {
				return true;
			} else {
				return false;
			}
		} catch (DataAccessException e) {
			log.error("Failed to fetch the count of records from book table", e);
			throw new OLMSException("Failed to fetch the count of records from book table");
		}
	}
}