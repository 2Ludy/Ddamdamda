package com.ddam.damda.board.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Board {
	
	int id;
	int userId;
	String category;
	String title;
	String content;
	int viewCount;
	int likesCount;
	int commentsCount;
	String createdAt;

}
