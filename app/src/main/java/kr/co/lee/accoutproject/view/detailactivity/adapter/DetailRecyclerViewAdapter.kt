package kr.co.lee.accoutproject.view.detailactivity.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.lee.accoutproject.databinding.ItemCategoryBinding
import kr.co.lee.accoutproject.room.TypeEntity

class DetailRecyclerViewAdapter(val typeList: List<TypeEntity>, val context: Context): RecyclerView.Adapter<DetailRecyclerViewAdapter.DetailItemViewHolder>() {

    inner class DetailItemViewHolder(val view: ItemCategoryBinding): RecyclerView.ViewHolder(view.root) {
        fun bindTo(typeEntity: TypeEntity) {
            view.itemCategoryName.text = typeEntity.typeName

            // 이미지 획득
            val resId = context.resources.getIdentifier(typeEntity.typeImageName,
                "drawable", context.packageName)

            view.itemCategoryImage.apply {
                // 이미지 적용
                setImageResource(resId)
            }

            val bgShape = view.itemCategoryImage.background as GradientDrawable
            bgShape.setColor(Color.parseColor(typeEntity.typeColor))

//            view.itemCategoryImage.apply {
//                setImageResource(resId)
//                borderColor = Color.parseColor(typeEntity.typeColor)
//                circleBackgroundColor = Color.parseColor(typeEntity.typeColor)
//            }
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