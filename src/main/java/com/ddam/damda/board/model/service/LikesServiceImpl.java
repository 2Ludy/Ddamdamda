package com.ddam.damda.board.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddam.damda.board.model.Likes;
import com.ddam.damda.board.model.mapper.LikesMapper;

@Service
public class LikesServiceImpl implements LikesService {
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private LikesMapper likesMapper;

	@Override
	@Transactional
	public Likes selectLikes(int id) {
		return likesMapper.selectLikes(id);
	}

	@Override
	@Transactional
	public Likes haveLikes(Likes likes) {
		return likesMapper.haveLikes(likes);
	}

	@Override
	@Transactional
	public int insertLikes(Likes likes) {
		int boardId = likes.getBoardId();
		boardService.increaseLikesCount(boardId);
		return likesMapper.insertLikes(likes);
	}

	@Override
	@Transactional
	public int deleteLikes(int id) {
		Likes likes = selectLikes(id);
		int boardId = likes.getBoardId();
		boardService.decreaseLikesCount(boardId);
		return likesMapper.deleteLikes(id);
	}


}
