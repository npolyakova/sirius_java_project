package ru.hpclab.hl.module1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto { //kafka
    private String entity;
    private String operation;
    private Object payload;
}












