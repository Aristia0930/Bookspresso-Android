package com.ssafy.cafe.gpt.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.cafe.gpt.GptConfig;
import com.ssafy.cafe.gpt.dto.GptResponse;
import com.ssafy.cafe.gpt.dto.RecommendationResponse;
import com.ssafy.cafe.gpt.service.GptService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/gpt")
public class GptController {

    @Autowired
    private GptService gptService;

    @Autowired
    private GptConfig gptConfig;

//    @GetMapping("")
//    @Operation(summary="GPT TEXT로 요청")
//    public ResponseEntity<GptResponse> gptApiRequest(@RequestParam("text") String text){
//
//        GptResponse gptResponse=gptService.gptApiRequest(text);
//
//        return ResponseEntity.ok(gptResponse);
//    }

    @GetMapping("/today")
    @Operation(summary="GPT 오늘의 책 음식 요청")
    public ResponseEntity<RecommendationResponse> gptApiRequest() throws JsonProcessingException {

        RecommendationResponse gptResponse=gptService.gptApiRequest();

        return ResponseEntity.ok(gptResponse);
    }

    @GetMapping("/get/recommend")
    @Operation(summary="오늘의 책 음식 db 테이블에서 가져오기")
    public ResponseEntity<RecommendationResponse> getApiRecommendation() throws JsonProcessingException {

        RecommendationResponse gptResponse=gptService.getApiRecommendation();

        return ResponseEntity.ok(gptResponse);
    }
}
