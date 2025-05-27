package com.ssafy.cafe.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class TokenRequest {
    private String token;

    // 기본 생성자
    public TokenRequest() {}


    public void setToken(String token) {
        this.token = token;
    }
}
