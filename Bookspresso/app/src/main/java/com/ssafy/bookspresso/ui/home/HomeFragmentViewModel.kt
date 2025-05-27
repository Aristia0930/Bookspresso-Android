package com.ssafy.bookspresso.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.bookspresso.data.model.response.OrderResponse
import com.ssafy.bookspresso.data.model.response.RecommendationResponse
import com.ssafy.bookspresso.data.remote.RetrofitUtil
import kotlinx.coroutines.launch
import kotlin.math.log

private const val TAG = "HomeFragmentViewModel_싸피"
class HomeFragmentViewModel: ViewModel() {

    private val _orderList = MutableLiveData<List<OrderResponse>>()
    val orderList: LiveData<List<OrderResponse>>
        get() = _orderList

    private val _recommendation = MutableLiveData<RecommendationResponse>()
    val recommendation: LiveData<RecommendationResponse>
        get() = _recommendation

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

    fun getRecommendation() {
        viewModelScope.launch {
            runCatching {
                RetrofitUtil.gptService.getRecommendation()
            }.onSuccess {
                _recommendation.value = it
                Log.d(TAG, "getRecommendation: 성공 $it")
            }.onFailure {
                Log.d(TAG, "getRecommendation: 실패 ${it.cause}")
            }
        }
    }

}
