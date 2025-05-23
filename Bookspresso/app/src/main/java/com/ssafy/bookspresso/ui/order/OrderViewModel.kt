package com.ssafy.bookspresso.ui.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.bookspresso.data.model.dto.Product
import com.ssafy.bookspresso.data.remote.RetrofitUtil
import kotlinx.coroutines.launch

class OrderViewModel : ViewModel() {

    private var _productList = MutableLiveData<List<Product>>()
    val productList: LiveData<List<Product>>
        get() = _productList

    fun getProductList() {
        viewModelScope.launch {
            runCatching {
                RetrofitUtil.productService.getProductList()
            }.onSuccess {
                _productList.value = it
            }.onFailure {
                _productList.value = arrayListOf()
            }
        }
    }
}