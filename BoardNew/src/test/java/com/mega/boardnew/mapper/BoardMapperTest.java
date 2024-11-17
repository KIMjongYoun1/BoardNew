package com.mega.boardnew.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mega.boardnew.bean.BoardVO;
import com.mega.boardnew.utill.Color;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class BoardMapperTest {
	
	@Autowired
	private BoardMapper mapper;
		//Lambda 문법
	@Test
	public void testGetList() {
	mapper.getList()
		  .forEach(board -> log.info(Color.TET + board + Color.END));
	}
	
	@Test
	public void testInsert() {
		BoardVO board = new BoardVO();
		board.setTitle("테스트 작성");
		board.setContent("테스트 작성");
		board.setWriter("테스트 작성");
		
		int result = mapper.insert(board);
		log.info(Color.GREEN + result + "건 insert 성공" + Color.END);
		board.setTitle("셀렉트를 먼저함");
		result = mapper.insertSelectKey(board);
		log.info(Color.GREEN + result + "건 insert 성공" + Color.END);
	}
	@Test
	public void testRead() {
		BoardVO board = mapper.read(8L);
		log.info(Color.RED + board + Color.END);
	}
	@Test
	public void testDelete() {
		Long bno = 8L;
		int result = mapper.delete(bno);
		if(result == 0) {
			log.info("DELETE XXX 삭제할 값이 없습니다.");
		}
		else {
			log.info(result + "건 삭제 되었습니다.");
		}
		
	}
	@Test
	public void testUpdate() {
		Long bno = 18L;
		BoardVO board = new BoardVO();
		board.setBno(bno);
		board.setTitle("수정테스트1");
		board.setContent("수정테스트12345");
		board.setWriter("Crooked Rage Dove");
		
		int result = mapper.update(board);
		if(result == 0) {
			log.info("Update XXX Update할 값이 없습니다.");
		}
		else {
			log.info(result + "건 Update 되었습니다.");
		}
	}
	
}
