package com.ssafy.bookspresso.data.model.dto

data class Comment(
    val id: Int = -1,
    val userId: String,
    val productId: Int,
    val rating: Float,
    val comment: String,
    val userName:String = ""
) {
    constructor(userId: String, productId: Int, rating: Float, comment: String) :
            this(-1, userId, productId, rating, comment, "")
}