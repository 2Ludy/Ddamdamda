package com.ddam.damda.board.model;

import java.util.List;

import com.ddam.damda.images.model.BoardImage;

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
	
	private int id;
	private int userId;
	private String category;
	private String title;
	private String content;
	private int viewCount;
	private int likesCount;
	private int commentsCount;
	private String createdAt;

}
