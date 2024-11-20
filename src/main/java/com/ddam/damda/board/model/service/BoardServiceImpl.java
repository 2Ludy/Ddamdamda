package com.ddam.damda.board.model.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
		Board board = boardMapper.selectBoard(id);
		if (board != null) {
            board.setImages(boardImageService.findByBoardId(id));
        }
		return board;
	}

	@Transactional
	@Override
	public int insertBoard(Board board, List<MultipartFile> images) throws IOException {
        // 1. 게시글 저장
        int result = boardMapper.insertBoard(board);
        if (result <= 0) {
            throw new RuntimeException("게시글 저장 실패");
        }

        // 2. 이미지 처리
        if (images != null && !images.isEmpty()) {
            List<BoardImage> savedImages = new ArrayList<>();
            for (MultipartFile image : images) {
                if (!image.isEmpty()) {
                    BoardImage savedImage = boardImageService.saveBoardImage(image, board.getId());
                    savedImages.add(savedImage);
                }
            }
            board.setImages(savedImages);
        }
        return result;
    }

	@Transactional
	@Override
	public int deleteBoard(int id) throws IOException {
		boardImageService.deleteAllByBoardId(id);
		return boardMapper.deleteBoard(id);
	}

	@Transactional
	@Override
	public int updateBoard(Board board, List<MultipartFile> newImages, List<Integer> deleteImageIds) throws IOException {
        // 1. 게시글 수정
        int result = boardMapper.updateBoard(board);
        if (result <= 0) {
            throw new RuntimeException("게시글 수정 실패");
        }

        // 2. 이미지 삭제 처리
        if (deleteImageIds != null && !deleteImageIds.isEmpty()) {
            for (Integer imageId : deleteImageIds) {
                boardImageService.deleteImage(imageId);
            }
        }

        // 3. 새 이미지 추가
        if (newImages != null && !newImages.isEmpty()) {
            List<BoardImage> currentImages = board.getImages() != null ? 
                board.getImages() : new ArrayList<>();
                
            for (MultipartFile image : newImages) {
                if (!image.isEmpty()) {
                    BoardImage savedImage = boardImageService.saveBoardImage(image, board.getId());
                    currentImages.add(savedImage);
                }
            }
            board.setImages(currentImages);
        }

        return result;
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
