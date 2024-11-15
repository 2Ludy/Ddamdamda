package com.ddam.damda.jwt.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Setter
@Getter
@ToString
public class ApiResponse {
	private String message; 
	private String text;
	private int code;
}
