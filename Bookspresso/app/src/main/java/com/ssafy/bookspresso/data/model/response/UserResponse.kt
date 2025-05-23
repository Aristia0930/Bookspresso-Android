package com.ssafy.bookspresso.data.model.response

import com.ssafy.bookspresso.data.model.dto.Grade
import com.ssafy.bookspresso.data.model.dto.Order
import com.ssafy.bookspresso.data.model.dto.User

data class UserResponse(val grade: Grade, val user: User, var order:List<Order>)