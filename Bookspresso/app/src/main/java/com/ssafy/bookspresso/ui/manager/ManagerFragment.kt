package com.ssafy.bookspresso.ui.manager

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.bookspresso.R
import com.ssafy.bookspresso.base.ApplicationClass
import com.ssafy.bookspresso.base.BaseFragment
import com.ssafy.bookspresso.data.model.response.OrderResponse
import com.ssafy.bookspresso.databinding.FragmentManagerBinding
import com.ssafy.bookspresso.ui.LoginActivity
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class ManagerFragment : BaseFragment<FragmentManagerBinding>(
    FragmentManagerBinding::bind,
    R.layout.fragment_manager
) {

    private val managerViewModel: ManagerViewModel by activityViewModels()

    private var orderListManagerAdapter :OrderListManagerAdapter=OrderListManagerAdapter(emptyList())

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        orderList()
        val today = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일", Locale.KOREAN)
        binding.tvCurrentTime.text = today.format(formatter)

        binding.logout.setOnClickListener{
            ApplicationClass.sharedPreferencesUtil.deleteUser()
            ApplicationClass.sharedPreferencesUtil.deleteUserCookie()
            ApplicationClass.sharedPreferencesUtil.remove("Authorization")

            val intent = Intent(requireContext(), LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK + Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(intent)

        }
    }

    private fun initAdapter(){
        orderListManagerAdapter.setItemClickListener1(object :OrderListManagerAdapter.ItemClickListener{
            override fun onClick(view: View, position: Int, orderid: Int) {
                managerViewModel.cancel(orderid,position)

            }

        })

        orderListManagerAdapter.setItemClickListener2(object :OrderListManagerAdapter.ItemClickListener{
            override fun onClick(view: View, position: Int, orderid: Int) {
                managerViewModel.accept(orderid,position)

            }

        })

        binding.rvOrders.apply{
            layoutManager=LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            adapter=orderListManagerAdapter
        }
    }

    private fun orderList(){
        managerViewModel.getListOrder()
        managerViewModel.orderList.observe(viewLifecycleOwner) { list ->
            if (list != null) {

//                orderListManagerAdapter.list = totalPrice(list)
                val filteredList = list.filter { it.orderCompleted == 'N' }
                binding.tvCompletedCount.text=(list.size-filteredList.size).toString()
                binding.tvPendingCount.text=filteredList.size.toString()
                val updatedList = totalPrice(filteredList)
                orderListManagerAdapter.list=updatedList
                orderListManagerAdapter.notifyDataSetChanged()
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

}