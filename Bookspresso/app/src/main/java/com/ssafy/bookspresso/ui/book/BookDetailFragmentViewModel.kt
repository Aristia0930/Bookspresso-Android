package com.ssafy.bookspresso.ui.book

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.bookspresso.data.model.dto.Book
import com.ssafy.bookspresso.data.model.dto.Comment
import com.ssafy.bookspresso.data.model.response.ProductWithCommentResponse
import com.ssafy.bookspresso.data.remote.RetrofitUtil
import kotlinx.coroutines.launch

private const val TAG = "MenuDetailFragmentViewM_싸피"
class BookDetailFragmentViewModel(private val handle: SavedStateHandle): ViewModel() {
    var isbn = handle.get<String>("isbn") ?: ""
        set(value){
            handle.set("isbn", value)
            field = value
        }

    private val _bookInfo = MutableLiveData<Book>()
    val bookInfo: LiveData<Book>
        get() = _bookInfo

    fun getBookInfo(){
        getBookInfo(isbn)
    }

    fun getBookInfo(isbn: String) {
        viewModelScope.launch{
            var info:Book
            try{
                info = RetrofitUtil.bookService.getBook(isbn)
            }catch (e:Exception){
                info = Book()
            }
            _bookInfo.value = info
        }
    }

}