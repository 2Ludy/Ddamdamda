package com.ddam.damda.board.model.mapper;

import java.util.List;

import com.ddam.damda.board.model.Comment;
import com.ddam.damda.common.util.CPageRequest;

public interface CommentMapper {
	
	List<Comment> selectAllComments(CPageRequest preq);
	
	Comment selectComment(int id);
	
	int insertComment(Comment comment); // user_id, content, board_id 사용
	
	int deleteComment(int id);
	
	int updateComment(Comment comment); // id, content   사용
	
}

/*
@Override
public PageInfo<Board> selectAllBoards(PageRequest preq) {
	
	BoardExample empcriteria=new BoardExample();
	if(preq.getKeyword()!=null && preq.getKeyword().equals("title")) {
		empcriteria.or().andTitleLike(ToLike.toLike(preq.getSearch()));
	}else if(preq.getKeyword()!=null && preq.getKeyword().equals("boardcontent")) {
		empcriteria.or().andBoardcontentLike(ToLike.toLike(preq.getSearch()));
	}
	PageHelper.startPage(preq.getPageNum(), preq.getPageSize());
	
	List<Board> list= boardDao.selectByExample(empcriteria);
    PageInfo<Board> page = new PageInfo<Board>(list);
	return page;
}

	@PostMapping("/page")
public PageInfo<Board> selectBoard(@RequestBody PageRequest preq ) throws Exception {
  return boardService.selectAllBoards(preq);
}

*/