package com.ssafy.bookspresso.ui.login

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
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
    private val googleLoginLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        Log.d(TAG, "런처: 실행")
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)

            if (task.isSuccessful) {
                try {
                    val account = task.getResult(ApiException::class.java)
                    val idToken = account.idToken
                    Log.d(TAG, "idToken: $idToken")
                    idToken?.let {
                        viewModel.loginWithGoogle(it)
                    } ?: run {
                        Log.e(TAG, "idToken is null")
                    }
                } catch (e: ApiException) {
                    Log.e(TAG, "Google 로그인 예외: ${e.statusCode} - ${e.message}", e)
                }
            } else {
                Log.e(TAG, "GoogleSignIn task failed", task.exception)
            }
        } else {
            Log.e(TAG, "GoogleSignIn resultCode != RESULT_OK")
        }
    }


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

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("624340357559-uudsqmkttvla2mnq4sm99dcgjbu4nqoi.apps.googleusercontent.com")  // Firebase 콘솔에서 확인 가능
            .requestEmail()
            .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
        binding.btnGoogle.setOnClickListener {
            Log.d(TAG, "onViewCreated: 버튼클릭")
            val signInIntent = mGoogleSignInClient.signInIntent
            googleLoginLauncher.launch(signInIntent)
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