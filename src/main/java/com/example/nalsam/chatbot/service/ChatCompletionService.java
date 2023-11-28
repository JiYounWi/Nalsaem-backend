package com.example.nalsam.chatbot.service;

import com.example.nalsam.chatbot.domain.Message;
import com.example.nalsam.chatbot.dto.request.ChatRequest;
import com.example.nalsam.chatbot.infrastructure.ChatCompletionClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatCompletionService {

    private final ChatCompletionClient chatCompletionClient;
    private final static String ROLE_USER = "user";
    private final static String MODEL = "gpt-3.5-turbo";

    @Value("${chatgpt.api-key}")
    private String apiKey;

    public String chatCompletions(String question) {

        Message message = Message.builder()
                .role(ROLE_USER)
                .content(question)
                .build();

        ChatRequest chatRequest = ChatRequest.builder()
                .model(MODEL)
                .messages(Collections.singletonList(message))
                .build();

        return chatCompletionClient
                .chatCompletions(apiKey, chatRequest)
                .getChoices()
                .stream()
                .findFirst()
                .orElseThrow()
                .getMessage()
                .getContent();
    }
}