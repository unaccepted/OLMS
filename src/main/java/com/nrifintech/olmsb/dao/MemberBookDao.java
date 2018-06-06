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
import com.nrifintech.olmsb.exception.OLMSException;
import com.nrifintech.olmsb.domain.MemberBook;
import com.nrifintech.olmsb.dto.BookDto;
/**
 * Data access layer contract for Book Search and issue functionality for members.
 * @author amols
 *
 */
public interface MemberBookDao {
	
	/**
	 * This API is responsible for fetching a list of books according to query criteria of a member.
	 * @param bookDto
	 *           BookDto
	 * @return List<MemberBook>
	 * @throws OLMSException
	 */
	public List<MemberBook> findBook(BookDto bookDto) throws OLMSException;
	
	/**
	 * This API is responsible for making issue request against a list of books according to issue criteria of a member.
	 * @param bookPks
	 * @param userId
	 * @throws OLMSException
	 */
	public void issueBooksForParticularUser(List<Long> bookPks, String userId) throws OLMSException;
}
