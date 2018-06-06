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

import com.nrifintech.olmsb.domain.MemberBook;
import com.nrifintech.olmsb.dto.BookDto;
import com.nrifintech.olmsb.exception.OLMSException;
/**
 * Service layer contract for book search and issue functionality for a member.
 * @author amols
 *
 */
public interface MemberBookService {
	
	/**
	 * This API is responsible for fetching a list of books against a particular query
	 * @param bookDto
	 *           BookDto
	 * @return OLMSModelMap
	 * @throws OLMSException 
	 */
	public List<MemberBook> findBook(BookDto bookDto) throws OLMSException;
	/**
	 * This API is responsible for validating ISBN.
	 * @param bookDto
	 *            BookDto
	 * @return OLMSModelMap
	 */
	public List<String> validate(BookDto bookDto); 
	/**
	 * This API is responsible for issuing book.
	 * @param bookPks
	 * @param userId
	 * @throws OLMSException
	 */
	public void issueBooksForParticularUser(List<Long> bookPks, String userId) throws OLMSException;
}
