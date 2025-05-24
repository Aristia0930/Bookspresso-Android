package com.ssafy.bookspresso.ui.book

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.distinctUntilChanged
import androidx.recyclerview.widget.GridLayoutManager
import com.ssafy.bookspresso.R
import com.ssafy.bookspresso.base.BaseFragment
import com.ssafy.bookspresso.databinding.FragmentBookBinding
import com.ssafy.bookspresso.ui.MainActivity
import com.ssafy.bookspresso.ui.MainActivityViewModel

// 하단 주문 탭
private const val TAG = "OrderFragment_싸피"
class BookFragment : BaseFragment<FragmentBookBinding>(FragmentBookBinding::bind, R.layout.fragment_book){
    private lateinit var bookAdapter: BookAdapter
    private lateinit var mainActivity: MainActivity

    private val activityViewModel: MainActivityViewModel by activityViewModels()
    private val orderViewModel: BookViewModel by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bookAdapter = BookAdapter(arrayListOf()) { bookId ->
            activityViewModel.setBookId(bookId)
            mainActivity.openFragment(4)
        }

        binding.recyclerViewBook.apply {
            layoutManager = GridLayoutManager(context,3)
            adapter = bookAdapter
        }

        initData()
        initEvent()
    }

    private fun initEvent(){
        binding.floatingBtn.setOnClickListener{
            //장바구니 이동
            mainActivity.openFragment(1)
        }

    }

    private fun initData(){
        // 책목록 조회.

        orderViewModel.bookList.distinctUntilChanged().observe(viewLifecycleOwner) {
            bookAdapter.bookList = it
            bookAdapter.notifyDataSetChanged()
        }
        orderViewModel.getProductList()


    }
}