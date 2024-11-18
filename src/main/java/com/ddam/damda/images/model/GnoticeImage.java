package com.ddam.damda.images.model;

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
public class GnoticeImage {
	
	private int id;
	private int gnoticeId;
	private String filePath;
	private String fileName;
	private String fileType;

}
