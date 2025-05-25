package com.ssafy.bookspresso.ui.my

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssafy.bookspresso.R
import com.ssafy.bookspresso.base.ApplicationClass
import com.ssafy.bookspresso.data.model.dto.BookRental
import com.ssafy.bookspresso.data.model.response.OrderResponse
import com.ssafy.bookspresso.util.CommonUtils

class BookListAdapter(var list:List<BookRental>) :
    RecyclerView.Adapter<BookListAdapter.BookHolder>(){

    inner class BookHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val bookCoverImage = itemView.findViewById<ImageView>(R.id.bookCoverImage)
        val text_title = itemView.findViewById<TextView>(R.id.text_title)
        val text_author = itemView.findViewById<TextView>(R.id.text_author)
        val text_status = itemView.findViewById<TextView>(R.id.text_status)

        fun bindInfo(data: BookRental){


//            Glide.with(itemView)
//                .load("${ApplicationClass.BOOK_IMGS_URL}${data.i}")
//                .into(menuImage)
//
//            if(data.orderCount > 1){
//                textMenuNames.text = "${data.details[0].productName} 외 ${data.orderCount -1}건"  //외 x건
//            }else{
//                textMenuNames.text = data.details[0].productName
//            }
//
//            textMenuPrice.text = CommonUtils.makeComma(data.totalPrice)
//            textMenuDate.text = CommonUtils.dateformatYMDHM(data.orderDate)
//            textCompleted.text = CommonUtils.isOrderCompleted(data)
//            //클릭연결
//            itemView.setOnClickListener{
//                Log.d(TAG, "bindInfo: ${data.orderId}")
//                itemClickListner.onClick(it, layoutPosition, data.orderId)
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_rental_book, parent, false)
        return BookHolder(view)
    }

    override fun onBindViewHolder(holder: BookHolder, position: Int) {
        holder.bindInfo(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
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