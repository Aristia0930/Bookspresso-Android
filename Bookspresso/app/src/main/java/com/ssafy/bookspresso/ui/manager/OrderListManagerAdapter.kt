package com.ssafy.bookspresso.ui.manager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.bookspresso.R
import com.ssafy.bookspresso.data.model.dto.Order
import com.ssafy.bookspresso.data.model.response.OrderResponse

private const val TAG = "OrderListAdapter_싸피"
class OrderListManagerAdapter(var list:List<OrderResponse>) : RecyclerView.Adapter<OrderListManagerAdapter.OrderHolder>(){
    inner  class OrderHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var orderNumber=itemView.findViewById<TextView>(R.id.tv_order_number)
        var orderTime=itemView.findViewById<TextView>(R.id.tv_order_time)
        var orderTable=itemView.findViewById<TextView>(R.id.tv_table_name)
        var rejectButton=itemView.findViewById<AppCompatButton>(R.id.btn_reject)
        var acceptButton=itemView.findViewById<AppCompatButton>(R.id.btn_accept)
        var totalPrice=itemView.findViewById<TextView>(R.id.tv_total_price)
        var userId=itemView.findViewById<TextView>(R.id.tv_user_id)

        fun bindInfo(orderResponse: OrderResponse) {

                orderNumber.text = "No " + orderResponse.orderId.toString()
                orderTime.text = orderResponse.orderDate.toString()
                orderTable.text = orderResponse.orderTable.toString()
                userId.text = orderResponse.userId + " 님이 주문하셨습니다."

                //전체 가격 계산하기
                totalPrice.text = orderResponse.totalPrice.toString() + "원"

                //여기서 하위 리스트 뷰 설정하자
                val detailAdapter = OrderManagerDetailAdapter(orderResponse.details)
                val recyclerView = itemView.findViewById<RecyclerView>(R.id.recycler_order_items)
                recyclerView.adapter = detailAdapter
                recyclerView.layoutManager = LinearLayoutManager(itemView.context)
                detailAdapter.list = orderResponse.details
                detailAdapter.notifyDataSetChanged()


                //수락 거절 버튼 처리하기
                rejectButton.setOnClickListener {
                    itemClickListner.onClick(it, layoutPosition, orderResponse.orderId)
                }

                acceptButton.setOnClickListener {
                    itemClickListner2.onClick(it, layoutPosition, orderResponse.orderId)
                }



        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHolder {
        val view =LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order,parent,false)

        return OrderHolder(view)


    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: OrderHolder, position: Int) {
        holder.bindInfo(list[position])
    }

    //클릭 인터페이스 정의 사용하는 곳에서 만들어준다.
    interface ItemClickListener {
        fun onClick(view: View, position: Int, orderid:Int)
    }
    //클릭리스너 선언
    private lateinit var itemClickListner: ItemClickListener

    //클릭리스너 선언
    private lateinit var itemClickListner2: ItemClickListener
    //클릭리스너 등록 매소드
    fun setItemClickListener1(itemClickListener: ItemClickListener) {
        this.itemClickListner = itemClickListener
    }

    fun setItemClickListener2(itemClickListener: ItemClickListener) {
        this.itemClickListner2 = itemClickListener
    }

    fun updateList(newList: List<OrderResponse>) {
        list = newList
        notifyDataSetChanged()
    }



}


