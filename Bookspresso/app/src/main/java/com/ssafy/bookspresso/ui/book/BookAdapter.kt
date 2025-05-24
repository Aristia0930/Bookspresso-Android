package com.ssafy.bookspresso.ui.book

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssafy.bookspresso.R
import com.ssafy.bookspresso.base.ApplicationClass
import com.ssafy.bookspresso.data.model.dto.Book

private const val TAG = "MenuAdapter_싸피"
class BookAdapter(var bookList:List<Book>, val itemClickListener:ItemClickListener) :RecyclerView.Adapter<BookAdapter.BookHolder>(){

    inner class BookHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textBookTitle = itemView.findViewById<TextView>(R.id.textBookTitle)
        val textBookAuthor = itemView.findViewById<TextView>(R.id.textBookAuthor)
        val bookCoverImage = itemView.findViewById<ImageView>(R.id.bookCoverImage)

        fun bindInfo(book : Book){
            textBookTitle.text = book.title
            textBookAuthor.text = book.author
            Glide.with(itemView)
                .load("${ApplicationClass.BOOK_IMGS_URL}${book.img}")
                .into(bookCoverImage)

            itemView.setOnClickListener{
                itemClickListener.onClick(bookList[layoutPosition].isbn)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_book, parent, false)
        return BookHolder(view)
    }

    override fun onBindViewHolder(holder: BookHolder, position: Int) {
        holder.apply{
            bindInfo(bookList[position])
        }
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    //클릭 인터페이스 정의 사용하는 곳에서 만들어준다.
    fun interface ItemClickListener {
        fun onClick(isbn:String)
    }

}

