package com.ssafy.bookspresso.data.model.dto

data class KakaoReadyRequest(
    val cid: String,
    val partner_order_id: String,
    val partner_user_id: String,
    val item_name: String,
    val quantity: Int,
    val total_amount: Int,
    val tax_free_amount: Int,
    val approval_url: String,
    val cancel_url: String,
    val fail_url: String
) {
    constructor() : this("", "", "", "", 0, 0, 0, "", "", "")
    constructor(oid:String, uid:String, item_name:String, quantity: Int, total_amount: Int) : this("", oid, uid, item_name, quantity, total_amount, 0, "", "", "")
}
