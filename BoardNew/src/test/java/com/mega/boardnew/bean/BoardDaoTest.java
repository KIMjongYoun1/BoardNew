package com.mega.boardnew.bean;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class BoardDaoTest {
	
	@Autowired
	private BoardDAO dao;
	
	@Test
	public void testRemove() {
		Long bno = 13L;
		if(dao.remove(bno)) {
			log.info("글번호 : " + bno + " 삭제성공");
		}
		
		else {
			log.info("이미삭제되었어");
		}
	}
	
}
