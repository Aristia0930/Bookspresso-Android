package com.ssafy.bookspresso.ui.book

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.RatingBar
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.ssafy.bookspresso.R
import com.ssafy.bookspresso.base.ApplicationClass
import com.ssafy.bookspresso.base.BaseFragment
import com.ssafy.bookspresso.data.model.dto.Comment
import com.ssafy.bookspresso.data.model.dto.ShoppingCart
import com.ssafy.bookspresso.data.model.response.ProductWithCommentResponse
import com.ssafy.bookspresso.databinding.FragmentMenuDetailBinding
import com.ssafy.bookspresso.ui.MainActivity
import com.ssafy.bookspresso.ui.MainActivityViewModel
import com.ssafy.bookspresso.util.CommonUtils
import java.text.DecimalFormat

//메뉴 상세 화면 . Order탭 - 특정 메뉴 선택시 열림
private const val TAG = "MenuDetailFragment_싸피"
class MenuDetailFragment : BaseFragment<FragmentMenuDetailBinding>(FragmentMenuDetailBinding::bind, R.layout.fragment_menu_detail){
    private lateinit var mainActivity: MainActivity
    private var commentAdapter = CommentAdapter(emptyList())

    private val activityViewModel: MainActivityViewModel by activityViewModels()
    private val viewModel: MenuDetailFragmentViewModel by viewModels()
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.productId = activityViewModel.productId.value!!
        mainActivity.hideBottomNav(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerObserver()
        viewModel.getProductInfo()

        initListener()

        binding.recyclerViewMenuDetail.apply {
            adapter = commentAdapter
        }

        commentAdapter.delete(object :CommentAdapter.ItemClickListener{
            override fun onClick(id: Int, productId: Int) {
                viewModel.deleteComment(id,productId)
            }

        })
        commentAdapter.update(object :CommentAdapter.ItemClickListener2{

            override fun onClick(text: String, comment: Comment) {
                val updatedComment = comment.copy(comment = text)
                viewModel.updateComment(updatedComment)
            }

        })

        binding.btnCreateComment.setOnClickListener {
            val comment=binding.etCreateComment.text.toString()
            val userId=ApplicationClass.sharedPreferencesUtil.getUser().id
            val productId = viewModel.productId


            // 다이얼로그 레이아웃 인플레이트
            val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_menu_comment, null)

            val ratingBar = dialogView.findViewById<RatingBar>(R.id.ratingBarMenuDialogComment)

            AlertDialog.Builder(requireContext())
                .setTitle("리뷰 작성")
                .setView(dialogView)
                .setPositiveButton("확인") { dialog, _ ->
                    val rating = ratingBar.rating

                    val comment=Comment(userId,productId,rating, comment )
                    viewModel.insertComment(comment)
//                    Toast.makeText(requireContext(), "선택한 별점: $rating", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
                .setNegativeButton("취소") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
                .show()
        }

    }

    private fun registerObserver(){
        viewModel.productInfo.observe(viewLifecycleOwner) {
            Log.d(TAG, "registerObserver: $it")

            commentAdapter.commentList = it.comments
            commentAdapter.notifyDataSetChanged()

            // 화면 정보 갱신
            setScreen(it)
        }
    }

    // 초기 화면 설정
    private fun setScreen(response: ProductWithCommentResponse){
        Log.d(TAG, "setScreen: $response")
        Glide.with(this)
            .load("${ApplicationClass.MENU_IMGS_URL}${response.productImg}")
            .into(binding.menuImage)

        binding.txtMenuName.text = response.productName
        binding.txtMenuPrice.text = "${CommonUtils.makeComma(response.productPrice)}"
        binding.tvAverage.text = "${DecimalFormat("#.#").format(response.productRatingAvg)} 점"
        binding.ratingBar.rating = response.productRatingAvg.toFloat()
    }


    private var count = 1

    private fun initListener(){
        binding.btnAddCount.setOnClickListener {
            count++
            binding.textMenuCount.text = count.toString()
        }

        binding.btnMinusCount.setOnClickListener {
            if (count > 1) count-- else count=1
            binding.textMenuCount.text = count.toString()
        }

        binding.btnAddList.setOnClickListener {
            viewModel.productInfo.value?.let { item ->
                ShoppingCart(
                    menuId = viewModel.productId,
                    menuImg = item.productImg,
                    menuName = item.productName,
                    menuCnt = count,
                    menuPrice = item.productPrice,
                    type = item.type
                )
            }?.apply {
                activityViewModel.addShoppingList(this)
                showToast("상품이 장바구니에 담겼습니다.")
            }
        }


        binding.btnCreateComment.setOnClickListener {
            showDialogRatingStar()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        mainActivity.hideBottomNav(false)
    }

    private fun showDialogRatingStar() {



    }

}