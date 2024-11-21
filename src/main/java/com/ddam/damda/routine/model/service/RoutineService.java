package com.ddam.damda.routine.model.service;

import java.util.List;

import com.ddam.damda.routine.model.Routine;
import com.ddam.damda.routine.model.RoutineRecommendationRequest;
import com.ddam.damda.routine.model.RoutineRecommendationResponse;

public interface RoutineService {
	
	List<Routine> selectAllRoutineByUserId(int userId);
	
	List<Routine> selectDateRoutineByUserId(Routine routine); // String exerciseDate, int userId
	
	Routine selectRoutineById(int id);
	
	int insertRoutine(Routine routine);
	
	int updateSetsReps(Routine routine); // int id, int sets, int reps
	
	int updateIsCompleted(int id);
	
	int deleteRoutineById(int id);
	
	int selectCountDayRoutine(Routine routine); // String exerciseDate, int userId
	
	int selectCountDayCompletedRoutine(Routine routine); // String exerciseDate, int userId
	
	 // AI 추천 받기
    RoutineRecommendationResponse getAiRoutineRecommendation(RoutineRecommendationRequest request);
    
    // AI 추천 루틴 저장하기
    List<Routine> convertToRoutines(RoutineRecommendationResponse recommendationResponse);
}
