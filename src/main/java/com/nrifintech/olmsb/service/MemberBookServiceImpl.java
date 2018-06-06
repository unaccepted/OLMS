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
import com.nrifintech.olmsb.dao.MemberBookDao;
import com.nrifintech.olmsb.domain.MemberBook;
import com.nrifintech.olmsb.dto.BookDto;
import com.nrifintech.olmsb.exception.OLMSException;
@Service
public class MemberBookServiceImpl implements MemberBookService {
	
	private final Logger log = LoggerFactory.getLogger(BookCategoryServiceImpl.class);
	
	@Autowired
	private MemberBookDao memberBookDao;
	
	@Override
	public List<MemberBook> findBook(BookDto bookDto) throws OLMSException {

		return memberBookDao.findBook(bookDto);
	}
	
	@Override
	@Transactional
    public void issueBooksForParticularUser(List<Long> bookPks, String userId) throws OLMSException {
		memberBookDao.issueBooksForParticularUser(bookPks, userId);		
	}
	
	
	@Override
	public List<String> validate(BookDto bookDto) {
		List<String> errors = new ArrayList<>();
			String isbn = bookDto.getIsbn();
			if(!StringUtils.isBlank(isbn)){
			       if(!StringUtils.isNumeric(isbn)){
				            log.error("ISBN must be a number");
				            errors.add("ISBN must be a number");
			        }
			}
		return errors;
	}
}
