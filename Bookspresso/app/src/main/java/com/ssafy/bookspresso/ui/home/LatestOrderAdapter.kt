package com.ssafy.bookspresso.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssafy.bookspresso.R
import com.ssafy.bookspresso.base.ApplicationClass
import com.ssafy.bookspresso.data.model.response.OrderResponse
import com.ssafy.bookspresso.util.CommonUtils

class LatestOrderAdapter(var orderList:List<OrderResponse>) :
    RecyclerView.Adapter<LatestOrderAdapter.OrderHolder>(){

    inner class OrderHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val menuImage = itemView.findViewById<ImageView>(R.id.menuImage)
        val orderId = itemView.findViewById<TextView>(R.id.orderId)
        val textMenuNames = itemView.findViewById<TextView>(R.id.textMenuNames)
        val textMenuPrice = itemView.findViewById<TextView>(R.id.textMenuPrice)
        val textMenuDate = itemView.findViewById<TextView>(R.id.textMenuDate)

        fun bindInfo(data : OrderResponse){
            Glide.with(itemView)
                .load("${ApplicationClass.MENU_IMGS_URL}${data.details[0].productImg}")
                .into(menuImage)
            orderId.text = data.orderId.toString()
            if(data.orderCount > 1){
                textMenuNames.text = "${data.details[0].productName} 외 ${data.orderCount -1}건"  //외 x건
            }else{
                textMenuNames.text = data.details[0].productName
            }

            textMenuPrice.text = CommonUtils.makeComma(data.totalPrice)
            textMenuDate.text = CommonUtils.dateformatYMDHM(data.orderDate)

            itemView.setOnClickListener{
                itemClickListner.onClick(it, layoutPosition, data.orderId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_latest_order, parent, false)
        return OrderHolder(view)
    }

    override fun onBindViewHolder(holder: OrderHolder, position: Int) {
        holder.bindInfo(orderList[position])
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    //클릭 인터페이스 정의 사용하는 곳에서 만들어준다.
    interface ItemClickListener {
        fun onClick(view: View, position: Int, orderid:Int)
    }
    //클릭리스너 선언
    private lateinit var itemClickListner: ItemClickListener
    //클릭리스너 등록 매소드
    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListner = itemClickListener
    }
}