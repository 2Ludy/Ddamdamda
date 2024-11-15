package com.ddam.damda.exercises.model;

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

public class Exercises {
	
	private int id;
	private String part;
	private String name;
	private String videoUrl;
	
}
