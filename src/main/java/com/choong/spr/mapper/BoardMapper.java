package com.choong.spr.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.choong.spr.domain.BoardDto;

public interface BoardMapper {


	BoardDto getBoard(int id);

	int updateBoard(BoardDto board);

	int deleteBoard(int id);

	int insertBoard(BoardDto board);

	int countBoard();

	List<BoardDto> selectBoard();

	List<BoardDto> listBoardPage(@Param("from") int from, @Param("rowPerPage") int rowPerPage);
	
	
	
	//(@Param("from") int from, @Param("rowPerPage") int rowPerPage);


}





