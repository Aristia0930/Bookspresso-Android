package com.ssafy.bookspresso.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.airbnb.lottie.LottieAnimationView
import com.ssafy.bookspresso.R
import com.ssafy.bookspresso.base.ApplicationClass
import com.ssafy.bookspresso.base.BaseActivity
import com.ssafy.bookspresso.data.local.SharedPreferencesUtil
import com.ssafy.bookspresso.databinding.ActivityLoginBinding
import com.ssafy.bookspresso.ui.login.JoinFragment
import com.ssafy.bookspresso.ui.login.LoginFragment
import com.ssafy.bookspresso.ui.login.LoginFragmentViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val TAG = "LoginActivity_싸피"
class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate) {

    private val loginFragmentViewModel: LoginFragmentViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        //로그인 된 상태인지 확인
        var user = ApplicationClass.sharedPreferencesUtil.getUser()

        //로그인 상태 확인. id가 있다면 로그인 된 상태
//        if (user.id != ""){
//            openFragment(1)
//        } else {
//            // 가장 첫 화면은 홈 화면의 Fragment로 지정
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.frame_layout_login, LoginFragment())
//                .commit()
//        }

        if(user.id!="") {

            lifecycleScope.launch {
                val lottieView = binding.lottieLoading
                lottieView.visibility = View.VISIBLE
                lottieView.playAnimation()

                ApplicationClass.sharedPreferencesUtil.deleteUser()
                val isLoggedIn = loginFragmentViewModel.jwtIsUsed()
                user = ApplicationClass.sharedPreferencesUtil.getUser()
                delay(2000L)
                lottieView.cancelAnimation()
                lottieView.visibility = View.GONE
                if (isLoggedIn && user.id != "") {
                    openFragment(1)
                } else {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_layout_login, LoginFragment())
                        .commit()
                }
            }
        }else{
            supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout_login, LoginFragment())
                .commit()
        }
    }

    fun openFragment(int: Int){
        val transaction = supportFragmentManager.beginTransaction()
        when(int){
            1 -> {
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent)
            }
            2 -> transaction.replace(R.id.frame_layout_login, JoinFragment())
                .addToBackStack(null)

            3 -> {
                // 회원가입한 뒤 돌아오면, 2번에서 addToBackStack해 놓은게 남아 있어서,
                // stack을 날려 줘야 한다. stack날리기.
                supportFragmentManager.popBackStack()
                transaction.replace(R.id.frame_layout_login, LoginFragment())
            }
            4 -> {
                val intent = Intent(this, ManagerActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent)
            }
        }
        transaction.commit()
    }

}
