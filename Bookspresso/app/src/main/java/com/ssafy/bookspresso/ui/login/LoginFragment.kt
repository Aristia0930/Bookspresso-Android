package com.ssafy.bookspresso.ui.login

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.ssafy.bookspresso.R
import com.ssafy.bookspresso.base.ApplicationClass
import com.ssafy.bookspresso.base.BaseFragment
import com.ssafy.bookspresso.databinding.FragmentLoginBinding
import com.ssafy.bookspresso.ui.LoginActivity

private const val TAG = "LoginFragment_싸피"
class LoginFragment : BaseFragment<FragmentLoginBinding>(
    FragmentLoginBinding::bind,
    R.layout.fragment_login
) {

    private val viewModel: LoginFragmentViewModel by viewModels()
    private lateinit var loginActivity: LoginActivity
    override fun onAttach(context: Context) {
        super.onAttach(context)
        loginActivity = context as LoginActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerObserver()
        binding.btnLogin.setOnClickListener {
            val id = binding.editTextLoginID.text.toString()
            val pass = binding.editTextLoginPW.text.toString()
            viewModel.login(id, pass)
        }
        binding.btnJoin.setOnClickListener {
            loginActivity.openFragment(2)
        }
    }

    fun registerObserver() {
        viewModel.user.observe(viewLifecycleOwner) {
            if (it.id.isNotEmpty()) {
                Log.d(TAG, "registerObserver: ${it.role}")

                showToast("로그인 되었습니다")
                if(it.role=="ROLE_USER"){
                    ApplicationClass.sharedPreferencesUtil.addUser(it)
                    loginActivity.openFragment(1)
                }else{
                    ApplicationClass.sharedPreferencesUtil.addUser(it)
                    loginActivity.openFragment(4)
                }

            } else {
                showToast("ID 혹은 Password를 확인해 주세요.")
            }
        }
    }

}