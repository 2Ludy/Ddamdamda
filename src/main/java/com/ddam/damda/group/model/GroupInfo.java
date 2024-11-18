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
public class GroupInfo {
	
	private int groupId;
	private String groupName;
	private String description;
	private int adminId;
	private int groupImg;
	private String mateStatus; // 모집중, 마감 
	private String region;
	private String exerciseType;
	private int currentMembers;
	private int memberCount;

}
