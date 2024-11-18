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
public class Likes {
	
	private int id;
	private int userId;
	private int boardId;
	private String createdAt;

}
