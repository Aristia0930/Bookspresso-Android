package com.ssafy.bookspresso.ui.book

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.bookspresso.base.ApplicationClass
import com.ssafy.bookspresso.data.model.dto.Comment
import com.ssafy.bookspresso.databinding.ListItemCommentBinding


class CommentAdapter(var commentList: List<Comment>) :RecyclerView.Adapter<CommentAdapter.CommentHolder>(){

    inner class CommentHolder(private val binding: ListItemCommentBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(comment: Comment){
            binding.etCommentContent.visibility= View.GONE
            binding.textNoticeContent.text = comment.comment
            val isMyComment = ApplicationClass.sharedPreferencesUtil.getUser().id == comment.userId

            if (isMyComment) {
                binding.ivModifyComment.visibility = View.VISIBLE
                binding.ivModifyCancelComment.visibility = View.GONE
                binding.ivDeleteComment.visibility = View.VISIBLE
                binding.ivModifyAcceptComment.visibility = View.GONE
            } else {
                binding.ivModifyComment.visibility = View.GONE
                binding.ivModifyCancelComment.visibility = View.GONE
                binding.ivDeleteComment.visibility = View.GONE
                binding.ivModifyAcceptComment.visibility = View.GONE
            }

            binding.ivDeleteComment.setOnClickListener{
                itemClick.onClick(comment.id,comment.productId)
            }

            //수정버튼
            binding.ivModifyComment.setOnClickListener{
                binding.ivModifyComment.visibility = View.GONE
                binding.ivDeleteComment.visibility = View.GONE
                binding.ivModifyCancelComment.visibility = View.VISIBLE
                binding.ivModifyAcceptComment.visibility = View.VISIBLE
                binding.etCommentContent.visibility= View.VISIBLE
                binding.etCommentContent.setText(comment.comment.toString())
                binding.textNoticeContent.visibility=View.GONE
            }
            //취소버튼
            binding.ivModifyCancelComment.setOnClickListener{
                binding.etCommentContent.visibility= View.GONE
                binding.textNoticeContent.visibility=View.VISIBLE
                if (isMyComment) {
                    binding.ivModifyComment.visibility = View.VISIBLE
                    binding.ivModifyCancelComment.visibility = View.GONE
                    binding.ivDeleteComment.visibility = View.VISIBLE
                    binding.ivModifyAcceptComment.visibility = View.GONE
                } else {
                    binding.ivModifyComment.visibility = View.GONE
                    binding.ivModifyCancelComment.visibility = View.GONE
                    binding.ivDeleteComment.visibility = View.GONE
                    binding.ivModifyAcceptComment.visibility = View.GONE
                }
            }
            //저장
            binding.ivModifyAcceptComment.setOnClickListener{
                itemClick2.onClick(binding.etCommentContent.text.toString(),comment)
                binding.etCommentContent.visibility= View.GONE
                binding.textNoticeContent.visibility=View.VISIBLE
                binding.ivModifyComment.visibility = View.VISIBLE
                binding.ivModifyCancelComment.visibility = View.GONE
                binding.ivDeleteComment.visibility = View.VISIBLE
                binding.ivModifyAcceptComment.visibility = View.GONE


            }



        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentHolder {
        val binding = ListItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentHolder, position: Int) {
        holder.bind(commentList[position])
    }

    override fun getItemCount(): Int {
        return commentList.size
    }

    fun interface ItemClickListener {
        fun onClick(id:Int,productId:Int)
    }

    fun interface ItemClickListener2 {
        fun onClick(text:String,comment: Comment)
    }

    lateinit var itemClick: ItemClickListener
    lateinit var itemClick2: ItemClickListener2
    fun delete(itemClickListener: ItemClickListener) {
        this.itemClick = itemClickListener
    }

    fun update(itemClickListener: ItemClickListener2) {
        this.itemClick2 = itemClickListener
    }


}

