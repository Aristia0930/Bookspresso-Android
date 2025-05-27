package com.ssafy.cafe.model.dao;

import com.ssafy.cafe.model.dto.BookRecommendation;

public interface RecommendationDao {

    int insert(BookRecommendation bookRecommendation);

    // 가장최근거 하나 가저오기
    BookRecommendation select();
}
