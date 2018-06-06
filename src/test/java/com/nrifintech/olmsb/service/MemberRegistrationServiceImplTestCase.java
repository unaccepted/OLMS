package com.nrifintech.olmsb.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.nrifintech.olmsb.AbstractTestCase;
import com.nrifintech.olmsb.dao.MemberRegistrationDao;
import com.nrifintech.olmsb.dto.MemberRegistrationDto;

public class MemberRegistrationServiceImplTestCase extends AbstractTestCase {

	@Autowired
	private MemberRegistrationService memberRegistrationService;
	
	@Autowired
	private MemberRegistrationDao memberRegistrationDao;
	
	@Test
	@Transactional
	public void testAddNewMember() throws Exception {
		
		MemberRegistrationDto memberRegistrationDto=new MemberRegistrationDto();
		memberRegistrationDto.setUserId("ABCDEFGHIJJKL");
		memberRegistrationDto.setPassword("123456789");
		memberRegistrationDto.setConfirmPassword("123456789");
		memberRegistrationDto.setFirstName("AMOL");
		memberRegistrationDto.setLastName("SHANKAR");
		memberRegistrationDto.setContactNo("9874561023");
		memberRegistrationDto.setEmail("amol@gmail.com");
		
		memberRegistrationService.addNewMember(memberRegistrationDto);
		assertEquals(true,memberRegistrationDao.isUserExist("ABCDEFGHIJJKL"));
		
		memberRegistrationDto=new MemberRegistrationDto();
		memberRegistrationDto.setUserId("");
		memberRegistrationDto.setPassword("");
		memberRegistrationDto.setConfirmPassword("");
		memberRegistrationDto.setFirstName("");
		memberRegistrationDto.setLastName("");
		memberRegistrationDto.setContactNo("");
		memberRegistrationDto.setEmail("");
		
		List<String> err = memberRegistrationService.validateNewMemberDetails(memberRegistrationDto) ;
		assertEquals(3, err.size());
		memberRegistrationDto=new MemberRegistrationDto();
		memberRegistrationDto.setUserId("member");
		memberRegistrationDto.setPassword("asdasdasdasdadadadasasdasdadad");
		memberRegistrationDto.setConfirmPassword("asdasd");
		memberRegistrationDto.setFirstName("1");
		memberRegistrationDto.setLastName("1");
		memberRegistrationDto.setContactNo("123");
		memberRegistrationDto.setEmail("123");
		
		err = memberRegistrationService.validateNewMemberDetails(memberRegistrationDto) ;
		assertEquals(7, err.size());
		
		List<String> error = memberRegistrationService.validateNewMemberDetails(memberRegistrationDto) ;
		assertEquals(7, err.size());
		memberRegistrationDto=new MemberRegistrationDto();
		memberRegistrationDto.setUserId("ooooooooooooooooooooooooooo");
		memberRegistrationDto.setPassword("asdasdasdasdadadadasasdasdadad");
		memberRegistrationDto.setConfirmPassword("asdasd");
		memberRegistrationDto.setFirstName("aaaaaaaaaaaaaaaaaaaaaaaaa");
		memberRegistrationDto.setLastName("bbbbbbbbbbbbbbbbbbbbbbbbbb");
		memberRegistrationDto.setContactNo("123");
		memberRegistrationDto.setEmail("qwertyuiopasdfghjklzxcvbnmqwertyuiop@gmail.com");
		
		error = memberRegistrationService.validateNewMemberDetails(memberRegistrationDto) ;
		assertEquals(7, error.size());
		
		
	}	

}
