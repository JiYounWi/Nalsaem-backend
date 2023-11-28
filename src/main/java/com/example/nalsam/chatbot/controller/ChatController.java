package com.example.nalsam.chatbot.controller;


import com.example.nalsam.chatbot.dto.request.*;
import com.example.nalsam.chatbot.service.ChatCompletionService;
import com.example.nalsam.chatbot.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/chatbot")
public class ChatController {
    private final ChatService chatService;

    private final ChatCompletionService chatCompletionService;

    @PostMapping("/question")
    public ResponseEntity<String>  chat(@RequestBody QuestionRequest questionRequest){

        try {
            String response = chatService.getChatResponse(questionRequest);

            System.out.println("응답 : "+response);
            // 응답을 프론트엔드로 POST로 반환
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // 오류 발생 시 오류 응답 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing the question.");
        }
    }
    //chat-gpt 와 간단한 채팅 서비스 소스
    @PostMapping("/healthStatus")
    public ResponseEntity<String> chat(@RequestBody HealthRequest healthRequest) {
        try {
            String response = chatService.getChatResponse(healthRequest);

            System.out.println("응답 : "+response);
            // 응답을 프론트엔드로 POST로 반환
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // 오류 발생 시 오류 응답 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing the question.");
        }
    }
    @PostMapping("/airQuality")
    public ResponseEntity<String> chat(@RequestBody AirQualityRequest airQualityRequest){
        try {

            String response = chatService.getChatResponse(airQualityRequest);
            System.out.println("응답 : "+response);

            // 응답을 프론트엔드로 POST로 반환
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // 오류 발생 시 오류 응답 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing the question.");
        }
    }
    @PostMapping("/placeRecommend")
    public ResponseEntity<String> chat(@RequestBody PlaceRecommend placeRecommend){
        try {

            String response = chatService.getChatResponse(placeRecommend);
            System.out.println("응답 : "+response);

            // 응답을 프론트엔드로 POST로 반환
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // 오류 발생 시 오류 응답 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing the question.");
        }
    }
    @PostMapping("/symytomCaution")
    public ResponseEntity<String> chat(@RequestBody SymtomCaution symtomCaution){
        try {

            String response = chatService.getChatResponse(symtomCaution);
            System.out.println("응답 : "+response);

            // 응답을 프론트엔드로 POST로 반환
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // 오류 발생 시 오류 응답 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing the question.");
        }
    }
}