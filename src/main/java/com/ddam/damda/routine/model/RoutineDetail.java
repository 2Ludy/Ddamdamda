package com.ddam.damda.routine.model;

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

public class RoutineDetail {
	
    private int exerciseId;
    private String title;
    private int sets;
    private int reps;
    private String note;

}
