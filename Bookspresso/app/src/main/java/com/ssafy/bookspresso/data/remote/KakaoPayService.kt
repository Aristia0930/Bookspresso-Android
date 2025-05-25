package com.ssafy.bookspresso.data.remote

import com.ssafy.bookspresso.data.model.dto.KakaoPaymentOrderRequest
import com.ssafy.bookspresso.data.model.dto.KakaoReadyRequest
import com.ssafy.bookspresso.data.model.response.KakaoPayReadyResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface KakaoPayService {
    @POST("rest/payment/ready")
    suspend fun requestPayment(@Body kakaoReadyRequest: KakaoReadyRequest): KakaoPayReadyResponse

    @POST("rest/payment/order")
    suspend fun doPayment(@Body kakaoPaymentOrderRequest: KakaoPaymentOrderRequest): Boolean

}