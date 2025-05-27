package com.ssafy.bookspresso.ui.cafe

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.bookspresso.data.model.dto.KakaoPaymentOrderRequest
import com.ssafy.bookspresso.data.model.dto.KakaoReadyRequest
import com.ssafy.bookspresso.data.model.dto.Product
import com.ssafy.bookspresso.data.model.response.KakaoPayReadyResponse
import com.ssafy.bookspresso.data.remote.RetrofitUtil
import kotlinx.coroutines.launch

private const val TAG = "OrderViewModel_싸피"

class OrderViewModel : ViewModel() {

    private var _productList = MutableLiveData<List<Product>>() // 전체 리스트
    val productList: LiveData<List<Product>> get() = _productList

    private val _filteredProductList = MutableLiveData<List<Product>>() // 필터링된 리스트
    val filteredProductList: LiveData<List<Product>> get() = _filteredProductList

    private var tabFilteredList: List<Product> = listOf()

    private val _kakaoPayResponse = MutableLiveData<KakaoPayReadyResponse>()
    val kakaoPayReadyResponse: LiveData<KakaoPayReadyResponse>
        get() = _kakaoPayResponse

    fun getProductList() {
        viewModelScope.launch {
            runCatching {
                RetrofitUtil.productService.getProductList()
            }.onSuccess {
                _productList.value = it
                filterProductList("drink") // 기본 필터 적용 (예: drink 탭)
            }.onFailure {
                _productList.value = emptyList()
                _filteredProductList.value = emptyList()
            }
        }
    }

    fun filterProductList(type: String) {
        _productList.value?.let { list ->
            tabFilteredList = list.filter { it.type == type }
            _filteredProductList.value = tabFilteredList
        }
    }


    fun searchProduct(currentType: String, query: String) {
        if (query.isBlank()) {
            // 검색어 없을 경우 현재 탭 기준 필터 다시 적용
            filterProductList(currentType)
            return
        }

        // 탭 필터 기준 리스트에서 검색
        val result = tabFilteredList.filter {
            it.name.contains(query, ignoreCase = true)
        }
        _filteredProductList.value = result
    }


    fun requestPayment(kakaoReadyRequest: KakaoReadyRequest) {
        viewModelScope.launch {
            runCatching {
                RetrofitUtil.kakaoPayService.requestPayment(kakaoReadyRequest)
            }.onSuccess {
                _kakaoPayResponse.value = it
                Log.d(TAG, "requestPayment: $it")
            }.onFailure {
                Log.d(TAG, "requestPayment: 실패 ${it.cause}")
            }
        }
    }

    suspend fun doPayment(tid: String): Boolean {
        return RetrofitUtil.kakaoPayService.doPayment(KakaoPaymentOrderRequest(tid))
    }
}
