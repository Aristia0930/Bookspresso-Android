package com.ssafy.bookspresso.util

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.ssafy.bookspresso.base.ApplicationClass
import com.ssafy.bookspresso.data.model.response.OrderResponse
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

private const val TAG = "CommonUtils_싸피"
object CommonUtils {

    //천단위 콤마
    fun makeComma(num: Int): String {
        val comma = DecimalFormat("#,###")
        return "${comma.format(num)} 원"
    }

    //날짜 포맷 출력
    fun dateformatYMDHM(time:Date):String{
        val format = SimpleDateFormat("yyyy.MM.dd. HH:mm", Locale.KOREA)
        format.timeZone = TimeZone.getTimeZone("Asia/Seoul")
        return format.format(time)
    }

    fun dateformatYMD(time: Date):String{
        val format = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA)
        format.timeZone = TimeZone.getTimeZone("Asia/Seoul")
        return format.format(time)
    }

    // 시간 계산을 통해 완성된 제품인지 확인
    fun isOrderCompleted(order: OrderResponse): String {
        Log.d(TAG, "isOrderCompleted: ${order.orderDate.time}")
        return if( checkTime(order.orderDate.time))  "주문완료" else "진행 중.."
    }

    private fun checkTime(time:Long):Boolean{
        val currentTimeMillis = System.currentTimeMillis()
        return (currentTimeMillis - time) > ApplicationClass.ORDER_COMPLETED_TIME
    }

    // 주문 목록에서 총가격, 주문 개수 구하여 List로 반환한다.
    fun calcTotalPrice(orderList: List<OrderResponse>): List<OrderResponse>{
        orderList.forEach { order ->
            calcTotalPrice(order)
        }
        return orderList
    }

    fun calcTotalPrice(order: OrderResponse): OrderResponse {
        order.details.forEach { detail ->
            order.totalPrice += detail.sumPrice
            order.orderCount += detail.quantity
        }
        return order
    }

    fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: (T) -> Unit) {
        val wrappedObserver = object : Observer<T> {
            override fun onChanged(t: T) {
                observer(t)
                removeObserver(this)
            }
        }
        observe(lifecycleOwner, wrappedObserver)
    }
}