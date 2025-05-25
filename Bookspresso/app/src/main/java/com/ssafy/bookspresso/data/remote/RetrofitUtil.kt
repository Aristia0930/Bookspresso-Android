package com.ssafy.bookspresso.data.remote

import com.ssafy.bookspresso.base.ApplicationClass

class RetrofitUtil {
    companion object{
        val commentService = ApplicationClass.retrofit.create(CommentService::class.java)
        val orderService = ApplicationClass.retrofit.create(OrderService::class.java)
        val productService = ApplicationClass.retrofit.create(ProductService::class.java)
        val userService = ApplicationClass.retrofit.create(UserService::class.java)
        val bookService = ApplicationClass.retrofit.create(BookService::class.java)
        val kakaoPayService = ApplicationClass.retrofit.create(KakaoPayService::class.java)
    }
}