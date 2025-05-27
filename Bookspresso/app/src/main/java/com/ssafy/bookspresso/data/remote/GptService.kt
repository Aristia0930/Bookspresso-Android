package com.ssafy.bookspresso.data.remote

import com.ssafy.bookspresso.data.model.dto.Book
import com.ssafy.bookspresso.data.model.dto.BookRental
import com.ssafy.bookspresso.data.model.dto.BookRentalInfo
import com.ssafy.bookspresso.data.model.dto.Comment
import com.ssafy.bookspresso.data.model.dto.Product
import com.ssafy.bookspresso.data.model.response.ProductWithCommentResponse
import com.ssafy.bookspresso.data.model.response.RecommendationResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface GptService {
    // 전체 상품의 목록을 반환한다
    @GET("rest/gpt/get/recommend")
    suspend fun getRecommendation(): RecommendationResponse

}