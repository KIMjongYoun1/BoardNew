package com.mega.boardnew.bean;

import lombok.Data;

@Data
public class ArtVO {
	String title;
	String artist;
	String desc;
	
	public void setTtitle(String title) {
		System.out.println("세팅이 금지");
		this.title = title; 
	}
	
	public String getTitle() {
		//return "제목 조회금지";
		return this.title;
	}
}
