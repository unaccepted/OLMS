package com.nrifintech.olmsb.repository;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.nrifintech.olmsb.AbstractTestCase;
import com.nrifintech.olmsb.dao.MemberRegistrationDao;
import com.nrifintech.olmsb.dto.MemberRegistrationDto;

public class MemberRegistrationDaoImplTestCase extends AbstractTestCase {
	
	@Autowired
	private MemberRegistrationDao memberRegistrationDao;
	
	@Test
	@Transactional
	public void testAddNewMember() throws Exception {
		
		MemberRegistrationDto memberRegistrationDto  =new MemberRegistrationDto();
		memberRegistrationDto.setUserId("AmolShankar");
		memberRegistrationDto.setPassword("123456789");
		memberRegistrationDto.setConfirmPassword("123456789");
		memberRegistrationDto.setFirstName("AMOL");
		memberRegistrationDto.setLastName("SHANKAR");
		
		memberRegistrationDao.addNewMember(memberRegistrationDto);
		assertEquals(true,memberRegistrationDao.isUserExist("AmolShankar"));
	}	
	
	@Test
	@Transactional
	public void testIsUserExistWithInvalidUser() throws Exception {
		
		MemberRegistrationDto memberRegistrationDto  =new MemberRegistrationDto();
		memberRegistrationDto.setUserId("AmolShankar");
		memberRegistrationDto.setPassword("123456789");
		memberRegistrationDto.setConfirmPassword("123456789");
		memberRegistrationDto.setFirstName("AMOL");
		memberRegistrationDto.setLastName("SHANKAR");
		
		memberRegistrationDao.addNewMember(memberRegistrationDto);
		Assert.assertFalse(memberRegistrationDao.isUserExist("AmolShankarRao"));
	}	

	@Test
	@Transactional
	public void testIsUserExist() throws Exception {
		
		MemberRegistrationDto memberRegistrationDto  =new MemberRegistrationDto();
		memberRegistrationDto.setUserId("AmolShankar");
		memberRegistrationDto.setPassword("123456789");
		memberRegistrationDto.setConfirmPassword("123456789");
		memberRegistrationDto.setFirstName("AMOL");
		memberRegistrationDto.setLastName("SHANKAR");
		
		memberRegistrationDao.addNewMember(memberRegistrationDto);
		
		assertEquals(true,memberRegistrationDao.isUserExist("AmolShankar"));
	}	
}
