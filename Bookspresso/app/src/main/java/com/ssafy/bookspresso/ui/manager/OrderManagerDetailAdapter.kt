package com.ssafy.bookspresso.ui.manager

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssafy.bookspresso.R
import com.ssafy.bookspresso.base.ApplicationClass
import com.ssafy.bookspresso.data.model.response.OrderDetailResponse
import com.ssafy.bookspresso.data.model.response.OrderResponse
import com.ssafy.bookspresso.ui.my.OrderDetailListAdapter

class OrderManagerDetailAdapter (var list:List<OrderDetailResponse>) : RecyclerView.Adapter<OrderManagerDetailAdapter.OrderHolder>(){
    inner  class OrderHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name=itemView.findViewById<TextView>(R.id.tv_name)
        var quantity=itemView.findViewById<TextView>(R.id.tv_quantity)
        var price = itemView.findViewById<TextView>(R.id.tv_price)


        fun bindInfo(dto: OrderDetailResponse) {
            name.text=dto.productName
            quantity.text="x "+dto.quantity.toString()
            price.text=(dto.unitPrice*dto.quantity).toString()+"원"



        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHolder {
        val view =LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order_product,parent,false)

        return OrderHolder(view)


    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: OrderHolder, position: Int) {
        holder.bindInfo(list[position])
    }

}