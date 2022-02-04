package kr.co.lee.accoutproject.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.lee.accoutproject.databinding.ItemCategoryBinding
import kr.co.lee.accoutproject.data.TypeEntity

class DetailRecyclerViewAdapter(val typeList: List<TypeEntity>, val context: Context)
    : RecyclerView.Adapter<DetailRecyclerViewAdapter.DetailItemViewHolder>() {
    private val entityClickListener = context as OnEntityClickListener

    inner class DetailItemViewHolder(val binding: ItemCategoryBinding): RecyclerView.ViewHolder(binding.root) {
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
                itemCategoryName.text = item.typeName
                typeEntity = item
            }

            // 이미지 획득
            val resId = context.resources.getIdentifier(item.typeImageName,
                "drawable", context.packageName)

            // CircleImageView 라이브러리 사용
            binding.itemCategoryImage.apply {
                // 이미지 적용
                setImageResource(resId)
                // 테두리 지정
                borderColor = Color.parseColor(item.typeColor)
                // 배경색 지정
                circleBackgroundColor = Color.parseColor(item.typeColor)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailItemViewHolder {
        val databinding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailItemViewHolder(databinding)
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