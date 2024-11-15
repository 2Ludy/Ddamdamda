package com.ddam.damda.exercises.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ddam.damda.exercises.model.Exercises;
import com.ddam.damda.exercises.model.mapper.ExercisesMapper;

@Service
public class ExercisesServiceImpl implements ExercisesService {
	
	@Autowired
	private ExercisesMapper exercisesMapper;

	@Override
	public int insertExercises(Exercises exercises) {
		return exercisesMapper.insertExercises(exercises);
	}

}
