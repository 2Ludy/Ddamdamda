package com.ddam.damda.board.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddam.damda.board.model.Board;
import com.ddam.damda.board.model.mapper.BoardMapper;
import com.ddam.damda.common.util.PageRequest;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private BoardMapper boardMapper;

	@Transactional
	@Override
	public PageInfo<Board> selectAllBoards(PageRequest preq) {
		PageHelper.startPage(preq.getPageNum(), preq.getPageSize());
		List<Board> list = boardMapper.selectAllBoards(preq);
		PageInfo<Board> page = new PageInfo<Board>(list);
		return page;
	}

	@Transactional
	@Override
	public Board selectBoard(int id) {
		// 이미지도 불러와야하는데 음... 어캐해야하지??
		return boardMapper.selectBoard(id);
	}

	@Transactional
	@Override
	public int insertBoard(Board board) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Transactional
	@Override
	public int deleteBoard(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Transactional
	@Override
	public int updateBoard(Board board) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Transactional
	@Override
	public int updateViewCount(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Transactional
	@Override
	public int updateLikesCount(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Transactional
	@Override
	public int updateCommentsCount(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

}
