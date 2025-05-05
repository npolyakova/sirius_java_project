package ru.hpclab.hl.module1.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto { //kafka
    private String entity;
    private String operation;
    private JsonNode payload;
}












