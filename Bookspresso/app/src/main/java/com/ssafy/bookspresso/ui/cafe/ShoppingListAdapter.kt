package com.ssafy.bookspresso.ui.cafe

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssafy.bookspresso.base.ApplicationClass
import com.ssafy.bookspresso.databinding.ListItemShoppingListBinding
import com.ssafy.bookspresso.data.model.dto.ShoppingCart


class ShoppingListAdapter(
    var list: MutableList<ShoppingCart>,
    private val onDeleteClick: (ShoppingCart) -> Unit
) :
    RecyclerView.Adapter<ShoppingListAdapter.ShoppingListHolder>() {

    inner class ShoppingListHolder(private val binding: ListItemShoppingListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindInfo(position: Int) {
            val item = list[position]

            Glide.with(itemView)
                .load("${ApplicationClass.MENU_IMGS_URL}${item.menuImg}")
                .into(binding.menuImage)
            binding.textShoppingMenuName.text = item.menuName
            binding.textShoppingMenuCount.text = "${item.menuCnt} 잔"
            binding.textShoppingMenuMoney.text = "${item.menuPrice} 원"
            binding.textShoppingMenuMoneyAll.text = "${item.totalPrice} 원"

            // 삭제 버튼 클릭 처리
            binding.btnDelete.setOnClickListener {
                onDeleteClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingListHolder {
        return ShoppingListHolder(
            ListItemShoppingListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ShoppingListHolder, position: Int) {
        holder.bindInfo(position)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}