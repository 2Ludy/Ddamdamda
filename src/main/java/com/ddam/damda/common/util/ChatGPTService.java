package com.ddam.damda.common.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ddam.damda.exercises.model.Exercises;
import com.ddam.damda.exercises.model.mapper.ExercisesMapper;
import com.ddam.damda.routine.model.RoutineRecommendationRequest;
import com.ddam.damda.routine.model.RoutineRecommendationResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ChatGPTService {
    @Value("${chatgpt.api.key}")
    private String apiKey;
	
    @Autowired
    private ExercisesMapper exerciseMapper;
    
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private static final int MAX_TOKENS = 4096;
    
    public RoutineRecommendationResponse getRoutineRecommendation(RoutineRecommendationRequest request) {
        try {
            // 프롬프트 생성
            String prompt = createPrompt(request);
            
            // JSON 요청 본문 생성
            JSONObject requestBody = new JSONObject();
            requestBody.put("model", "gpt-4o-mini");
            requestBody.put("max_tokens", MAX_TOKENS);
            JSONArray messages = new JSONArray();
            JSONObject message = new JSONObject();
            message.put("role", "user");
            message.put("content", prompt);
            messages.put(message);
            requestBody.put("messages", messages);

            // HTTP 연결 설정
            HttpURLConnection connection = (HttpURLConnection) new URL(API_URL).openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // 요청 본문 전송
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = requestBody.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // 응답 받기
            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }

            // 응답 파싱
            JSONObject jsonResponse = new JSONObject(response.toString());
            JSONArray choices = jsonResponse.getJSONArray("choices");
            if (!choices.isEmpty()) {
                String content = choices.getJSONObject(0)
                                     .getJSONObject("message")
                                     .getString("content");
                
                content = content.replaceAll("```json", "")
                        .replaceAll("```", "")
                        .trim();
                                     
                // JSON 문자열을 RoutineRecommendationResponse 객체로 변환
                return new ObjectMapper().readValue(content, RoutineRecommendationResponse.class);
            }
            
            throw new RuntimeException("ChatGPT API 응답 없음");
            
        } catch (Exception e) {
            throw new RuntimeException("ChatGPT API 호출 실패", e);
        }
    }
    
    private String createPrompt(RoutineRecommendationRequest request) {
        StringBuilder prompt = new StringBuilder();
        
        // 1. 기본 정보 전달
        prompt.append("당신은 전문적인 피트니스 트레이너입니다. 다음 조건에 맞는 운동 루틴을 추천해주세요:\n\n");
        
        // 2. 사용자 정보 전달
        prompt.append("### 사용자 정보 ###\n");
        prompt.append("1. 운동 경험: ").append(translateExperience(request.getExperienceLevel())).append("\n");
        prompt.append("2. 운동 목적: ").append(translatePurposes(request.getPurposes())).append("\n");
        prompt.append("3. 목표 부위: ").append(translateAreas(request.getTargetAreas())).append("\n");
        prompt.append("4. 운동 시간: ").append(request.getDuration()).append("분\n\n");
        
        // 3. DB에서 운동 목록 가져와서 전달
        prompt.append("### 사용 가능한 운동 목록 ###\n");
        prompt.append("아래 운동 목록 중에서만 선택하여 추천해주세요:\n");
        prompt.append("```\n");
        
        for (String targetArea : request.getTargetAreas()) {
            // 영어로 된 부위명을 한글로 변환
            String koreanPart = switch (targetArea) {
                case "chest" -> "가슴";
                case "back" -> "등";
                case "shoulder" -> "어깨";
                case "arms" -> "팔";
                case "core" -> "코어";
                case "legs" -> "하체";
                case "full" -> "전신";
                default -> targetArea;
            };
            
            List<Exercises> exercises = exerciseMapper.selectExercisesByPart(koreanPart);
            
            prompt.append(koreanPart).append(" 운동:\n");
            
            for (Exercises exercise : exercises) {
                prompt.append(exercise.getExercisesId())
                      .append(". ")
                      .append(exercise.getName())
                      .append("\n");
            }
            prompt.append("\n");
        }
        
        prompt.append("```\n\n");
        
        // 4. 응답 형식 안내
        prompt.append("### 응답 요구사항 ###\n");
        prompt.append("1. 위 운동 목록에 있는 운동만 선택해주세요.\n");
        prompt.append("2. 사용자의 운동 경험과 목적을 고려하여 적절한 세트 수와 반복 횟수를 추천해주세요.\n");
        prompt.append("3. 선택한 운동들이 목표 부위를 효과적으로 커버할 수 있도록 해주세요.\n");
        prompt.append("4. 운동 시간이 60분이라면 4가지의 운동, 90분이라면 6개의 운동, 120분이라면 8개의 운동을 추천해주세요. \n\n");
        
        // 응답 형식 수정
        prompt.append("### 응답 형식 ###\n");
        prompt.append("아래 JSON 형식으로 응답해주세요. 반드시 reps는 하나의 정수값으로 지정해주고, 응답 외의 다른 텍스트나 마크다운은 포함하지 마세요:\n");
        prompt.append("""
            {
                "routines": [
                    {
                        "exerciseId": 운동ID (정수),
                        "title": "운동명",
                        "sets": 세트수 (정수, 3-5 사이),
                        "reps": 반복횟수 (정수, 범위가 아닌 정확한 숫자로 지정),
                        "note": "운동 자세를 한 마디로 설명해주세요"
                    },
                    ...
                ],
                "totalDuration": 예상 소요 시간 (정수, 분 단위),
                "recommendation": "해둥 루틴을 통한 효능과 동기부여 메시지 한 줄로 설명해주세요"
            }
            """);
        
        return prompt.toString();
    }

    
    // 번역 헬퍼 메서드들
    private String translateExperience(String level) {
        return switch (level) {
            case "beginner" -> "초보자 (운동 경험 1년 미만)";
            case "intermediate" -> "중급자 (운동 경험 1-3년)";
            case "advanced" -> "상급자 (운동 경험 3년 이상)";
            default -> "초보자";
        };
    }
    
    private String translatePurposes(List<String> purposes) {
        List<String> translated = purposes.stream()
            .map(purpose -> switch (purpose) {
                case "muscle" -> "근비대";
                case "strength" -> "근력 향상";
                case "endurance" -> "지구력 향상";
                case "weight" -> "체중 감량";
                case "balance" -> "체형 교정";
                default -> purpose;
            })
            .collect(Collectors.toList());
        
        return String.join(", ", translated);
    }
    
    private String translateAreas(List<String> areas) {
        List<String> translated = areas.stream()
            .map(area -> switch (area) {
                case "chest" -> "가슴";
                case "back" -> "등";
                case "shoulder" -> "어깨";
                case "arms" -> "팔";
                case "core" -> "코어";
                case "legs" -> "하체";
                case "full" -> "전신";
                default -> area;
            })
            .collect(Collectors.toList());
        
        return String.join(", ", translated);
    }
    
}