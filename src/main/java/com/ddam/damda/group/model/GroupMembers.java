package com.ddam.damda.group.model;

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
public class GroupMembers {
	
	private int id;
	private int groupId;
	private int userId;
	private String createdAt;

}
