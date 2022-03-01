package kr.co.lee.accoutproject.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.recyclerview.widget.RecyclerView
import kr.co.lee.accoutproject.ActivityReceipt
import kr.co.lee.accoutproject.MainActivity
import kr.co.lee.accoutproject.data.AccountAndType
import kr.co.lee.accoutproject.databinding.ItemContentListBinding

class MonthRecyclerAdapter(
    private val launcher: ActivityResultLauncher<Intent>
): RecyclerView.Adapter<MonthRecyclerAdapter.ViewHolder>() {

    var accountList: ArrayList<AccountAndType>? = ArrayList()

    inner class ViewHolder(val binding: ItemContentListBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener {
                // Activity For Result 어떻게 할지
                binding.accountAndType?.let {
                    Intent(binding.moneyView.context, ActivityReceipt::class.java).apply {
                        putExtra("accountAndType", it)
                        launcher.launch(this)
                    }
                }
            }
        }

        fun bindTo(item: AccountAndType?) {
            binding.apply {
                accountAndType = item
                executePendingBindings()
            }
        }
    }

    fun add(list: ArrayList<AccountAndType>?) {
        accountList = list
        notifyDataSetChanged()
    }

    // Layout 초기화
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemContentListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    // 아이템 하나의 뷰 초기화
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindTo(accountList?.get(position))
    }

    // 아이템 개수
    override fun getItemCount(): Int = accountList?.size ?: 0
}