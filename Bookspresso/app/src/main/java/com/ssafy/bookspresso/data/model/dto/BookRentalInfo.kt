package com.ssafy.bookspresso.data.model.dto

data class BookRentalInfo(
    val rentalId: Int,         // Primary Key
    val userId: String,
    val isbn: String,
    val rentalDate: String,
    val dueDate: String,
    val fee: Int,
    val status: String,
    val book: Book
)
