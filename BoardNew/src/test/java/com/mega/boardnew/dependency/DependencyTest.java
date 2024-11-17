package com.mega.boardnew.dependency;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mega.boardnew.utill.Color;

import lombok.extern.slf4j.Slf4j;


@SpringBootTest
@Slf4j
public class DependencyTest {
		@Autowired
		Coding coding;
		
		@Autowired
		Color color;
		
		@BeforeAll
		public static void initTest() {
			System.out.println("테스트시작");
			log.info(Color.BOLD + "테스트가 시작 됩니다. BeforAll" +Color.END);
		}
		
		@BeforeEach
		public void initTest2() {
			log.info(Color.BOLD + "테스트가 시작 됩니다.BeforeEach" +Color.END);
		}
		
		@Test
		public void testSample() {
			log.info("testSample logging...");
		}
		
		@Test
		public void testSample2() {
			log.info(Color.BLUE + Color.BOLD + "testSample2 logging..." + Color.END);
		}
		
		@Test
		public void testCoding() {
			
			log.info("" + coding.toString());
		}
}
