package com.ssafy.bookspresso.ui.book

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.ssafy.bookspresso.R
import com.ssafy.bookspresso.base.ApplicationClass
import com.ssafy.bookspresso.base.BaseFragment
import com.ssafy.bookspresso.data.model.dto.Book
import com.ssafy.bookspresso.databinding.FragmentBookBinding
import com.ssafy.bookspresso.databinding.FragmentNfcBinding
import com.ssafy.bookspresso.ui.MainActivity
import com.ssafy.bookspresso.ui.MainActivityViewModel
import com.ssafy.bookspresso.util.CommonUtils.observeOnce
import kotlinx.coroutines.launch

private const val TAG = "NfcFragment_싸피"

class NfcFragment : BaseFragment<FragmentNfcBinding>(
    FragmentNfcBinding::bind,
    R.layout.fragment_nfc
) {
    private lateinit var mainActivity: MainActivity
    private val viewModel: BookViewModel by viewModels()
    private var selectedMode: String = "rent"

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity.hideBottomNav(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEvent()
    }

    private fun initEvent() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                selectedMode = when (tab?.position) {
                    0 -> "rent"
                    1 -> "return"
                    else -> "rent"
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun registerObserver() {
    }

    fun onNfcScanned(data: String) {
        Log.d(TAG, "onNfcScanned: $selectedMode")
        viewLifecycleOwner.lifecycleScope.launch {
            val book = viewModel.getBookInfo(data)
            Log.d(TAG, "onNfcScanned: 책 조회 $book")
            showDialogBook(book)
        }
    }

    private fun showDialogBook(book: Book) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_book, null)
        // 뷰에서 요소 찾기
        val bookCover = dialogView.findViewById<ImageView>(R.id.image_book_cover)
        val bookTitle = dialogView.findViewById<TextView>(R.id.bookTitle)
        val bookAuthor = dialogView.findViewById<TextView>(R.id.bookAuthor)
        val bookIsbn = dialogView.findViewById<TextView>(R.id.bookIsbn)
        val bookSummary = dialogView.findViewById<TextView>(R.id.bookSummary)
        val bookStatus = dialogView.findViewById<TextView>(R.id.bookStatus)

        // 데이터 바인딩
        bookTitle.text = book.title
        bookAuthor.text = "저자: ${book.author}"
        bookIsbn.text = "ISBN: ${book.isbn}"
        bookSummary.text = book.summary
        bookStatus.text = when (book.status) {
            "available" -> "대출 가능"
            "borrowed" -> "대출 중"
            "reserved" -> "예약됨"
            else -> "상태 미상"
        }

        val statusColor = when (book.status) {
            "available" -> Color.GREEN
            "borrowed" -> Color.RED
            "reserved" -> Color.RED
            else -> ContextCompat.getColor(requireContext(), android.R.color.darker_gray)
        }

        bookStatus.setTextColor(statusColor)


        // 이미지 로딩 (예: Glide 사용 시)
        Glide.with(this)
            .load("${ApplicationClass.BOOK_IMGS_URL}${book.img}")
            .into(bookCover)

        // 버튼 텍스트 및 다이얼로그 생성
        val btnText = if (selectedMode == "rent") "대출하기" else "반납하기"

        AlertDialog.Builder(requireContext())
            .setTitle("책 정보")
            .setView(dialogView)
            .setPositiveButton(btnText) { dialog, _ ->
                if (selectedMode == "rent") {
                    when (book.status) {
                        "available" -> {
                            viewModel.rentalBook(book.isbn)
                            showToast("${book.title} 대여 완료!")
                        }
                        "borrowed" -> showToast("이미 대출 중인 도서입니다.")
                        "reserved" -> showToast("예약된 도서입니다.")
                    }
                } else {
                    when (book.status) {
                        "available" -> showToast("반납 대상이 아닙니다!")
                        "borrowed" -> {
                            viewModel.returnBook(book.isbn)
                            showToast("${book.title} 반납 완료!")
                        }
                    }
                }
            }
            .setNegativeButton("취소") { dialog, _ -> dialog.cancel() }
            .create()
            .show()
    }


    override fun onDestroy() {
        super.onDestroy()
        mainActivity.hideBottomNav(false)
    }

}