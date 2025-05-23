package com.ssafy.bookspresso.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.bookspresso.data.model.response.OrderResponse
import com.ssafy.bookspresso.data.remote.RetrofitUtil
import kotlinx.coroutines.launch

class HomeFragmentViewModel: ViewModel() {

    private val _orderList = MutableLiveData<List<OrderResponse>>()
    val orderList: LiveData<List<OrderResponse>>
        get() = _orderList

    fun getLastMonthOrder(id: String) {
        viewModelScope.launch {
            runCatching {
                RetrofitUtil.orderService.getLastMonthOrder(id)
            }.onSuccess {
                _orderList.value = it
            }.onFailure {
                _orderList.value = arrayListOf()
            }
        }
    }

}
