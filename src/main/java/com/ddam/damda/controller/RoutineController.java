package com.ddam.damda.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.ddam.damda.routine.model.RoutineRecommendationRequest;
import com.ddam.damda.routine.model.RoutineRecommendationResponse;
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
				return new ResponseEntity<>(new ApiResponse("Fail", "getAllRoutine", 400), HttpStatus.BAD_REQUEST);
			}
		}catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("Error", "getAllRoutine", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Operation(summary = "특정 유저의 특정 날짜의 routine 조회", description = "Routine DTO의 exerciseDate와 userId를 이용해 특정 날짜의 루틴를 모두 가져오는데, like로 구현되어 exerciseDate가 '2024'라면 2024년의 모든 루틴, '2024-11'라면 2024년 11월의 모든 루틴, '2024-11-20'라면 2024년 11월 20일의 모든 루틴을 가져오는 방식")
	@PostMapping("/date")
	public  ResponseEntity<?> getDateRoutine(@RequestBody Routine routine) { // String exerciseDate, int userId
		try {
			List<Routine> list = routineService.selectDateRoutineByUserId(routine);
			if(list.size() != 0) {
				return new ResponseEntity<List<Routine>>(list, HttpStatus.OK);
			}else {
				return new ResponseEntity<>(new ApiResponse("Fail", "getDateRoutine", 400), HttpStatus.BAD_REQUEST);
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
				return new ResponseEntity<>(new ApiResponse("Fail", "getRoutine", 400), HttpStatus.BAD_REQUEST);
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
				return new ResponseEntity<>(new ApiResponse("Fail", "updateSetsReps", 400), HttpStatus.BAD_REQUEST);
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
				return new ResponseEntity<>(new ApiResponse("Fail", "completeRoutine", 400), HttpStatus.BAD_REQUEST);
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
				return new ResponseEntity<>(new ApiResponse("Fail", "deleteRoutine", 400), HttpStatus.BAD_REQUEST);
			}
		}catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("Error", "deleteRoutine", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Operation(summary = "특정 유저의 선택된 날짜의 루틴 개수 조회", description = "Routine DTO의 exerciseDate와 userId를 이용해 특정 날짜의 루틴개수를 가져오는데, like로 구현되어 exerciseDate가 '2024'라면 2024년의 모든 루틴개수, '2024-11'라면 2024년 11월의 모든 루틴개수, '2024-11-20'라면 2024년 11월 20일의 모든 루틴개수를 가져오는 방식")
	@PostMapping("/count")
	public  ResponseEntity<?> getCountRoutine(@RequestBody Routine routine) { // String exerciseDate, int userId
		try {
				int count = routineService.selectCountDayRoutine(routine);
				return new ResponseEntity<Integer>(count, HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("Error", "getCountRoutine", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Operation(summary = "특정 유저의 선택된 날짜의 완료된 루틴 개수 조회", description = "Routine DTO의 exerciseDate와 userId를 이용해 특정 날짜의 완료된루틴개수를 가져오는데, like로 구현되어 exerciseDate가 '2024'라면 2024년의 모든 완료루틴개수, '2024-11'라면 2024년 11월의 모든 완료루틴개수, '2024-11-20'라면 2024년 11월 20일의 모든 완료루틴개수를 가져오는 방식")
	@PostMapping("/count/complete")
	public  ResponseEntity<?> getCountCompleteRoutine(@RequestBody Routine routine) { // String exerciseDate, int userId
		try {
				int count = routineService.selectCountDayCompletedRoutine(routine);
				return new ResponseEntity<Integer>(count, HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("Error", "getCountRoutine", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// 1. AI 추천 받기
    @Operation(summary = "AI 맞춤 루틴 추천 받기", 
            description = "사용자의 운동 경험, 목적, 부위, 시간을 고려하여 AI가 루틴을 추천하는 메서드")
  @PostMapping("/ai-recommendation")
  public ResponseEntity<?> getAiRecommendation(@RequestBody RoutineRecommendationRequest request) {
      try {
          RoutineRecommendationResponse response = routineService.getAiRoutineRecommendation(request);
          
          if (response != null && !response.getRoutines().isEmpty()) {
              return new ResponseEntity<>(response, HttpStatus.OK);
          } else {
              return new ResponseEntity<>(
                  new ApiResponse("Fail", "No suitable exercises found", 400),
                  HttpStatus.BAD_REQUEST
              );
          }
      } catch (Exception e) {
          e.printStackTrace();
          return new ResponseEntity<>(
              new ApiResponse("Error", "getAiRecommendation", 500),
              HttpStatus.INTERNAL_SERVER_ERROR
          );
      }
  }
  
  // 2. 추천받은 루틴 저장하기
  @Operation(summary = "AI 추천 루틴을 Routine 객체로 변환", 
            description = "AI가 추천한 루틴리스트를을 사Routine 객체로 변환 메서드")
  @PostMapping("/ai-recommendation/convert")
  public ResponseEntity<?> convertToRoutines(@RequestBody RoutineRecommendationResponse recommendationResponse) {
      try {
          List<Routine> convertedRoutines = routineService.convertToRoutines(recommendationResponse);
          
          if (!convertedRoutines.isEmpty()) {
              return new ResponseEntity<>(convertedRoutines, HttpStatus.CREATED);
          } else {
              return new ResponseEntity<>(
                  new ApiResponse("Fail", "Failed to save routines", 400),
                  HttpStatus.BAD_REQUEST
              );
          }
      } catch (Exception e) {
          e.printStackTrace();
          return new ResponseEntity<>(
              new ApiResponse("Error", "saveAiRecommendation", 500),
              HttpStatus.INTERNAL_SERVER_ERROR
          );
      }
  }
}
