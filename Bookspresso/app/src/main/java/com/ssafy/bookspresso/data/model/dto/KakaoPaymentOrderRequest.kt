package com.ssafy.bookspresso.data.model.dto

data class KakaoPaymentOrderRequest(
    val cid: String,
    val tid: String
) {
    constructor() : this("", "")
    constructor(tid: String) : this("", tid)
}
