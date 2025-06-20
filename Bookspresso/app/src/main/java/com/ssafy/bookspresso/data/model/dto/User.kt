package com.ssafy.bookspresso.data.model.dto

data class User(
    val id: String,
    val name: String,
    val pass: String,
    val stamps: Int,
    val role:String,
    val stampList: ArrayList<Stamp> = ArrayList()
) {
    constructor() : this("", "", "", 0,"")
    constructor(id: String, pass: String) : this(id, "", pass, 0,"")
    constructor(id: String, name: String, pass: String) : this(id, name, pass, 0,"")
}