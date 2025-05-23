package com.ssafy.bookspresso.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.bookspresso.data.model.dto.Order
import com.ssafy.bookspresso.data.model.dto.ShoppingCart
import com.ssafy.bookspresso.data.model.response.OrderResponse
import com.ssafy.bookspresso.data.model.response.UserResponse
import com.ssafy.bookspresso.data.remote.RetrofitUtil
import kotlinx.coroutines.launch

private const val TAG = "MainActivityViewModel_싸피"

class MainActivityViewModel : ViewModel() {
    // my 페이지에서 detail로 이동시 사용
    private val _myPageOrderId = MutableLiveData<Int>()
    val myPageOrderId: LiveData<Int>
        get() = _myPageOrderId

    fun setOrderId(orderId: Int) {
        _myPageOrderId.value = orderId
    }

    // 선택된 productId
    private val _productId = MutableLiveData<Int>()
    val productId: LiveData<Int>
        get() = _productId


    fun setProductId(productId: Int) {
        _productId.value = productId
    }

    //주문 상세내역
    private val _orderDetail = MutableLiveData<OrderResponse>()
    val orderDetail: LiveData<OrderResponse>
        get() = _orderDetail


    //주문 내역
    private val _orderList = MutableLiveData<List<OrderResponse>>()
    val orderList: LiveData<List<OrderResponse>>
        get() = _orderList

    // 정보 (등급, 유저, 주문내역)
    private val _info = MutableLiveData<UserResponse>()
    val info: LiveData<UserResponse>
        get() = _info


    // 장바구니
    val shoppingList = mutableListOf<ShoppingCart>()

    fun addShoppingList(shoppingCart: ShoppingCart) {
        val position = checkDuplication(shoppingCart.menuName)
        if (position == -1) {
            shoppingList.add(shoppingCart)
        } else {
            shoppingList[position].addDupMenu(shoppingCart)
        }
    }

    private fun checkDuplication(itemName: String): Int {
        var position = -1
        shoppingList.forEachIndexed { index, item ->
            if (item.menuName == itemName)
                position = index
        }
        return position
    }

    //주문
    fun orderShoppingList(dto: Order) {

        viewModelScope.launch {
            kotlin.runCatching {
                RetrofitUtil.orderService.makeOrder(dto)
            }.onSuccess {
                Log.d(TAG, "orderShoppingList: 성공")
            }.onFailure {
                Log.d(TAG, "orderShoppingList: ${it}")
                Log.d(TAG, "orderShoppingList: 실패")
            }
        }
    }

    //주문 내역 초기화
    fun clearShoppingList() {
        shoppingList.clear()
    }

    //6개월 주문 내역 갱신
    fun getLast6MonthOrder(id: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                RetrofitUtil.orderService.getLast6MonthOrder(id)
            }.onSuccess {
                _orderList.value = it
                Log.d(TAG, "orderShoppingList: 성공")
            }.onFailure {
                Log.d(TAG, "orderShoppingList: ${it.cause}")
                Log.d(TAG, "orderShoppingList: 실패")
            }
        }
    }

    //주문 아이EL로 주문 조회
    fun getOrderById(id: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                RetrofitUtil.orderService.getOrderDetail(id)
            }.onSuccess {
                _orderDetail.value = it
                Log.d(TAG, "getOrderById:  성공")
            }.onFailure {
                Log.d(TAG, "getOrderById: ${it.cause}")
                Log.d(TAG, "getOrderById: 실패")

            }
        }
    }

    // 사용자 정보, 주문내역, 등급 정보
    fun getUserInfo(id: String) {
        viewModelScope.launch {
            runCatching {
                RetrofitUtil.userService.getUserInfo(id)
            }.onSuccess {
                _info.value = it
                Log.d(TAG, "getUserInfo: 정보 받아옴")
            }.onFailure {
                Log.d(TAG, "getUserInfo: 실패")
            }
        }
    }

    fun addToShoppingList(item: ShoppingCart) {
        // 기존 장바구니에 동일한 메뉴가 있는지 확인
        val existingItem = shoppingList.find { it.menuId == item.menuId }
        if (existingItem != null) {
            // 동일한 메뉴가 있으면 수량만 증가
            existingItem.menuCnt += item.menuCnt
            existingItem.totalPrice = existingItem.menuPrice * existingItem.menuCnt
        } else {
            // 없으면 새로 추가
            shoppingList.add(item)
        }
    }

}