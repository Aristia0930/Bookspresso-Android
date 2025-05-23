package com.ssafy.bookspresso.ui.order

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.bookspresso.data.model.dto.Comment
import com.ssafy.bookspresso.data.remote.RetrofitUtil
import kotlinx.coroutines.launch

private const val TAG = "CommentViewModel_싸피"
class CommentViewModel : ViewModel(){
    
    
    
    fun insertComment(dto:Comment ){
        viewModelScope.launch {
            try{
                RetrofitUtil.commentService.insert(dto)
            }catch (e:Exception){
                Log.d(TAG, "insertComment: 실패")
            }
            
            
        }
    }
}