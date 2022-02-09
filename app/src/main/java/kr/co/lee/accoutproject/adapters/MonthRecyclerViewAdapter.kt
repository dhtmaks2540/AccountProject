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
                moneyView.text = item.account.money.toString()
                contentView.text = item.account.content.toString()
                typeLabel.text = item.type.typeName
                val resId = context.resources.getIdentifier(item.type.typeImageName,
                    "drawable", context.packageName)
                typeImage.setImageResource(resId)
                // 테두리 지정
                typeImage.borderColor = Color.parseColor(item.type.typeColor)
                // 배경색 지정
                typeImage.circleBackgroundColor = Color.parseColor(item.type.typeColor)
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