package com.ddam.damda.images.model.mapper;

import java.util.List;

import com.ddam.damda.images.model.BoardImage;

public interface BoardImageMapper {
    void insertBoardImage(BoardImage boardImage);
    List<BoardImage> selectByBoardId(int boardId);
    BoardImage selectById(int id);
    void deleteById(int id);
    void deleteByBoardId(int boardId);
}