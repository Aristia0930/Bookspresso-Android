package com.ssafy.bookspresso.ui.my

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.bookspresso.R
import com.ssafy.bookspresso.base.BaseFragment
import com.ssafy.bookspresso.data.model.response.OrderResponse
import com.ssafy.bookspresso.databinding.FragmentOrderDetailBinding
import com.ssafy.bookspresso.ui.MainActivity
import com.ssafy.bookspresso.ui.MainActivityViewModel

private const val TAG = "OrderDetailFragment_싸피"
class OrderDetailFragment : BaseFragment<FragmentOrderDetailBinding>(
    FragmentOrderDetailBinding::bind,
    R.layout.fragment_order_detail
) {
    private lateinit var mainActivity: MainActivity
    private val activityViewModel : MainActivityViewModel by activityViewModels()
    private lateinit var orderAdapter : OrderDetailListAdapter
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity.hideBottomNav(true)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        orderAdapter= OrderDetailListAdapter(requireContext(), emptyList())

//        Log.d(TAG, "onViewCreated: $orderId")
        initAdapter()
        getList()
    }

    private fun initAdapter(){

        binding.recyclerViewOrderDetailList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = orderAdapter

        }
    }

    //주문 내역 조회
    fun getList(){
        var orderId=activityViewModel.myPageOrderId.value
        if (orderId != null) {
            activityViewModel.getOrderById(orderId)
        }

        activityViewModel.orderDetail.observe(viewLifecycleOwner){
            Log.d(TAG, "getList: ${it.details.size}")

            binding.tvOrderDate.text=it.orderDate.toString()
            binding.tvTotalPrice.text=totalPrice(it).toString()
            orderAdapter.list=it.details
            orderAdapter.notifyDataSetChanged()

        }

    }


    fun totalPrice(list: OrderResponse): Int {
        var price = 0



            for (detail in list.details) {
                price += detail.unitPrice * detail.quantity


        }

        return price
    }


    override fun onDestroy() {
        super.onDestroy()
        mainActivity.hideBottomNav(false)
    }
}