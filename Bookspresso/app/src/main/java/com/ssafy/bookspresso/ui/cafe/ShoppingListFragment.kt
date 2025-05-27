package com.ssafy.bookspresso.ui.cafe

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.bookspresso.R
import com.ssafy.bookspresso.base.ApplicationClass
import com.ssafy.bookspresso.base.BaseFragment
import com.ssafy.bookspresso.data.model.dto.KakaoReadyRequest
import com.ssafy.bookspresso.data.model.dto.Order
import com.ssafy.bookspresso.data.model.dto.OrderDetail
import com.ssafy.bookspresso.databinding.FragmentShoppingListBinding
import com.ssafy.bookspresso.ui.MainActivity
import com.ssafy.bookspresso.ui.MainActivityViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.UUID

private const val TAG = "ShoppingListF_싸피"
const val ORDER_ID = "orderId"

//장바구니 Fragment
class ShoppingListFragment : BaseFragment<FragmentShoppingListBinding>(
    FragmentShoppingListBinding::bind,
    R.layout.fragment_shopping_list
) {
    private lateinit var shoppingListAdapter: ShoppingListAdapter
    private lateinit var mainActivity: MainActivity
    private var isShop: Boolean = true
    private var hasTagged: Boolean = false
    private var reOrderId = -1
    private var dialog: AlertDialog? = null

    private val activityViewModel: MainActivityViewModel by activityViewModels()
    private val orderViewModel: OrderViewModel by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity.hideBottomNav(true)

        // 재주문일때는 argument로 들어온다.
        arguments?.let {
            reOrderId = it.getInt("orderId", -1)
            Log.d(TAG, "onCreate: reOrderId :  $reOrderId")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val savedTable = ApplicationClass.sharedPreferencesUtil.getTable()
        if (savedTable != null) {
            hasTagged = true
            table = savedTable
            binding.textRoomNumber.text = "방 번호 : ${savedTable.replace("Table:", "")}"
        }
        initAdapter()
        initEvent()
        refreshList()
    }

    private fun initAdapter() {
        shoppingListAdapter = ShoppingListAdapter(activityViewModel.shoppingList) { itemToDelete ->
            activityViewModel.shoppingList.remove(itemToDelete) // ViewModel에서 삭제
            refreshList()
        }



        binding.recyclerViewShoppingList.apply {
            adapter = shoppingListAdapter
            //원래의 목록위치로 돌아오게함
            adapter?.stateRestorationPolicy =
                RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        }

    }

    private fun refreshList() {
        shoppingListAdapter.list = activityViewModel.shoppingList


        var money = 0;
        var count = 0;
        for (e in shoppingListAdapter.list) {
            money += e.totalPrice
            count += e.menuCnt
        }
        binding.textShoppingCount.text = "총 " + count.toString() + "잔"
        binding.textShoppingMoney.text = money.toString() + "원"
        shoppingListAdapter.notifyDataSetChanged()
    }


    private fun initEvent() {
//        binding.btnShop.setOnClickListener {
//            binding.btnShop.background =
//                ContextCompat.getDrawable(requireContext(), R.drawable.button_color)
//            binding.btnTakeout.background =
//                ContextCompat.getDrawable(requireContext(), R.drawable.button_non_color)
//            isShop = true
//        }
//        binding.btnTakeout.setOnClickListener {
//            binding.btnTakeout.background =
//                ContextCompat.getDrawable(requireContext(), R.drawable.button_color)
//            binding.btnShop.background =
//                ContextCompat.getDrawable(requireContext(), R.drawable.button_non_color)
//            isShop = false
//        }
        binding.btnOrder.setOnClickListener {
            if (!hasTagged) showDialogForOrderInShop()
            else{
                kakaoPayProgress()
            }

//            else {
//                //거리가 200이상이라면
//                if (true) showDialogForOrderTakeoutOver200m()
//            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mainActivity.hideBottomNav(false)
    }

    private var paymentTid: String = ""
    private var table: String = ""
    private val paymentResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        viewLifecycleOwner.lifecycleScope.launch {
            val tid = paymentTid
            val isPaySuccess = orderViewModel.doPayment(tid)
            if (isPaySuccess) {
                showToast("결제 성공")
                completedOrder(table)
            } else {
                showToast("결제 실패")
            }
        }
    }

    fun kakaoPayProgress(){
        val oid = UUID.randomUUID().toString()
        val uid = ApplicationClass.sharedPreferencesUtil.getUser().id
        val list = activityViewModel.shoppingList
        if (list.size <= 0) {
            showToast("장바구니가 비어 있습니다.")
            return;
        }
        val item_name =
            if (list.size == 1) "${list[0].menuName}" else "${list[0].menuName} 외 ${list.size - 1}개"
        var money = 0;
        var count = 0;
        for (e in shoppingListAdapter.list) {
            money += e.totalPrice
            count += e.menuCnt
        }
        val quantity = count
        val total_amount = money
        val kakaoReadyRequest = KakaoReadyRequest(oid, uid, item_name, quantity, total_amount)
        orderViewModel.requestPayment(kakaoReadyRequest)

        orderViewModel.kakaoPayReadyResponse.observe(viewLifecycleOwner) {
            paymentTid = it.tid
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.next_redirect_mobile_url))
                paymentResultLauncher.launch(intent)
            } catch (e: Exception) {
                // 앱 실행 실패 시 모바일 웹 URL로 대체
                val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it.android_app_scheme))
                paymentResultLauncher.launch(webIntent)
            }
        }
    }

    fun onNfcScanned(data: String) {
        if (!data.contains("Table")) return
        hasTagged = true
        table = data
        ApplicationClass.sharedPreferencesUtil.saveTable(data)
        binding.textRoomNumber.text = "방 번호 : ${data.replace("Table:", "")}"
        dialog?.cancel()
    }


    private fun showDialogForOrderInShop() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setTitle("알림")
        builder.setMessage(
            "방 정보가 없습니다!\n방에 있는 NFC 리더기에 먼저 찍어주세요."
        )

        builder.setCancelable(true)
        builder.setNegativeButton(
            "확인"
        ) { dialog, _ ->
            dialog.cancel()
//            showToast("주문이 취소되었습니다.")
        }
        dialog = builder.create()
        dialog?.show()
    }

    private fun showDialogForOrderTakeoutOver200m() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setTitle("알림")
        builder.setMessage(
            "현재 고객님의 위치가 매장과 200m 이상 떨어져 있습니다.\n정말 주문하시겠습니까?"
        )
        builder.setCancelable(true)
        builder.setPositiveButton("확인") { _, _ ->
            completedOrder("Take Out 주문")
        }
        builder.setNegativeButton("취소") { dialog, _ ->
            dialog.cancel()
            showToast("주문이 취소되었습니다.")
        }
        builder.create().show()
    }


    private fun completedOrder(table: String) {


        val list = activityViewModel.shoppingList
        val userId = ApplicationClass.sharedPreferencesUtil.getUser().id // ← 실제 유저 아이디로 바꿔주세요

        val orderDetails = list.map {
            OrderDetail(productId = it.menuId, quantity = it.menuCnt)
        }

        showToast("주문이 완료되었습니다.")

        val isoFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.KOREA)
        isoFormat.timeZone = TimeZone.getTimeZone("Asia/Seoul")
        val nowIsoStr = isoFormat.format(Date())
        val order = Order(
            id = 0,
            userId = userId,
            orderTable = table,
            orderTime = nowIsoStr,
            completed = "N", // 초기값으로 "false" 지정 (완료 여부)
            details = ArrayList(orderDetails)
        )

        activityViewModel.orderShoppingList(order)

        activityViewModel.clearShoppingList()
        refreshList()
        shoppingListAdapter.notifyDataSetChanged()

    }


    // 재주문할때는 parameter로 넘기기.
    companion object {
        @JvmStatic
        fun newInstance(param: Int) =
            ShoppingListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ORDER_ID, param)
                }
            }
    }
}