package com.ssafy.bookspresso.ui.order

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.distinctUntilChanged
import androidx.recyclerview.widget.GridLayoutManager
import com.ssafy.bookspresso.R
import com.ssafy.bookspresso.base.BaseFragment
import com.ssafy.bookspresso.databinding.FragmentOrderBinding
import com.ssafy.bookspresso.ui.MainActivity
import com.ssafy.bookspresso.ui.MainActivityViewModel

// 하단 주문 탭
private const val TAG = "OrderFragment_싸피"
class OrderFragment : BaseFragment<FragmentOrderBinding>(FragmentOrderBinding::bind, R.layout.fragment_order){
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

        binding.recyclerViewMenu.apply {
            layoutManager = GridLayoutManager(context,3)
            adapter = menuAdapter
        }

        initData()
        initEvent()
    }

    private fun initEvent(){
        binding.floatingBtn.setOnClickListener{
            //장바구니 이동
            mainActivity.openFragment(1)
        }

        binding.btnMap.setOnClickListener{
            mainActivity.openFragment(4)
        }

    }

    private fun initData(){
        // 상품목록 조회.

        orderViewModel.productList.distinctUntilChanged().observe(viewLifecycleOwner) {
            menuAdapter.productList = it
            menuAdapter.notifyDataSetChanged()
        }
        orderViewModel.getProductList()


    }
}