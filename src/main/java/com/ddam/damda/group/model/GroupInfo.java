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
	
	int groupId;
	String groupName;
	String description;
	int adminId;
	int groupImg;
	String mateStatus;
	String region;
	String exerciseType;
	int currentMembers;
	int memberCount;

}
