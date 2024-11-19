package com.ddam.damda.board.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Comment {
	
	private int id;
	private int userId;
	private String content;
	private int boardId;
	private String createdAt;
	private String username;

}
