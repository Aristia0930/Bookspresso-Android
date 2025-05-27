package com.ssafy.cafe.gpt.dto;

import com.ssafy.cafe.model.dto.Book;
import com.ssafy.cafe.model.dto.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationResponse {
    private Book book;
    private Product drink;
    private Product dessert;
    String reason;
}