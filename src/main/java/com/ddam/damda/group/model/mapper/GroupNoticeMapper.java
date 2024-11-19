package com.ddam.damda.group.model.mapper;

import java.util.List;

import com.ddam.damda.board.model.Board;
import com.ddam.damda.common.util.GNPageRequest;
import com.ddam.damda.group.model.GroupNotice;

public interface GroupNoticeMapper {
	
	List<GroupNotice> selectAllGroupNotice(GNPageRequest preq);
	
	GroupNotice selectGroupNotice(int gnoticeId);
	
	int insertGroupNotice(GroupNotice groupNotice); // user_id, category, title, content 사용
	
	int deleteGroupNotice(int gnoticeId);
	
	int updateGroupNotice(GroupNotice groupNotice); // id, title, content 사용
	
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