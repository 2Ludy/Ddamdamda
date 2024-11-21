package com.ddam.damda.routine.model;

import java.util.List;

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
public class RoutineRecommendationResponse {
	
	private List<RoutineDetail> routines;
    private int totalDuration;
    private String recommendation;
    private int userId;

}
