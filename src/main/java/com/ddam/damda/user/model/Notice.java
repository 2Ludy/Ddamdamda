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
	
	int id;
	int userId;
	String content;
	int referenceId;
	String referenceType;
	String createdAt;
	int isRead;

}
