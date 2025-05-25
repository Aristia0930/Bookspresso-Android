package com.ssafy.bookspresso.data.model.dto

data class BookRental(
    val rentalId: Int,         // Primary Key
    val userId: String,
    val isbn: String,
    val rentalDate: String,
    val dueDate: String,
    val fee: Int,
    val status: String,
) {
    constructor() : this(0, "", "", "", "", 0, "")
    constructor(userId: String, isbn: String, fee: Int) : this(0, userId, isbn, "", "", fee, "")
}
