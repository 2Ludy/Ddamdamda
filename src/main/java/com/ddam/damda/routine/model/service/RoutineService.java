package com.ddam.damda.routine.model.service;

import java.util.List;

import com.ddam.damda.routine.model.Routine;

public interface RoutineService {
	
	List<Routine> selectAllRoutineByUserId(int userId);
	
	List<Routine> selectDateRoutineByUserId(String exerciseDate, int userId); // String exerciseDate, int userId
	
	Routine selectRoutineById(int id);
	
	int insertRoutine(Routine routine);
	
	int updateSetsReps(int id, int sets, int reps); // int id, int sets, int reps
	
	int updateIsCompleted(int id);
	
	int deleteRoutineById(int id);
	
	int selectCountDayRoutine(String exerciseDate, int userId); // String exerciseDate, int userId
	
	int selectCountDayCompletedRoutine(String exerciseDate, int userId); // String exerciseDate, int userId

}
