package com.ddam.damda.group.model;

import java.util.List;

import com.ddam.damda.images.model.GnoticeImage;

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
public class GroupNotice {
	
	private int gnoticeId;
	private int groupId;
	private String title;
	private String content;
	private String createdAt;
	
	private List<GnoticeImage> images;

}
