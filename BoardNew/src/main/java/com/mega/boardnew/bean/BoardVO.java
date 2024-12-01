package com.mega.boardnew.bean;

import java.util.List;

import lombok.Data;

@Data
public class BoardVO {
	private Long bno;
	private String title;
	private String content;
	private String writer;
	private String regdate;
	private String updatedate;
	private List<AttachFileVO> attachList;
	
	// input tag의 name에
	// name="bno" 형식으로 작성하나 배열인경우 
	// attachList[i].fileName
	// attachList[i].uuid
	// attachList[i].uploadPath
	// attachList[i].fileType
	// 방식으로 submit 하면 자동으로 List에 add 된다.
	
}
