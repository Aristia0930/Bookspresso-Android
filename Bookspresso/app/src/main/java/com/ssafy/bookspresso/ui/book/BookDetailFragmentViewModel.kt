package com.ssafy.bookspresso.ui.book

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.bookspresso.data.model.dto.Comment
import com.ssafy.bookspresso.data.model.response.ProductWithCommentResponse
import com.ssafy.bookspresso.data.remote.RetrofitUtil
import kotlinx.coroutines.launch

private const val TAG = "MenuDetailFragmentViewM_싸피"
class MenuDetailFragmentViewModel(private val handle: SavedStateHandle): ViewModel() {
    var productId = handle.get<Int>("productId") ?: 0
        set(value){
            handle.set("productId", value)
            field = value
        }

    private val _productInfo = MutableLiveData<ProductWithCommentResponse>()
    val productInfo: LiveData<ProductWithCommentResponse>
        get() = _productInfo

    fun getProductInfo(){
        getProductInfo(productId)
    }

    fun getProductInfo(pId:Int) {
        viewModelScope.launch{
            var info:ProductWithCommentResponse
            try{
                info = RetrofitUtil.productService.getProductWithComments(pId)
            }catch (e:Exception){
                info = ProductWithCommentResponse()
            }
            _productInfo.value = info
        }
    }

    fun insertComment(dto: Comment){
        viewModelScope.launch {
            try{
                RetrofitUtil.commentService.insert(dto)
                getProductInfo(dto.productId)
            }catch (e:Exception){
                Log.d(TAG, "insertComment: 실패")
            }


        }
    }

    //댓글 삭제
    fun deleteComment(id:Int,productId:Int){
        viewModelScope.launch {
            try{
                RetrofitUtil.commentService.delete(id)
                getProductInfo(productId)
            }catch (e:Exception){
                Log.d(TAG, "insertComment: 실패")
            }


        }
    }

    //댓글 업데이트
    fun updateComment(dto:Comment){
        viewModelScope.launch {
            try{
                RetrofitUtil.commentService.update(dto)
                getProductInfo(productId)
            }catch (e:Exception){
                Log.d(TAG, "insertComment: 실패")
            }


        }
    }



}