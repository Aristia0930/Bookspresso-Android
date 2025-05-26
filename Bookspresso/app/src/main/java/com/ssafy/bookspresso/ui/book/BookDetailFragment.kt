package com.ssafy.bookspresso.ui.book

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.ssafy.bookspresso.R
import com.ssafy.bookspresso.base.ApplicationClass
import com.ssafy.bookspresso.base.BaseFragment
import com.ssafy.bookspresso.data.model.dto.Book
import com.ssafy.bookspresso.data.model.dto.Comment
import com.ssafy.bookspresso.data.model.dto.ShoppingCart
import com.ssafy.bookspresso.data.model.response.ProductWithCommentResponse
import com.ssafy.bookspresso.databinding.FragmentBookDetailBinding
import com.ssafy.bookspresso.ui.MainActivity
import com.ssafy.bookspresso.ui.MainActivityViewModel
import com.ssafy.bookspresso.util.CommonUtils

//메뉴 상세 화면 . Order탭 - 특정 메뉴 선택시 열림
private const val TAG = "BookDetailFragment_싸피"

class BookDetailFragment : BaseFragment<FragmentBookDetailBinding>(
    FragmentBookDetailBinding::bind,
    R.layout.fragment_book_detail
) {
    private lateinit var mainActivity: MainActivity
//    private var commentAdapter = CommentAdapter(emptyList())

    private val activityViewModel: MainActivityViewModel by activityViewModels()
    private val viewModel: BookDetailFragmentViewModel by viewModels()
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.isbn = activityViewModel.isbn.value!!
        mainActivity.hideBottomNav(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerObserver()
        viewModel.getBookInfo()

        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun registerObserver() {
        viewModel.bookInfo.observe(viewLifecycleOwner) {
            Log.d(TAG, "registerObserver: $it")

            // 화면 정보 갱신
            setScreen(it)
        }
    }

    // 초기 화면 설정
    private fun setScreen(book: Book) {
        Log.d(TAG, "setScreen: $book")
        Glide.with(this)
            .load("${ApplicationClass.BOOK_IMGS_URL}${book.img}")
            .into(binding.imageBookCover)

        binding.textTitle.text = book.title
        binding.textAuthor.text = book.author
        binding.textIsbn.text = book.isbn
        binding.textStatus.text = formatStatus(book.status)
        binding.textSummary.text = book.summary

    }

    private fun formatStatus(status: String): String {
//        available, borrowed, reserved
        var res: String = ""
        if (status == "available") {
            res = "대출 가능"
        } else if (status == "borrowed") {
            res = "대출 중"
            binding.textStatus.setTextColor(Color.RED)
        } else if (status == "reserved") {
            res = "예약됨"
        }
        return res
    }


    override fun onDestroy() {
        super.onDestroy()
        mainActivity.hideBottomNav(false)
    }

}