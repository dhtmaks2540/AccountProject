package kr.co.lee.accoutproject.binding

import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import de.hdodenhof.circleimageview.CircleImageView
import kr.co.lee.accoutproject.R
import kr.co.lee.accoutproject.model.AccountAndType
import kr.co.lee.accoutproject.ui.main.MainViewModel
import kr.co.lee.accoutproject.utilities.decimalFormat
import org.joda.time.LocalDate
import java.util.*

object ViewBinding {
    // InverseBindingAdapter는 getter에 해당(View -> Data)
    // attribute - BindingAdapter처럼 특성 이름, event = onChange()를 호출하기 위한 event
    @JvmStatic
    @InverseBindingAdapter(attribute = "android:text", event = "textAttrChanged")
    fun bindGetString(view: EditText): String {
        return view.text.toString()
    }

    /**
     * attribute명은 event 이름 그대로
     * 메서드의 파라미터로 InverseBindingListener가 들어옴
     */
    @JvmStatic
    @BindingAdapter("textAttrChanged")
    fun bindSetTextWatcher(view: EditText, textAttrChanged: InverseBindingListener) {
        view.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                textAttrChanged.onChange()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
    }

    @JvmStatic
    @BindingAdapter("android:text")
    fun bindGetTextString(view: EditText, content: String) {
        var old = view.text.toString()
        if(old != content) view.setText(content)
    }

    @JvmStatic
    @BindingAdapter("onItemSelectedListener")
    fun bindSetOnNavigationItemSelectedListener(view: BottomNavigationView, viewModel: MainViewModel) {
        view.setOnItemSelectedListener { menuItem ->
            viewModel.setCurrentPage(menuItem.itemId)
        }
    }

    @JvmStatic
    @BindingAdapter("image_res", "image_color")
    fun bindSetImageUrl(view: CircleImageView, imageName: String, imageColor: String) {
        val resId = view.context.resources.getIdentifier(imageName,
            "drawable", view.context.packageName)
        view.setImageResource(resId)
        // 테두리 지정
        view.borderColor = Color.parseColor(imageColor)
        // 배경색 지정
        view.circleBackgroundColor = Color.parseColor(imageColor)
    }

    @JvmStatic
    @BindingAdapter("money")
    fun bindSetMoney(view: TextView, money: Double) {
        val money = decimalFormat.format(money)
        view.text = money + "원"
    }

    @JvmStatic
    @BindingAdapter("only_money")
    fun bindSetOnlyMoney(view: TextView, money: Double) {
        val money = decimalFormat.format(money)
        view.text = money
    }

    @JvmStatic
    @BindingAdapter("date")
    fun bindSetDate(view: TextView, map: TreeMap<LocalDate, ArrayList<AccountAndType?>>) {
        val start = map.firstKey().toString("MM월 dd일")
        val end = map.lastKey().toString("MM월 dd일")
        if(start != end) {
            view.text = "$start - $end"
        } else {
            view.text = "$start"
        }
    }
    
    @JvmStatic
    @BindingAdapter("money_text")
    fun bindSetMoneyDate(view: TextView, map: TreeMap<LocalDate, ArrayList<AccountAndType>?>) {
        var depositMoney: Long = 0
        for((_, arrayList) in map.entries) {
            arrayList?.let {
                depositMoney += arrayList.filter { it.type.typeForm == 0 }.sumOf { it.account.money }
            }
        }

        view.setTextColor(ResourcesCompat.getColor(view.resources, R.color.money_red, null))
        view.text = "${decimalFormat.format(depositMoney)}원"
    }

}