package com.ssafy.bookspresso.data.model.dto

data class Book(
    val isbn: String,         // Primary Key
    val title: String,
    val author: String,
    val summary: String,
    val status: String,
    val img: String
) {
    constructor() : this("", "", "", "", "", "")
}
