package com.ssafy.bookspresso.ui.manager

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.bookspresso.data.model.response.OrderResponse
import com.ssafy.bookspresso.data.remote.RetrofitUtil
import kotlinx.coroutines.launch
import kotlin.math.log

private const val TAG = "ManagerViewModel_싸피"
class ManagerViewModel : ViewModel() {
    //주문 내역
    private val _orderList = MutableLiveData<List<OrderResponse>>()
    val orderList: LiveData<List<OrderResponse>>
        get() = _orderList

    fun getListOrder() {
        viewModelScope.launch {
            kotlin.runCatching {
                RetrofitUtil.orderService.getListOrder()
            }.onSuccess {
                _orderList.value = it
                Log.d(TAG, "getLastOrder: 성공")
            }.onFailure {
                Log.d(TAG, "getLastOrder: ${it.cause}")
                Log.d(TAG, "getLastOrder: 실패")
            }
        }
    }

    fun cancel(orderId: Int,index:Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                RetrofitUtil.orderService.cancel(orderId)
            }.onSuccess {
                getListOrder()
                Log.d(TAG, "cancel: 성공")
            }.onFailure {
                Log.d(TAG, "getLastOrder: ${it.cause}")
                Log.d(TAG, "getLastOrder: 실패")
            }
        }
    }

    fun accept(orderId: Int,index:Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                RetrofitUtil.orderService.accept(orderId)
            }.onSuccess {
                getListOrder()
                Log.d(TAG, "accept: 성공")
            }.onFailure {
                Log.d(TAG, "getLastOrder: ${it.cause}")
                Log.d(TAG, "getLastOrder: 실패")
            }
        }
    }
}