package com.ssafy.bookspresso.ui.cafe

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.distinctUntilChanged
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.ssafy.bookspresso.R
import com.ssafy.bookspresso.base.BaseFragment
import com.ssafy.bookspresso.databinding.FragmentCafeBinding
import com.ssafy.bookspresso.databinding.FragmentOrderBinding
import com.ssafy.bookspresso.ui.MainActivity
import com.ssafy.bookspresso.ui.MainActivityViewModel

// 하단 주문 탭
private const val TAG = "OrderFragment_싸피"
class CafeFragment : BaseFragment<FragmentCafeBinding>(FragmentCafeBinding::bind, R.layout.fragment_cafe){
    private lateinit var menuAdapter: MenuAdapter
    private lateinit var mainActivity: MainActivity

    private val activityViewModel: MainActivityViewModel by activityViewModels()
    private val orderViewModel: OrderViewModel by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        menuAdapter = MenuAdapter(arrayListOf()) { productId ->
            activityViewModel.setProductId(productId)
            mainActivity.openFragment(3)
        }

        binding.menuRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = menuAdapter
        }

        initData()
        initEvent()
    }

    private fun initEvent(){

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val selectedType = when (tab?.position) {
                    0 -> "drink"
                    1 -> "dessert"
                    else -> "drink"
                }
                orderViewModel.filterProductList(selectedType)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        binding.floatingBtn.setOnClickListener{
            //장바구니 이동
            mainActivity.openFragment(1)
        }

    }

    private fun initData(){
        // 상품목록 조회.

        orderViewModel.filteredProductList.distinctUntilChanged().observe(viewLifecycleOwner) {
            menuAdapter.productList = it
            menuAdapter.notifyDataSetChanged()
        }
        orderViewModel.getProductList()


    }
}