package com.example.nalsam.chatbot.service;


import com.example.nalsam.chatbot.dto.request.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatCompletionService chatCompletionService;
    public String getChatResponse(QuestionRequest questionRequest) {

        // 받은 정보로 질문 생성
        String question = questionRequest.getQuestion() + ", 5줄이내로 알려줘 ";
        System.out.println(question);
        return chatCompletionService.chatCompletions(question);
    }
    public String getChatResponse(HealthRequest healthRequest) {

        // 받은 정보로 질문 생성
        String question = "체내 산소포화도가 " +
                healthRequest.getOxyzenSaturation() + "%이며 " +
                "심박수는 " + healthRequest.getHeartRate() + "인데, 어떤 상태인지 5줄이내로 알려줘";

        System.out.println(question);
        String response =  chatCompletionService.chatCompletions(question);
        // Chat-GPT에게 질문 전송하여 응답 받기
        return response;
    }
    public String getChatResponse(AirQualityRequest airQualityRequest) {

        // 받은 정보로 질문 생성
        String question = "현재 대기질이 so2: " + airQualityRequest.getSo2Value()+", Co: "+airQualityRequest.getCoValue()
                + ", No2: "+airQualityRequest.getNo2Value()+ ", O3: " +airQualityRequest.getO3Value() + ", pm10: "+ airQualityRequest.getPm10Value()
                + ", pm25:" +airQualityRequest.getPm25Value()+" 인 상태인데 통합대기환경지수를 고려하여 각각 어떤 대기상태인지 5줄이내로 알려줘";

        System.out.println(question);
        // Chat-GPT에게 질문 전송하여 응답 받기
        return chatCompletionService.chatCompletions(question);
    }
    public String getChatResponse(PlaceRecommend placeRecommend) {

        // 받은 정보로 질문 생성
        String question = "대한민국 "+placeRecommend.getSido()+" "+ placeRecommend.getGu()+" 주변의 산책할만한 장소(공원, 산책로) 3가지를 간단하게 알려줘";
        System.out.println(question);

        return chatCompletionService.chatCompletions(question);
    }
    public String getChatResponse(SymtomCaution symtomCaution) {

        // 받은 정보로 질문 생성
        String question = "나는 " +symtomCaution.getSymptom()+"을 가지고있는데 주의해야할점을 5줄이내로 알려줘";
        System.out.println(question);
        return chatCompletionService.chatCompletions(question);
    }

}