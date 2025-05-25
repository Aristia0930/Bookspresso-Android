package com.ssafy.bookspresso.ui.book

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.bookspresso.base.ApplicationClass
import com.ssafy.bookspresso.data.local.SharedPreferencesUtil
import com.ssafy.bookspresso.data.model.dto.Book
import com.ssafy.bookspresso.data.model.dto.BookRental
import com.ssafy.bookspresso.data.model.dto.Product
import com.ssafy.bookspresso.data.remote.RetrofitUtil
import kotlinx.coroutines.launch
import kotlin.math.log

private const val TAG = "BookViewModel_싸피"
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

    fun rentalBook(isbn: String){
        viewModelScope.launch {
            runCatching {
                val userId = ApplicationClass.sharedPreferencesUtil.getUser().id
                val bookRental = BookRental(userId, isbn, 1000)
                Log.d(TAG, "rentalBook: $bookRental")
                RetrofitUtil.bookService.rentalBook(bookRental)
            }.onSuccess {
                Log.d(TAG, "rentalBook: 대출 성공여부 $it")
            }.onFailure {
                Log.d(TAG, "rentalBook: 오류 ${it.cause}")
            }
        }
    }
    fun returnBook(isbn: String){
        viewModelScope.launch {
            runCatching {
                RetrofitUtil.bookService.returnBook(isbn)
            }.onSuccess {
                Log.d(TAG, "returnBook: 반납 성공여부 $it")
            }.onFailure {
                Log.d(TAG, "returnBook: 오류 ${it.cause}")
            }
        }
    }
}