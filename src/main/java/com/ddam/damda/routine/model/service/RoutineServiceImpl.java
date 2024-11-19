package com.ddam.damda.routine.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddam.damda.routine.model.Routine;
import com.ddam.damda.routine.model.mapper.RoutineMapper;

@Service
public class RoutineServiceImpl implements RoutineService {
	
	@Autowired
	private RoutineMapper routineMapper;

	@Override
	@Transactional
	public List<Routine> selectAllRoutineByUserId(int userId) {
		return routineMapper.selectAllRoutineByUserId(userId);
	}

	@Override
	@Transactional
	public List<Routine> selectDateRoutineByUserId(String exerciseDate, int userId) {
		return routineMapper.selectDateRoutineByUserId(exerciseDate, userId);
	}

	@Override
	@Transactional
	public Routine selectRoutineById(int id) {
		return routineMapper.selectRoutineById(id);
	}
	
	@Override
	@Transactional
	public int insertRoutine(Routine routine) {
		return routineMapper.insertRoutine(routine);
	}

	@Override
	@Transactional
	public int updateSetsReps(Routine routine) {
		return routineMapper.updateSetsReps(routine);
	}

	@Override
	@Transactional
	public int updateIsCompleted(int id) {
		return routineMapper.updateIsCompleted(id);
	}

	@Override
	@Transactional
	public int deleteRoutineById(int id) {
		return routineMapper.deleteRoutineById(id);
	}

	@Override
	@Transactional
	public int selectCountDayRoutine(String exerciseDate, int userId) {
		return routineMapper.selectCountDayRoutine(exerciseDate, userId);
	}

	@Override
	@Transactional
	public int selectCountDayCompletedRoutine(String exerciseDate, int userId) {
		return routineMapper.selectCountDayCompletedRoutine(exerciseDate, userId);
	}

}
