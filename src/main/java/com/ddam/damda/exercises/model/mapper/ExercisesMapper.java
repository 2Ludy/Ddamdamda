package com.ddam.damda.exercises.model.mapper;

import java.util.List;

import com.ddam.damda.exercises.model.Exercises;

public interface ExercisesMapper {
	
	int insertExercises(Exercises exercises);
	
	List<Exercises> selectAllExercises();
	
	List<Exercises> selectExercisesByPart(String part);
	
}
