package com.ssafy.bookspresso.ui.my

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssafy.bookspresso.R
import com.ssafy.bookspresso.base.ApplicationClass
import com.ssafy.bookspresso.data.model.response.OrderDetailResponse

class OrderDetailListAdapter(val context: Context,var list:List<OrderDetailResponse>) :
    RecyclerView.Adapter<OrderDetailListAdapter.OrderDetailListHolder>(){

    inner class OrderDetailListHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bindInfo(position: Int) {
            val item = list[position]

            var image=itemView.findViewById<ImageView>(R.id.menuImage)
            var menuName=itemView.findViewById<TextView>(R.id.textShoppingMenuName)
            var menuMoney=itemView.findViewById<TextView>(R.id.textShoppingMenuMoney)
            var menuCount=itemView.findViewById<TextView>(R.id.textShoppingMenuCount)
            var menuMoneyAll=itemView.findViewById<TextView>(R.id.textShoppingMenuMoneyAll)

            Glide.with(itemView)
                .load("${ApplicationClass.MENU_IMGS_URL}${item.productImg}")
                .into(image)
            menuName.text=item.productName
            menuMoney.text=item.unitPrice.toString()
            menuCount.text=item.quantity.toString()
            menuMoneyAll.text=item.sumPrice.toString()


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailListHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_order_detail_list, parent, false)
        return OrderDetailListHolder(view)
    }

    override fun onBindViewHolder(holder: OrderDetailListHolder, position: Int) {
        holder.bindInfo(position)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}