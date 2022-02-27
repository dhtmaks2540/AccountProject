package kr.co.lee.accoutproject.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.lee.accoutproject.data.AccountAndType
import kr.co.lee.accoutproject.databinding.ItemWeekListBinding

class WeekRecyclerAdapter(
    private val weekList: Array<ArrayList<AccountAndType>>
): RecyclerView.Adapter<WeekRecyclerAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemWeekListBinding): RecyclerView.ViewHolder(binding.root) {
        init {

        }

        fun bindTo(item: ArrayList<AccountAndType>) {
            binding.apply {

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemWeekListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindTo(weekList[position])
    }

    override fun getItemCount(): Int = weekList.size
}