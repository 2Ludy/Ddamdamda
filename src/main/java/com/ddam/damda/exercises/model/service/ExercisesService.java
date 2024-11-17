package com.ddam.damda.exercises.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ddam.damda.exercises.model.Exercises;

public interface ExercisesService {
	
	int insertExercises(Exercises exercises);
	
	List<Exercises> selectAllExercises();
	
	List<Exercises> selectExercisesByPart(String part);

}
