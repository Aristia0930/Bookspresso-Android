package com.ssafy.bookspresso.ui.cafe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssafy.bookspresso.R
import com.ssafy.bookspresso.base.ApplicationClass
import com.ssafy.bookspresso.data.model.dto.Product
import com.ssafy.bookspresso.util.CommonUtils

private const val TAG = "MenuAdapter_싸피"
class MenuAdapter(var productList:List<Product>, val itemClickListener:ItemClickListener) :RecyclerView.Adapter<MenuAdapter.MenuHolder>(){

    inner class MenuHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val item_name = itemView.findViewById<TextView>(R.id.item_name)
        val item_image = itemView.findViewById<ImageView>(R.id.item_image)
        val item_price = itemView.findViewById<TextView>(R.id.item_price)

        fun bindInfo(product : Product){
            item_name.text = product.name
            item_price.text = CommonUtils.makeComma(product.price)
            Glide.with(itemView)
                .load("${ApplicationClass.MENU_IMGS_URL}${product.img}")
                .into(item_image)

            itemView.setOnClickListener{
                itemClickListener.onClick(productList[layoutPosition].id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_cafe, parent, false)
        return MenuHolder(view)
    }

    override fun onBindViewHolder(holder: MenuHolder, position: Int) {
        holder.apply{
            bindInfo(productList[position])
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    //클릭 인터페이스 정의 사용하는 곳에서 만들어준다.
    fun interface ItemClickListener {
        fun onClick(productId:Int)
    }

}

