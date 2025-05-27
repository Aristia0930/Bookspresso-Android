package com.ssafy.bookspresso.ui.home

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.ssafy.bookspresso.R
import com.ssafy.bookspresso.base.ApplicationClass
import com.ssafy.bookspresso.base.BaseFragment
import com.ssafy.bookspresso.data.model.dto.ShoppingCart
import com.ssafy.bookspresso.databinding.FragmentHomeBinding
import com.ssafy.bookspresso.ui.MainActivity
import com.ssafy.bookspresso.ui.MainActivityViewModel
import com.ssafy.bookspresso.util.CommonUtils

class HomeFragment : BaseFragment<FragmentHomeBinding>(
    FragmentHomeBinding::bind,
    R.layout.fragment_home
){
    private var orderAdapter : LatestOrderAdapter = LatestOrderAdapter(emptyList())
    private lateinit var mainActivity: MainActivity
    private val viewModel: HomeFragmentViewModel by viewModels()
    private val activityViewModel : MainActivityViewModel by activityViewModels()
    private lateinit var id:String

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initUserData()
//        initOrderData(id)
        initRecommendData()


    }

    private fun initRecommendData() {
        viewModel.getRecommendation()
        viewModel.recommendation.observe(viewLifecycleOwner){
            binding.rcmdBookTitle.text = it.book.title
            Glide.with(this)
                .load("${ApplicationClass.BOOK_IMGS_URL}${it.book.img}")
                .into(binding.rcmdBookCoverImage)
            binding.rcmdDrinkName.text = it.drink.name
            Glide.with(this)
                .load("${ApplicationClass.MENU_IMGS_URL}${it.drink.img}")
                .into(binding.rcmdDrinkImage)
            binding.rcmdDessertName.text = it.dessert.name
            Glide.with(this)
                .load("${ApplicationClass.MENU_IMGS_URL}${it.dessert.img}")
                .into(binding.rcmdDessertImage)

        }
    }

    private fun initUserData(){
        val user = ApplicationClass.sharedPreferencesUtil.getUser()
        id = user.id
        binding.textUserName.text = "${user.name} 님, 환영합니다."
    }


    fun initAdapter() {

        orderAdapter.setItemClickListener(object : LatestOrderAdapter.ItemClickListener {
            override fun onClick(view: View, position: Int, orderid: Int) {
                activityViewModel.getOrderById(orderid)
                activityViewModel.orderDetail.observe(viewLifecycleOwner) {
                    if (it != null) {
                        // 주문 상세 정보로부터 장바구니 항목 생성
                        for (detail in it.details) {
                            val shoppingItem = ShoppingCart(
                                menuId = detail.productId,
                                menuImg = detail.productImg,
                                menuName = detail.productName,
                                menuPrice = detail.unitPrice,
                                menuCnt = detail.quantity,
                                totalPrice = detail.sumPrice,
                                type = detail.productType,
                            )

                            // 액티비티의 ViewModel을 통해 장바구니에 추가
                            activityViewModel.addToShoppingList(shoppingItem)
                        }

                        // 장바구니 화면으로 이동
                        mainActivity.openFragment(1)

                        showToast("선택한 주문이 장바구니에 담겼습니다.")
                    }
                }
            }
        })

//        binding.recyclerViewLatestOrder.apply {
//            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//            adapter = orderAdapter
//        }

    }

    private fun initOrderData(id: String) {
        // 최근 6개월 주문내역
        viewModel.getLastMonthOrder(id)
        viewModel.orderList.observe(viewLifecycleOwner) {
            orderAdapter.orderList = CommonUtils.calcTotalPrice(it)
            orderAdapter.notifyDataSetChanged()


        }

    }



}