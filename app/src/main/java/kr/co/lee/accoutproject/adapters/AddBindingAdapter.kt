package kr.co.lee.accoutproject.adapters

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener

// InverseBindingAdapter는 getter에 해당(View -> Data)
// attribute - BindingAdapter처럼 특성 이름, event = onChange()를 호출하기 위한 event
@InverseBindingAdapter(attribute = "android:text", event = "textAttrChanged")
fun getString(view: EditText): String {
    return view.text.toString()
}

/**
 * attribute명은 event 이름 그대로
 * 메서드의 파라미터로 InverseBindingListener가 들어옴
 */
@BindingAdapter("textAttrChanged")
fun setTextWatcher(view: EditText, textAttrChanged: InverseBindingListener) {
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

@BindingAdapter("android:text")
fun getTextString(view: EditText, content: String) {
    var old = view.text.toString()
    if(old != content) view.setText(content)
}