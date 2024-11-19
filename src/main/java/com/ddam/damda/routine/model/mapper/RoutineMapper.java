package com.ddam.damda.routine.model.mapper;

import java.util.List;

import com.ddam.damda.routine.model.Routine;

public interface RoutineMapper {
	
	List<Routine> selectAllRoutineByUserId(int userId);
	
	List<Routine> selectDateRoutineByUserId(Routine routine); // String exerciseDate, int userId
	
	Routine selectRoutineById(int id);
	
	int insertRoutine(Routine routine);
	
	int updateSetsReps(Routine routine); // int id, int sets, int reps
	
	int updateIsCompleted(int id);
	
	int deleteRoutineById(int id);
	
	int selectCountDayRoutine(Routine routine); // String exerciseDate, int userId
	
	int selectCountDayCompletedRoutine(Routine routine); // String exerciseDate, int userId
	
}
