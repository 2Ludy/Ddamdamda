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

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/routine")
//@CrossOrigin("*")
public class RoutineController {
	
	@Autowired
	private RoutineService routineService;
	
	@Operation(summary = "특정 유저의 모든 루틴을 조회", description = "userId를 이용하여 모든 루틴을 조회하는 메서드")
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
	
	@Operation(summary = "특정 유저의 특정 날짜의 routine 조회", description = "Post로 바꿀까 생각중... / exerciseDate와 userId를 이용해 특정 날짜의 루틴를 모두 가져오는데, like로 구현되어 exerciseDate가 '2024'라면 2024년의 모든 루틴, '2024-11'라면 2024년 11월의 모든 루틴, '2024-11-20'라면 2024년 11월 20일의 모든 루틴을 가져오는 방식")
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
	
	@Operation(summary = "루틴 정보 조회", description = "id를 이용하여 해당 루틴의 정보를 조회하는 메서드")
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
	
	@Operation(summary = "루틴 추가", description = "Routine DTO의 userId, title, exerciseDate, sets, reps, exercisesId 를 이용하여 루틴 추가하는 메서드")
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
	
	@Operation(summary = "루틴 수정", description = "Routine DTO의 id, sets, reps를 이용하여 루틴을 수정하는 메서드")
	@PutMapping("/edit")
	public  ResponseEntity<?> editRoutine(@RequestBody Routine routine) { // int id, int sets, int reps
		try {
			int isS = routineService.updateSetsReps(routine);
			if(isS > 0) {
				return new ResponseEntity<>(new ApiResponse("Success", "updateSetsReps", 201), HttpStatus.CREATED);
			}else {
				return new ResponseEntity<>(new ApiResponse("Fail", "updateSetsReps", 404), HttpStatus.NOT_FOUND);
			}
		}catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("Error", "updateSetsReps", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Operation(summary = "루틴 완료", description = "Routine의 id를 이용하여 해당 routine을 완료 상태로 바꿔주는 메서드")
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
	
	@Operation(summary = "루틴 삭제", description = "Routine의 id를 루틴을 이용하여 삭제하는 메서드")
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
	
	@Operation(summary = "특정 유저의 선택된 날짜의 루틴 개수 조회", description = "Post로 바꿀까 생각중... / exerciseDate와 userId를 이용해 특정 날짜의 루틴개수를 가져오는데, like로 구현되어 exerciseDate가 '2024'라면 2024년의 모든 루틴개수, '2024-11'라면 2024년 11월의 모든 루틴개수, '2024-11-20'라면 2024년 11월 20일의 모든 루틴개수를 가져오는 방식")
	@GetMapping("/count")
	public  ResponseEntity<?> getCountRoutine(@RequestParam String exerciseDate, @RequestParam int userId) { // String exerciseDate, int userId
		try {
				int count = routineService.selectCountDayRoutine(exerciseDate, userId);
				return new ResponseEntity<Integer>(count, HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("Error", "getCountRoutine", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Operation(summary = "특정 유저의 선택된 날짜의 완료된 루틴 개수 조회", description = "Post로 바꿀까 생각중... / exerciseDate와 userId를 이용해 특정 날짜의 완료된루틴개수를 가져오는데, like로 구현되어 exerciseDate가 '2024'라면 2024년의 모든 완료루틴개수, '2024-11'라면 2024년 11월의 모든 완료루틴개수, '2024-11-20'라면 2024년 11월 20일의 모든 완료루틴개수를 가져오는 방식")
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
