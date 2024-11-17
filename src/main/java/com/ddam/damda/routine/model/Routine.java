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
	
	int id;
	int userId;
	String title;
	String exerciseDate;
	int sets;
	int reps;
	int exercisesId;
	int isCompleted;

}
