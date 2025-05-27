package com.ssafy.bookspresso.ui.cafe

import android.content.Context
import android.graphics.Color
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import androidx.core.widget.addTextChangedListener
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

    private var selectedType = "drink"

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
        initSearch()
    }

    private fun initSearch() {
        binding.searchEditText.addTextChangedListener { text ->
            val query = text.toString().trim()
            Log.d(TAG, "initSearch: 검색어 입력됨: $query")
            orderViewModel.searchProduct(selectedType, query)
        }
    }


    private fun initData() {
        val tabLayout = binding.tabLayout

        // 음료 탭 생성
        val drinkTab = tabLayout.newTab()
        val drinkTabView = layoutInflater.inflate(R.layout.tab_item_drink, null)
        drinkTab.customView = drinkTabView
        tabLayout.addTab(drinkTab)

        // 디저트 탭 생성
        val dessertTab = tabLayout.newTab()
        val dessertTabView = layoutInflater.inflate(R.layout.tab_item_dessert, null)
        dessertTab.customView = dessertTabView
        tabLayout.addTab(dessertTab)

        tabLayout.selectTab(drinkTab)
        applySelectedTabStyle(drinkTab)
        removeSelectedTabStyle(dessertTab)
        orderViewModel.filterProductList("drink")

        orderViewModel.filteredProductList.distinctUntilChanged().observe(viewLifecycleOwner) {
            menuAdapter.productList = it
            menuAdapter.notifyDataSetChanged()
        }
        orderViewModel.getProductList()
    }

    private fun initEvent() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                applySelectedTabStyle(tab)

                selectedType = when (tab.position) {
                    0 -> "drink"
                    1 -> "dessert"
                    else -> "drink"
                }
                orderViewModel.filterProductList(selectedType)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                removeSelectedTabStyle(tab)
            }

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        binding.floatingBtn.setOnClickListener {
            mainActivity.openFragment(1)
        }
    }

    private fun applySelectedTabStyle(tab: TabLayout.Tab) {
        val customView = tab.customView ?: return
        val tabItem = customView.findViewById<LinearLayout>(R.id.tab_item)
        val textView = customView.findViewById<TextView>(R.id.tab_text)
        val iconView = customView.findViewById<ImageView>(R.id.tab_icon)
        tabItem.setPadding(0, 16, 0, 16)
        tabItem.setBackgroundResource(R.drawable.tab_selected_background)
        textView.setTextColor(Color.WHITE)
        iconView.setColorFilter(Color.WHITE)
    }

    private fun removeSelectedTabStyle(tab: TabLayout.Tab) {
        val customView = tab.customView ?: return
        val tabItem = customView.findViewById<LinearLayout>(R.id.tab_item)
        val textView = customView.findViewById<TextView>(R.id.tab_text)
        val iconView = customView.findViewById<ImageView>(R.id.tab_icon)
        tabItem.background = null
        textView.setTextColor(Color.parseColor("#BC8F66"))
        iconView.setColorFilter(Color.parseColor("#BC8F66"))
    }

}