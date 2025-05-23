package com.ssafy.bookspresso.data.model.response

import com.google.gson.annotations.SerializedName
import java.util.Date

data class OrderResponse(
    @SerializedName("id") val orderId: Int = 0,
    @SerializedName("userId") val userId: String = "",
    @SerializedName("orderTable") val orderTable: String = "",
    @SerializedName("orderTime") val orderDate: Date = Date(),
    @SerializedName("completed") val orderCompleted: Char = 'N',
    @SerializedName("details") var details:List<OrderDetailResponse> = emptyList(),

    var totalPrice: Int = 0,
    var orderCount: Int = 0
)