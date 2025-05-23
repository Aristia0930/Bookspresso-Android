package com.ssafy.bookspresso.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.bookspresso.base.ApplicationClass
import com.ssafy.bookspresso.data.model.dto.User
import com.ssafy.bookspresso.data.remote.RetrofitUtil
import kotlinx.coroutines.launch

private const val TAG = "LoginFragmentViewModel_싸피"

class LoginFragmentViewModel : ViewModel() {

    // user 객체를 livedata로 생성
    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    private val _check = MutableLiveData<Boolean>()
    val check: LiveData<Boolean>
        get() = _check

    // user 정보 조회
    fun login(loginId: String, loginPass: String) {
        // retrofit으로 호출
        viewModelScope.launch {
            runCatching {
                val response = RetrofitUtil.userService.login(User(loginId, loginPass))
                val token = response.headers()["Authorization"]

                ApplicationClass.sharedPreferencesUtil.addString("Authorization", "$token")
            }.onSuccess {
                runCatching {
                    RetrofitUtil.userService.info(User(loginId, loginPass))
                }.onSuccess {
                    _user.value = it
                }.onFailure {
                    _user.value = User()
                }

            }.onFailure {
                _user.value = User()
            }
        }
    }

    fun insert(id: String, pass: String, name: String) {
        viewModelScope.launch {
            runCatching {
                RetrofitUtil.userService.insert(User(id, name, pass))
            }.onSuccess { isJoinSuccess ->
                if (isJoinSuccess) {
                    Log.d(TAG, "insert: 회원가입 성공")

                } else {
                    Log.d(TAG, "insert: 회원가입 실패")
                }
            }.onFailure {
                Log.d(TAG, "insert: 에러")
            }
        }
    }

    fun isUsed(id: String) {
        viewModelScope.launch {
            runCatching {
                RetrofitUtil.userService.isUsedId(id)
            }.onSuccess { used ->
                _check.value = used
                if (used) {
                    Log.d(TAG, "isUsed: 사용증")
                } else {
                    Log.d(TAG, "isUsed: 사용 가능")
                }
            }.onFailure {
                _check.value = false
                Log.d(TAG, "isUsed: 에러")
            }
        }
    }

}