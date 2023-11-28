package com.example.nalsam.chatbot.dto.response;

import com.example.nalsam.chatbot.domain.Choice;
import com.example.nalsam.chatbot.domain.Usage;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
public class ChatResponse implements Serializable {

    private String id;
    private String object;
    private LocalDate created;
    private String model;
    private Usage usage;
    private List<Choice> choices;
}