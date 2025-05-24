package com.ssafy.bookspresso.ui.book

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.bookspresso.base.ApplicationClass
import com.ssafy.bookspresso.data.model.dto.Book
import com.ssafy.bookspresso.data.model.dto.Product
import com.ssafy.bookspresso.data.remote.RetrofitUtil
import kotlinx.coroutines.launch

class BookViewModel : ViewModel() {

    private var _bookList = MutableLiveData<List<Book>>()
    val bookList: LiveData<List<Book>>
        get() = _bookList

    fun getProductList() {
        viewModelScope.launch {
            runCatching {
                RetrofitUtil.bookService.getBookList()
            }.onSuccess {
                _bookList.value = it
            }.onFailure {
                _bookList.value = arrayListOf()
            }
        }
    }
}