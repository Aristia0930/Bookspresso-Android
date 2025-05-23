package com.ssafy.bookspresso.data.model.dto

data class Order (
    var id: Int,
    var userId: String,
    var orderTable: String,
    var orderTime: String,
    var completed: String,
    val details: ArrayList<OrderDetail> = ArrayList() ){

    constructor():this(0,"","","","")
}
