package kr.co.lee.accoutproject.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.lee.accoutproject.databinding.ItemCategoryBinding
import kr.co.lee.accoutproject.data.TypeEntity

class DetailRecyclerViewAdapter(val typeList: List<TypeEntity>, val context: Context): RecyclerView.Adapter<DetailRecyclerViewAdapter.DetailItemViewHolder>() {

    inner class DetailItemViewHolder(val binding: ItemCategoryBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener {
                binding.itemCategoryImage?.let { categoty ->
                    println()
                }
            }
        }

        fun bindTo(typeEntity: TypeEntity) {
            binding.itemCategoryName.text = typeEntity.typeName

            // 이미지 획득
            val resId = context.resources.getIdentifier(typeEntity.typeImageName,
                "drawable", context.packageName)

            // 라이브러리 사용 X
//            binding.itemCategoryImage.apply {
//                // 이미지 적용
//                setImageResource(resId)
//            }
//
//            val bgShape = binding.itemCategoryImage.background as GradientDrawable
//            bgShape.setColor(Color.parseColor(typeEntity.typeColor))

            // CircleImageView 라이브러리 사용
            binding.itemCategoryImage.apply {
                // 이미지 적용
                setImageResource(resId)
                // 테두리 지정
                borderColor = Color.parseColor(typeEntity.typeColor)
                // 배경색 지정
                circleBackgroundColor = Color.parseColor(typeEntity.typeColor)
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