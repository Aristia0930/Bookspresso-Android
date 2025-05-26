package com.ssafy.bookspresso.data.remote

import com.ssafy.bookspresso.data.model.dto.User
import com.ssafy.bookspresso.data.model.response.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserService {
    // 사용자 정보를 추가한다.
    @POST("rest/user")
    suspend fun insert(@Body body: User): Boolean
    
    // 사용자의 정보와 함께 사용자의 주문 내역, 사용자 등급 정보를 반환한다.
    @GET("rest/user/info")
    suspend fun getUserInfo(@Query("id") id:String): UserResponse

    // request parameter로 전달된 id가 이미 사용중인지 반환한다.
    @GET("rest/user/isUsed")
    suspend fun isUsedId(@Query("id") id: String): Boolean

    // 로그인 처리 후 성공적으로 로그인 되었다면 loginId라는 쿠키를 내려준다.
    @POST("rest/user/login")
    suspend fun login(@Body body: User) : Response<Void>

    @POST("rest/user/info")
    suspend fun info(@Body body: User): User

    @POST("rest/user/info/jwt")
    suspend fun jwtInfo(): User
}