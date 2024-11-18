package com.ddam.damda.routine.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Routine {
	
	private int id;
	private int userId;
	private String title;
	private String exerciseDate;
	private int sets;
	private int reps;
	private int exercisesId;
	private int isCompleted;

}
