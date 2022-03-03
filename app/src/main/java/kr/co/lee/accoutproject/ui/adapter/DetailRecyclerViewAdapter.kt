package kr.co.lee.accoutproject.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.lee.accoutproject.model.TypeEntity
import kr.co.lee.accoutproject.databinding.ItemTypeBinding

class DetailRecyclerViewAdapter(private val typeList: List<TypeEntity>, val context: Context)
    : RecyclerView.Adapter<DetailRecyclerViewAdapter.DetailItemViewHolder>() {
    private val entityClickListener = context as OnEntityClickListener

    inner class DetailItemViewHolder(val binding: ItemTypeBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            // 아이템 클릭 리스너
            binding.setClickListener {
                binding.typeEntity?.let {
                    // 클릭 시 데이터 넘기기
                    entityClickListener.onEntityClick(it)
                }
            }
        }

        fun bindTo(item: TypeEntity) {
            binding.apply {
                typeEntity = item
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailItemViewHolder {
        val binding = ItemTypeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailItemViewHolder, position: Int) {
        holder.bindTo(typeList[position])
    }

    // 아이템의 개수
    override fun getItemCount(): Int = typeList.size
}

// Callback Interface
interface OnEntityClickListener {
    fun onEntityClick(entity: TypeEntity)
}