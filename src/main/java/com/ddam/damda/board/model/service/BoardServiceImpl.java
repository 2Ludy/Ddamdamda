package com.ddam.damda.board.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddam.damda.board.model.Board;
import com.ddam.damda.board.model.mapper.BoardMapper;
import com.ddam.damda.common.util.PageRequest;
import com.ddam.damda.images.model.BoardImage;
import com.ddam.damda.images.model.service.BoardImageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private BoardMapper boardMapper;
	
	@Autowired
	private BoardImageService boardImageService;

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
		updateViewCount(id);
		return boardMapper.selectBoard(id);
	}

	@Transactional
	@Override
	public int insertBoard(Board board) {
		return boardMapper.insertBoard(board);
	}

	@Transactional
	@Override
	public int deleteBoard(int id) {
		return boardMapper.deleteBoard(id);
	}

	@Transactional
	@Override
	public int updateBoard(Board board) {
		return boardMapper.updateBoard(board);
	}

	@Transactional
	@Override
	public int updateViewCount(int id) {
		return boardMapper.updateViewCount(id);
	}

	@Transactional
	@Override
	public int increaseLikesCount(int id) {
		return boardMapper.increaseLikesCount(id);
	}
	
	@Transactional
	@Override
	public int decreaseLikesCount(int id) {
		return boardMapper.decreaseLikesCount(id);
	}

	@Transactional
	@Override
	public int increaseCommentsCount(int id) {
		return boardMapper.increaseCommentsCount(id);
	}
	
	@Transactional
	@Override
	public int decreaseCommentsCount(int id) {
		return boardMapper.decreaseCommentsCount(id);
	}

}
