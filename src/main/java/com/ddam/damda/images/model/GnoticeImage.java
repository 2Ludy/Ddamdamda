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
	
	int id;
	int gnoticeId;
	String filePath;
	String fileName;
	String fileType;

}
