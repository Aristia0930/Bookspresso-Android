package com.ssafy.bookspresso.ui.book

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.distinctUntilChanged
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.tabs.TabLayout
import com.ssafy.bookspresso.R
import com.ssafy.bookspresso.base.BaseFragment
import com.ssafy.bookspresso.databinding.FragmentBookBinding
import com.ssafy.bookspresso.databinding.FragmentNfcBinding
import com.ssafy.bookspresso.ui.MainActivity
import com.ssafy.bookspresso.ui.MainActivityViewModel

private const val TAG = "NfcFragment_싸피"
class NfcFragment : BaseFragment<FragmentNfcBinding>(
    FragmentNfcBinding::bind,
    R.layout.fragment_nfc
) {
    private lateinit var mainActivity: MainActivity
    private val viewModel: BookViewModel by viewModels()
    private var selectedMode: String = "rent"

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEvent()
    }

    private fun initEvent() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                selectedMode = when (tab?.position) {
                    0 -> "rent"
                    1 -> "return"
                    else -> "rent"
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun initData() {

    }

    fun onNfcScanned(data: String) {
//        dialog!!.cancel()
        showToast("책 정보 : $data ")
        Log.d(TAG, "onNfcScanned: $selectedMode")
        if(selectedMode == "rent"){
            viewModel.rentalBook(data)
        }else if(selectedMode == "return"){
            viewModel.returnBook(data)
        }
    }
}