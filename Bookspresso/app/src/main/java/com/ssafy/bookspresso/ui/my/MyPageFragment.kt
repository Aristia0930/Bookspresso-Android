package com.ssafy.bookspresso.ui.my

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.ssafy.bookspresso.R
import com.ssafy.bookspresso.base.ApplicationClass
import com.ssafy.bookspresso.base.BaseFragment
import com.ssafy.bookspresso.data.model.response.OrderResponse
import com.ssafy.bookspresso.databinding.FragmentMypageBinding
import com.ssafy.bookspresso.ui.MainActivity
import com.ssafy.bookspresso.ui.MainActivityViewModel

private const val TAG = "MyPageFragment_싸피"

class MyPageFragment : BaseFragment<FragmentMypageBinding>(
    FragmentMypageBinding::bind,
    R.layout.fragment_mypage
) {
    private var orderAdapter: OrderListAdapter = OrderListAdapter(emptyList())
    private lateinit var mainActivity: MainActivity

    private val activityViewModel: MainActivityViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerObserver()
//        getUserData()
        initAdapter()
        initOrderData("")
        initEvent()


    }


    private fun initAdapter() {

        orderAdapter.setItemClickListener(object : OrderListAdapter.ItemClickListener {
            override fun onClick(view: View, position: Int, orderid: Int) {
                activityViewModel.setOrderId(orderid)
                mainActivity.openFragment(2)
            }
        })

        binding.recyclerViewOrder.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = orderAdapter
        }
    }

    private fun initEvent() {
        binding.logout.setOnClickListener {
            mainActivity.openFragment(5)
        }
    }

    private fun getUserData() {
        val user = ApplicationClass.sharedPreferencesUtil.getUser()
        binding.textUserName.text = user.name
        activityViewModel.getUserInfo(user.id)

    }

    private fun initOrderData(id: String) {
        // 최근 6개월 주문내역
        activityViewModel.getLast6MonthOrder(ApplicationClass.sharedPreferencesUtil.getUser().id)
        activityViewModel.orderList.observe(viewLifecycleOwner) {
            var list = activityViewModel.orderList.value
            if (list != null) {
                if (list.size != 0) {
                    binding.tvMypageOrderEmpty.visibility = View.GONE
                    binding.recyclerViewOrder.visibility = View.VISIBLE
                    Log.d(TAG, "initOrderData: ${list}")

                    orderAdapter.list = totalPrice(list)
                    orderAdapter.notifyDataSetChanged()
                } else {
                    binding.recyclerViewOrder.visibility = View.GONE
                    binding.tvMypageOrderEmpty.visibility = View.VISIBLE
                    Log.d(TAG, "initOrderData: 주문 내역이 없습니다")
                }
            }
        }

    }

    fun totalPrice(list: List<OrderResponse>): List<OrderResponse> {
        val newList = mutableListOf<OrderResponse>()
        for (data in list) {
            var price = 0
            var count = 0

            for (detail in data.details) {
                price += detail.unitPrice * detail.quantity
                count += detail.quantity
            }
            Log.d("totalPrice", "orderId=${data.orderId}, price=$price, count=$count")
            val updatedOrder = data.copy(
                totalPrice = price,
                orderCount = count
            )

            newList.add(updatedOrder)
        }

        return newList
    }


    fun registerObserver() {
        activityViewModel.info.observe(viewLifecycleOwner) {
            val grade = it.grade
            val user = it.user

//            context?.let { it1 ->
//                Glide.with(it1)
//                    .load("${ApplicationClass.GRADE_URL}${grade.img}")
//                    .into(binding.imageLevel)
//            }
//
//            binding.textUserLevel.text = "${grade.title} ${grade.step}단계"
//            binding.proBarUserLevel.max = grade.stepMax
//            binding.proBarUserLevel.progress = grade.to
//
//            binding.textUserNextLevel.text = "${grade.to}/${grade.stepMax}"
//            binding.textLevelRest.text = "다음 레벨까지 ${grade.stepMax - grade.to}잔 남았습니다."

        }
    }

}