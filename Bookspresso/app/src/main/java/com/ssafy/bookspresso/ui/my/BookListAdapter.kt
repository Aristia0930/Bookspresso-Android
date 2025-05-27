package com.ssafy.bookspresso.ui.my

import android.graphics.Color
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
import com.ssafy.bookspresso.data.model.dto.BookRentalInfo
import com.ssafy.bookspresso.data.model.response.OrderResponse
import com.ssafy.bookspresso.util.CommonUtils
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

class BookListAdapter(var list:List<BookRentalInfo>) :
    RecyclerView.Adapter<BookListAdapter.BookHolder>(){

    inner class BookHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val bookCoverImage = itemView.findViewById<ImageView>(R.id.bookCoverImage)
        val text_title = itemView.findViewById<TextView>(R.id.text_title)
        val text_author = itemView.findViewById<TextView>(R.id.text_author)
        val text_status = itemView.findViewById<TextView>(R.id.text_status)

        fun bindInfo(data: BookRentalInfo){


            Glide.with(itemView)
                .load("${ApplicationClass.BOOK_IMGS_URL}${data.book.img}")
                .into(bookCoverImage)

            text_title.text = data.book.title
            text_author.text = data.book.author
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val today = Date()
            val due = dateFormat.parse(data.dueDate)

            val text = when (data.status) {
                "returned" -> "반납 완료"  // 반납된 상태이므로 대출 가능
                "rented" -> {
                    val diff = due.time - today.time
                    val daysLeft = TimeUnit.MILLISECONDS.toDays(diff)
                    "대출 중 (D-${daysLeft})"
                }
                "overdue" -> {
                    val diff = today.time - due.time  // 초과된 일수 계산
                    val daysOverdue = TimeUnit.MILLISECONDS.toDays(diff)
                    "연체됨 (D+${daysOverdue})"
                }
                "overdueReturned" -> {
                    val fee = data.fee
                    "연체료 (${fee}￦)"
                }
                else -> "상태 불명"
            }
            text_status.text = text

            when (data.status) {
                "returned" -> text_status.setTextColor(Color.parseColor("#4CAF50")) // 초록색
                "rented" -> text_status.setTextColor(Color.parseColor("#2196F3")) // 파란색
                "overdue" -> text_status.setTextColor(Color.parseColor("#F44336")) // 빨간색
                "overdueReturned" -> text_status.setTextColor(Color.parseColor("#FFA500"))// 주황
                else -> text_status.setTextColor(Color.parseColor("#757575")) // 회색
            }

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