package com.ssafy.bookspresso.ui.login

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.viewModels
import com.ssafy.bookspresso.R
import com.ssafy.bookspresso.base.BaseFragment
import com.ssafy.bookspresso.databinding.FragmentJoinBinding
import com.ssafy.bookspresso.ui.LoginActivity
import java.util.*

private const val TAG = "JoinFragment_싸피"

class JoinFragment : BaseFragment<FragmentJoinBinding>(
    FragmentJoinBinding::bind,
    R.layout.fragment_join
) {
    private var checkedId = false
    private val viewModel: LoginFragmentViewModel by viewModels()
    private lateinit var loginActivity: LoginActivity
    private var timer: Timer? = null
    private val delay = 800L // 0.8초 후 중복 확인

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loginActivity = context as LoginActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerObserver()

        // 아이디 실시간 중복 체크
        binding.editTextJoinID.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                checkedId = false
                binding.textIDStatus.visibility = View.GONE

                timer?.cancel()
                timer = Timer()
                timer?.schedule(object : TimerTask() {
                    override fun run() {
                        val id = s.toString().trim()
                        loginActivity.runOnUiThread {
                            if (id.isNotEmpty()) {
                                viewModel.isUsed(id)
                            } else {
                                binding.textIDStatus.apply {
                                    text = "아이디를 입력해 주세요."
                                    setTextColor(Color.RED)
                                    visibility = View.VISIBLE
                                }
                            }
                        }
                    }
                }, delay)
            }

        })

        // 회원가입 버튼
        binding.btnJoin.setOnClickListener {
            val id = binding.editTextJoinID.text.toString()
            val pass = binding.editTextJoinPW.text.toString()
            val name = binding.editTextJoinName.text.toString()

            if (checkedId) {
                viewModel.insert(id, pass, name)
                showToast("회원가입 완료!")
                parentFragmentManager.popBackStack()
            } else {
                showToast("사용 가능한 ID를 입력해 주세요.")
            }
        }
    }

    private fun registerObserver() {
        viewModel.check.observe(viewLifecycleOwner) { isDuplicate ->
            checkedId = !isDuplicate
            if (isDuplicate) {
                binding.textIDStatus.apply {
                    text = "이미 사용 중인 ID입니다."
                    setTextColor(Color.RED)
                    visibility = View.VISIBLE
                }
            } else {
                binding.textIDStatus.apply {
                    text = "사용 가능한 ID입니다."
                    setTextColor(Color.parseColor("#4CAF50")) // 초록
                    visibility = View.VISIBLE
                }
            }
        }
    }
}
