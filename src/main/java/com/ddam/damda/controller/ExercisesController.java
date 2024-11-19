package com.ddam.damda.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ddam.damda.exercises.model.Exercises;
import com.ddam.damda.exercises.model.service.ExercisesService;
import com.ddam.damda.jwt.model.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/exercises")
@CrossOrigin("*")
public class ExercisesController {
	
	@Autowired
	private ExercisesService exercisesService;
	
	@Operation(summary = "Exercises를 추가", description = "여긴 사실 사용 될 일이 없음 admin 구현되면 사용..?")
	@PostMapping("")
	public ResponseEntity<?> addExercises(@RequestBody Exercises exercises) {
		try {
			int isS = exercisesService.insertExercises(exercises);
			if(isS > 0 ) return new ResponseEntity<>(new ApiResponse("Success", "addExercieses", 201), HttpStatus.CREATED);
			return new ResponseEntity<>(new ApiResponse("Fail", "addExercieses", 400), HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("Error", "addExercises", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Operation(summary = "Exercises 모든 정보를 조회", description = "Exercises안에 담겨 있는 모든 정보를 반환")
	@GetMapping("")
	public  ResponseEntity<?> getAllExercises() {
		try {
			List<Exercises> list = exercisesService.selectAllExercises();
			if(list.size() != 0) {
				return new ResponseEntity<List<Exercises>>(list, HttpStatus.CREATED);
			}else {
				return new ResponseEntity<>(new ApiResponse("Fail", "getAllExercises", 400), HttpStatus.BAD_REQUEST);
			}
		}catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("Error", "getAllExercises", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Operation(summary = "part 별 Exericses 정보 조회", description = "part는 partId를 임의로 만들어서 넘기면 될듯 아래의 partId를 따라서 int를 넘기면 됩니다.")
	@GetMapping("/{partId}")
	public  ResponseEntity<?> getExercisesByPart(@PathVariable int partId) { // partId => 1 : 가슴 / 2 : 등 / 3 : 어깨 / 4 : 팔 / 5 : 코어 / 6 : 하체 / 7 : 전신
		String part = "";
		try {
			switch (partId) {
				case 1: part = "가슴";
						break;
				case 2: part = "등";
						break;
				case 3: part = "어깨";
						break;
				case 4: part = "팔";
						break;
				case 5: part = "코어";
						break;		
				case 6: part = "하체";
						break;
				case 7: part = "전신";
						break;
			}
			
			List<Exercises> list = exercisesService.selectExercisesByPart(part);
			if(list.size() != 0) {
				return new ResponseEntity<List<Exercises>>(list, HttpStatus.CREATED);
			}else {
				return new ResponseEntity<>(new ApiResponse("Fail", "getAllExercises", 400), HttpStatus.BAD_REQUEST);
			}
		}catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("Error", "getAllExercises", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
