package com.ssafy.bookspresso.data.remote

import com.ssafy.bookspresso.data.model.response.OrderResponse
import com.ssafy.bookspresso.data.model.dto.Order
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface OrderService {
    // order 객체를 저장하고 추가된 Order의 id를 반환한다.
    @POST("rest/order")
    suspend fun makeOrder(@Body body: Order): Int

    // {orderId}에 해당하는 주문의 상세 내역을 반환한다.
    @GET("rest/order/{orderId}")
    suspend fun getOrderDetail(@Path("orderId") orderId: Int): OrderResponse

    // {id}에 해당하는 사용자의 최근 1개월간 주문 내역을 반환한다.
    // 반환 정보는 1차 주문번호 내림차순, 2차 주문 상세 내림차순으로 정렬된다.
    @GET("rest/order/byUser")
    suspend fun getLastMonthOrder(@Query("id") id: String): List<OrderResponse>

    @GET("rest/order/byUserIn6Months")
    suspend fun getLast6MonthOrder(@Query("id") id: String): List<OrderResponse>
}