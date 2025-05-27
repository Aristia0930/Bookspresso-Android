package com.ssafy.cafe.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRecommendation {
    private Integer recommendationId;  // 추천 ID (PK)
    private String isbn;               // 책 ISBN
    private Integer drinkId;           // 음료 ID
    private Integer dessertId;         // 디저트 ID
    private LocalDateTime recommendDate; // 추천 저장 일시
    private String reason;
}