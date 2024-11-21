package com.ddam.damda.routine.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class RoutineRecommendationRequest {
	
    private String experienceLevel;  // beginner, intermediate, advanced
    private List<String> purposes;   // muscle, strength, endurance 등
    private List<String> targetAreas; // chest, back, shoulder 등
    private int duration;            // 운동 시간(분)

}
