package com.ddam.damda.user.model;

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
public class Notice {
	
	private int id;
	private int userId;
	private String content;
	private int referenceId;
	private String referenceType;
	private String createdAt;
	private int isRead;

}
