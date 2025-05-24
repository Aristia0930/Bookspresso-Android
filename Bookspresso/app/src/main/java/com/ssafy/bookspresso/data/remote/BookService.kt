package com.ssafy.bookspresso.data.remote

import com.ssafy.bookspresso.data.model.dto.Book
import com.ssafy.bookspresso.data.model.dto.Product
import com.ssafy.bookspresso.data.model.response.ProductWithCommentResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface BookService {
    // 전체 상품의 목록을 반환한다
    @GET("rest/book")
    suspend fun getBookList(): List<Book>

}