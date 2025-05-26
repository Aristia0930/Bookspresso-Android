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
                "available" -> "대출 가능"
                "borrowed" -> {
                    val diff = due.time - today.time
                    val daysLeft = TimeUnit.MILLISECONDS.toDays(diff)

                    if (daysLeft >= 0) {
                        "대출 중 (반납까지 ${daysLeft}일 남음)"
                    } else {
                        "대출 중 (반납 기한 초과 ${-daysLeft}일)"
                    }
                }
                "reserved" -> "예약됨"
                else -> "상태 불명"
            }
            text_status.text = text

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