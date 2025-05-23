package com.ssafy.bookspresso.ui

import android.content.Intent
import android.os.Bundle
import com.ssafy.bookspresso.R
import com.ssafy.bookspresso.base.ApplicationClass
import com.ssafy.bookspresso.base.BaseActivity
import com.ssafy.bookspresso.databinding.ActivityLoginBinding
import com.ssafy.bookspresso.ui.login.JoinFragment
import com.ssafy.bookspresso.ui.login.LoginFragment

class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //로그인 된 상태인지 확인
        val user = ApplicationClass.sharedPreferencesUtil.getUser()

        //로그인 상태 확인. id가 있다면 로그인 된 상태
        if (user.id != ""){
            openFragment(1)
        } else {
            // 가장 첫 화면은 홈 화면의 Fragment로 지정
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
        }
        transaction.commit()
    }

}
