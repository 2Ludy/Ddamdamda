package com.ddam.damda.routine.model.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddam.damda.common.util.ChatGPTService;
import com.ddam.damda.routine.model.Routine;
import com.ddam.damda.routine.model.RoutineDetail;
import com.ddam.damda.routine.model.RoutineRecommendationRequest;
import com.ddam.damda.routine.model.RoutineRecommendationResponse;
import com.ddam.damda.routine.model.mapper.RoutineMapper;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RoutineServiceImpl implements RoutineService {
	
	@Autowired
	private RoutineMapper routineMapper;
	
	@Autowired
	private ChatGPTService chatGPTService;
	
	@Autowired
	private ObjectMapper objectMapper;

	@Override
	@Transactional
	public List<Routine> selectAllRoutineByUserId(int userId) {
		return routineMapper.selectAllRoutineByUserId(userId);
	}

	@Override
	@Transactional
	public List<Routine> selectDateRoutineByUserId(Routine routine) {
		return routineMapper.selectDateRoutineByUserId(routine);
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
	public int selectCountDayRoutine(Routine routine) {
		return routineMapper.selectCountDayRoutine(routine);
	}

	@Override
	@Transactional
	public int selectCountDayCompletedRoutine(Routine routine) {
		return routineMapper.selectCountDayCompletedRoutine(routine);
	}
   @Override
   @Transactional
   public RoutineRecommendationResponse getAiRoutineRecommendation(RoutineRecommendationRequest request) {
       try {
           return chatGPTService.getRoutineRecommendation(request);
       } catch (Exception e) {
           throw new RuntimeException("AI 루틴 추천 실패", e);
       }
   }

   @Override
   @Transactional
   public List<Routine> convertToRoutines(RoutineRecommendationResponse recommendationResponse) {
       try {
           return convertToRoutineList(recommendationResponse);
       } catch (Exception e) {
           throw new RuntimeException("AI 추천 루틴 변환 실패", e);
       }
   }

   // DTO -> Entity 변환 메서드
   private List<Routine> convertToRoutineList(RoutineRecommendationResponse response) {
       List<Routine> routines = new ArrayList<>();
       LocalDate today = LocalDate.now();
       int userId = response.getUserId();

       for (RoutineDetail detail : response.getRoutines()) {
           Routine routine = new Routine();
           routine.setId(0);
           routine.setUserId(userId);
           routine.setTitle(detail.getTitle());
           routine.setExerciseDate(today.format(DateTimeFormatter.ISO_DATE));
           routine.setSets(detail.getSets());
           routine.setReps(detail.getReps());
           routine.setExercisesId(detail.getExerciseId());
           routine.setIsCompleted(0);

           routines.add(routine);
       }

       return routines;
   }
}
