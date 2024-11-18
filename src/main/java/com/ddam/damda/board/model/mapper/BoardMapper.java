package com.ddam.damda.board.model.mapper;

import java.util.List;

import com.ddam.damda.board.model.Board;
import com.ddam.damda.common.util.PageRequest;

public interface BoardMapper {
	
	List<Board> selectAllBoards(PageRequest preq);
	
	Board selectBoard(int id);
	
	int insertBoard(Board board); // user_id, category, title, content 사용
	
	int deleteBoard(int id);
	
	int updateBoard(Board board); // id, title, content 사용
	
	int updateViewCount(int id);
	
	int updateLikesCount(int id);
	
	int updateCommentsCount(int id);
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