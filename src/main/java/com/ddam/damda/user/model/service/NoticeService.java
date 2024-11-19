package com.ddam.damda.user.model.service;

import java.util.List;

import com.ddam.damda.user.model.Notice;

public interface NoticeService {
	
	List<Notice> selectAllNotice(int userId);
	
	

}
