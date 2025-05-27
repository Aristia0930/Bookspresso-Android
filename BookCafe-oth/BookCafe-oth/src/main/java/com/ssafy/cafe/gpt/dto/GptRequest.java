package com.ssafy.cafe.gpt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GptRequest {
    private String model;
    private String  input;
    private Double  temperature;
}
