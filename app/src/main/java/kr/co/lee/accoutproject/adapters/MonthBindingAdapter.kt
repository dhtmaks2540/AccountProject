package kr.co.lee.accoutproject.adapters

import android.graphics.Color
import android.widget.TextView
import androidx.databinding.BindingAdapter
import de.hdodenhof.circleimageview.CircleImageView
import kr.co.lee.accoutproject.utilities.decimalFormat

@BindingAdapter("image_res", "image_color")
fun setImageUrl(view: CircleImageView, imageName: String, imageColor: String) {
    val resId = view.context.resources.getIdentifier(imageName,
        "drawable", view.context.packageName)
    view.setImageResource(resId)
    // 테두리 지정
    view.borderColor = Color.parseColor(imageColor)
    // 배경색 지정
    view.circleBackgroundColor = Color.parseColor(imageColor)
}

@BindingAdapter("money")
fun setMoney(view: TextView, money: Double) {
    val money = decimalFormat.format(money)
    view.text = money + "원"
}

@BindingAdapter("only_money")
fun setOnlyMoney(view: TextView, money: Double) {
    val money = decimalFormat.format(money)
    view.text = money
}
