package com.ssafy.bookspresso.data.model.response

import com.ssafy.bookspresso.data.model.dto.Book
import com.ssafy.bookspresso.data.model.dto.Product

data class RecommendationResponse(val book: Book, val drink: Product, val dessert: Product, val reason: String)