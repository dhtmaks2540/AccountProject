package kr.co.lee.accoutproject.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.recyclerview.widget.RecyclerView
import kr.co.lee.accoutproject.ActivityReceipt
import kr.co.lee.accoutproject.data.AccountAndType
import kr.co.lee.accoutproject.databinding.ItemContentListBinding

class MonthRecyclerViewAdapter(
    private val accountEntityList: List<AccountAndType>
): RecyclerView.Adapter<MonthRecyclerViewAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemContentListBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener {
                binding.accountAndType?.let {
                    val intent = Intent(binding.moneyView.context, ActivityReceipt::class.java)
                    intent.putExtra("accountAndType", it)
                    binding.moneyView.context.startActivity(intent)
                }
            }
        }
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