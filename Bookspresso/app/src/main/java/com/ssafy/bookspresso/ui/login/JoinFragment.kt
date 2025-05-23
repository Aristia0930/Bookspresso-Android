package com.ssafy.bookspresso.ui.login

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.ssafy.bookspresso.R
import com.ssafy.bookspresso.base.BaseFragment
import com.ssafy.bookspresso.databinding.FragmentJoinBinding
import com.ssafy.bookspresso.ui.LoginActivity

private const val TAG = "JoinFragment_싸피"

class JoinFragment : BaseFragment<FragmentJoinBinding>(
    FragmentJoinBinding::bind,
    R.layout.fragment_join
) {
    private var checkedId = false
    private val viewModel: LoginFragmentViewModel by viewModels()
    private lateinit var loginActivity: LoginActivity
    override fun onAttach(context: Context) {
        super.onAttach(context)
        loginActivity = context as LoginActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerObserver()

        //id 중복 확인 버튼
        binding.btnConfirm.setOnClickListener {
            val id = binding.editTextJoinID.text.toString()
            viewModel.isUsed(id)
        }

        // 회원가입 버튼
        binding.btnJoin.setOnClickListener {
            val id = binding.editTextJoinID.text.toString()
            val pass = binding.editTextJoinPW.text.toString()
            val name = binding.editTextJoinName.text.toString()
            if (checkedId) {
                viewModel.insert(id, pass, name)
                parentFragmentManager.popBackStack()
            } else {
                binding.btnConfirm.setBackgroundResource(R.drawable.button_regular_red)
                showToast("ID를 체크해 주세요.")
            }
        }
    }

    fun registerObserver() {
        viewModel.check.observe(viewLifecycleOwner) {
            checkedId = !it
            if (it) {
                showToast("사용할 수 없는 ID 입니다.")
            } else {
                showToast("사용가능한 ID 입니다.")
                binding.btnConfirm.setBackgroundResource(R.drawable.button_regular)
            }
        }
    }
}