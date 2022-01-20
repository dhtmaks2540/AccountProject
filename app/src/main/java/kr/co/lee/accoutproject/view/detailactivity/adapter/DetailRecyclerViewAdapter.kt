package kr.co.lee.accoutproject.view.detailactivity.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.lee.accoutproject.databinding.ItemContentListBinding
import kr.co.lee.accoutproject.room.TypeEntity

class DetailRecyclerViewAdapter(val typeList: List<TypeEntity>): RecyclerView.Adapter<DetailRecyclerViewAdapter.DetailItemViewHolder>() {

    class DetailItemViewHolder(view: ItemContentListBinding): RecyclerView.ViewHolder(view.root) {
        fun bindTo() {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailItemViewHolder {
        val databinding = ItemContentListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailItemViewHolder(databinding)
    }

    override fun onBindViewHolder(holder: DetailItemViewHolder, position: Int) {
    }

    override fun getItemCount(): Int = typeList.size
}