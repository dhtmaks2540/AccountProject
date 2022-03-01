package kr.co.lee.accoutproject.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.lee.accoutproject.data.AccountAndType
import kr.co.lee.accoutproject.databinding.ItemWeekListBinding
import org.joda.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class WeekRecyclerAdapter: RecyclerView.Adapter<WeekRecyclerAdapter.ViewHolder>() {

    private var weekArray: Array<TreeMap<LocalDate, ArrayList<AccountAndType>?>> = arrayOf()

    class ViewHolder(val binding: ItemWeekListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindTo(item: TreeMap<LocalDate, ArrayList<AccountAndType>?>) {
            binding.apply {
                accountAndType = item
                weekPosition = adapterPosition
                executePendingBindings()
            }
        }
    }

    fun add(array: Array<TreeMap<LocalDate, ArrayList<AccountAndType>?>>) {
        weekArray = array
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemWeekListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindTo(weekArray[position])
    }

    override fun getItemCount(): Int = weekArray.size
}