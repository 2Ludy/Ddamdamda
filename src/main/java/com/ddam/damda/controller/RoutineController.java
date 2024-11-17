package com.ddam.damda.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ddam.damda.jwt.model.ApiResponse;
import com.ddam.damda.routine.model.Routine;
import com.ddam.damda.routine.model.service.RoutineService;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/routine")
@CrossOrigin("*")
public class RoutineController {
	
	@Autowired
	private RoutineService routineService;
	
	@GetMapping("/detail/{userId}")
	public  ResponseEntity<?> getAllRoutine(@PathVariable int userId) {
		try {
			List<Routine> list = routineService.selectAllRoutineByUserId(userId);
			if(list.size() != 0) {
				return new ResponseEntity<List<Routine>>(list, HttpStatus.OK);
			}else {
				return new ResponseEntity<>(new ApiResponse("Fail", "getAllRoutine", 404), HttpStatus.NOT_FOUND);
			}
		}catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("Error", "getAllRoutine", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/date")
	public  ResponseEntity<?> getDateRoutine(@RequestParam String exerciseDate, @RequestParam int userId) { // String exerciseDate, int userId
		try {
			List<Routine> list = routineService.selectDateRoutineByUserId(exerciseDate, userId);
			if(list.size() != 0) {
				return new ResponseEntity<List<Routine>>(list, HttpStatus.OK);
			}else {
				return new ResponseEntity<>(new ApiResponse("Fail", "getDateRoutine", 404), HttpStatus.NOT_FOUND);
			}
		}catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("Error", "getDateRoutine", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/{id}")
	public  ResponseEntity<?> getRoutine(@PathVariable int id) {
		try {
			Routine routine = routineService.selectRoutineById(id);
			if(routine != null) {
				return new ResponseEntity<Routine>(routine, HttpStatus.OK);
			}else {
				return new ResponseEntity<>(new ApiResponse("Fail", "getRoutine", 404), HttpStatus.NOT_FOUND);
			}
		}catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("Error", "getRoutine", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("")
	public  ResponseEntity<?> addRoutine(@RequestBody Routine routine) { // String exerciseDate, int userId
		try {
			int isS = routineService.insertRoutine(routine);
			if(isS > 0) {
				return new ResponseEntity<Routine>(routine, HttpStatus.CREATED);
			}else {
				return new ResponseEntity<>(new ApiResponse("Fail", "addRoutine", 400), HttpStatus.BAD_REQUEST);
			}
		}catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("Error", "addRoutine", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/edit")
	public  ResponseEntity<?> editRoutine(@RequestParam int id, @RequestParam int sets, int reps) { // int id, int sets, int reps
		try {
			int isS = routineService.updateSetsReps(id, sets, reps);
			if(isS > 0) {
				return new ResponseEntity<>(new ApiResponse("Success", "updateSetsReps", 201), HttpStatus.CREATED);
			}else {
				return new ResponseEntity<>(new ApiResponse("Fail", "updateSetsReps", 404), HttpStatus.NOT_FOUND);
			}
		}catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("Error", "updateSetsReps", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/complete/{id}")
	public  ResponseEntity<?> completeRoutine(@PathVariable int id) {
		try {
			int isS = routineService.updateIsCompleted(id);
			if(isS > 0) {
				return new ResponseEntity<>(new ApiResponse("Success", "completeRoutine", 201), HttpStatus.CREATED);
			}else {
				return new ResponseEntity<>(new ApiResponse("Fail", "completeRoutine", 404), HttpStatus.NOT_FOUND);
			}
		}catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("Error", "completeRoutine", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/{id}")
	public  ResponseEntity<?> deleteRoutine(@PathVariable int id) {
		try {
			int isS = routineService.deleteRoutineById(id);
			if(isS > 0) {
				return new ResponseEntity<>(new ApiResponse("Success", "deleteRoutine", 201), HttpStatus.CREATED);
			}else {
				return new ResponseEntity<>(new ApiResponse("Fail", "deleteRoutine", 404), HttpStatus.NOT_FOUND);
			}
		}catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("Error", "deleteRoutine", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/count")
	public  ResponseEntity<?> getCountRoutine(@RequestParam String exerciseDate, @RequestParam int userId) { // String exerciseDate, int userId
		try {
				int count = routineService.selectCountDayRoutine(exerciseDate, userId);
				return new ResponseEntity<Integer>(count, HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("Error", "getCountRoutine", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/count/complete")
	public  ResponseEntity<?> getCountCompleteRoutine(@RequestParam String exerciseDate, @RequestParam int userId) { // String exerciseDate, int userId
		try {
				int count = routineService.selectCountDayCompletedRoutine(exerciseDate, userId);
				return new ResponseEntity<Integer>(count, HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("Error", "getCountRoutine", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}