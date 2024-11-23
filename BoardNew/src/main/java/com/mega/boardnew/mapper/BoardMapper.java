 package com.mega.boardnew.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mega.boardnew.bean.BoardVO;

@Mapper
public interface BoardMapper {
	// 게시글 목록
	public List<BoardVO> getList();
	
	// 게시글 추가
	public int insert(BoardVO vo);
	public int insertSelectKey(BoardVO vo);
	
	// 게시글 읽기
	public BoardVO read (Long bno);
	
	// 게시글 삭제 글번호만으로 지울 수 있음
	public int delete(Long bno);
	
	//게시글 수정 내용이필요함
	public int update(BoardVO board);
}
