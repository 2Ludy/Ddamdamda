package com.ddam.damda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.ddam.damda.exercises.model.Exercises;
import com.ddam.damda.exercises.model.service.ExercisesService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Slf4j
@RestController
@CrossOrigin("*")
public class ExercisesController {
	
	@Autowired
	private ExercisesService exercisesService;
	
	@PostMapping("/exercises")
	public ResponseEntity<?> postMethodName(@RequestBody Exercises exercises) {
		int isS = exercisesService.insertExercises(exercises);
		if(isS > 0 ) return new ResponseEntity<String>("Success", HttpStatus.CREATED);
		return new ResponseEntity<String>("Fail", HttpStatus.NOT_FOUND);
	}
	
}
