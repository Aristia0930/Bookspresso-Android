package com.ssafy.bookspresso.ui.cafe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.bookspresso.data.model.dto.Product
import com.ssafy.bookspresso.data.remote.RetrofitUtil
import kotlinx.coroutines.launch

class OrderViewModel : ViewModel() {

    private var _productList = MutableLiveData<List<Product>>() // 전체 리스트
    val productList: LiveData<List<Product>> get() = _productList

    private val _filteredProductList = MutableLiveData<List<Product>>() // 필터링된 리스트
    val filteredProductList: LiveData<List<Product>> get() = _filteredProductList

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
            _filteredProductList.value = list.filter { it.type == type }
        }
    }
}
