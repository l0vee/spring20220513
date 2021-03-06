package com.choong.spr.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.choong.spr.domain.BoardDto;
import com.choong.spr.mapper.BoardMapper;
import com.choong.spr.mapper.ReplyMapper;

@Service
public class BoardService {

	@Autowired
	private BoardMapper mapper;
	
	@Autowired
	private ReplyMapper replyMapper;

	public List<BoardDto> listBoard(String type, String keyword) {
		// TODO Auto-generated method stub
		return mapper.selectBoardAll(type,"%"+keyword+"%");
	}

	public boolean insertBoard(BoardDto board) {
		// TODO Auto-generated method stub
		return mapper.insertBoard(board) == 1;
		//게시물 하나가 잘 추가 됐는지
	}

	public BoardDto getBoardById(int id) {
		// TODO Auto-generated method stub
		return mapper.selectBoardById(id);
		//int (몇번째 게시물인지)
	}

	public boolean updateBoard(BoardDto dto) {
		// TODO Auto-generated method stub
		return mapper.updateBoard(dto) == 1;
		//하나 잘 됐는지 boolean이라 true면 1?
	}

	@Transactional
	public boolean deleteBoard(int id) {
		
		replyMapper.deleteByBoardId(id);
		
		return mapper.deleteBoard(id) == 1;
	}
	
}

	