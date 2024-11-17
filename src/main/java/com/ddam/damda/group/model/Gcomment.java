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
public class Gcomment {
	
	int id;
	int userId;
	String content;
	int gnoticeId;
	String createdAt;

}
