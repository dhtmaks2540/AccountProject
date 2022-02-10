package kr.co.lee.accoutproject.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.lee.accoutproject.data.AccountAndType
import kr.co.lee.accoutproject.databinding.ItemContentListBinding

class MonthRecyclerViewAdapter(
    val accountEntityList: List<AccountAndType>, val context: Context
): RecyclerView.Adapter<MonthRecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemContentListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindTo(item: AccountAndType) {
            binding.apply {
                binding.accountAndType = item
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemContentListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindTo(accountEntityList[position])
    }

    override fun getItemCount(): Int = accountEntityList.size
}