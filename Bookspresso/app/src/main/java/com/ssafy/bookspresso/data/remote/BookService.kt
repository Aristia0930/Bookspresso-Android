package com.ssafy.bookspresso.data.remote

import com.ssafy.bookspresso.data.model.dto.Book
import com.ssafy.bookspresso.data.model.dto.BookRental
import com.ssafy.bookspresso.data.model.dto.Comment
import com.ssafy.bookspresso.data.model.dto.Product
import com.ssafy.bookspresso.data.model.response.ProductWithCommentResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface BookService {
    // 전체 상품의 목록을 반환한다
    @GET("rest/book")
    suspend fun getBookList(): List<Book>

    @GET("rest/book/{isbn}")
    suspend fun getBook(@Path("isbn") isbn: String): Book

    @POST("rest/book/rental")
    suspend fun rentalBook(@Body bookRental: BookRental): Boolean

    @PUT("rest/book/rental2/{isbn}")
    suspend fun returnBook(@Path("isbn") isbn: String): Boolean
}