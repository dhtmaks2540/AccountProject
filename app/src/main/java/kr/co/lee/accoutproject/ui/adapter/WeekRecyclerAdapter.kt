package kr.co.lee.accoutproject.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.lee.accoutproject.model.AccountAndType
import kr.co.lee.accoutproject.databinding.ItemWeekBinding
import org.joda.time.LocalDate
import java.util.*

// WeekFragment RecyclerViewAdapter
class WeekRecyclerAdapter: RecyclerView.Adapter<WeekRecyclerAdapter.ViewHolder>() {

    private var weekArray: Array<TreeMap<LocalDate, ArrayList<AccountAndType>?>> = arrayOf()

    class ViewHolder(val binding: ItemWeekBinding): RecyclerView.ViewHolder(binding.root) {
        // 아이템 초기화
        fun bindTo(item: TreeMap<LocalDate, ArrayList<AccountAndType>?>) {
            binding.apply {
                accountAndType = item
                weekPosition = adapterPosition
                executePendingBindings()
            }
        }
    }

    // 아이템 추가 메소드
    fun add(array: Array<TreeMap<LocalDate, ArrayList<AccountAndType>?>>) {
        weekArray = array
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemWeekBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindTo(weekArray[position])
    }

    override fun getItemCount(): Int = weekArray.size
}