package kr.co.lee.accoutproject.view.addactivity.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.Menu
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import kr.co.lee.accoutproject.R
import kr.co.lee.accoutproject.databinding.ActivityAddBinding
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

class AddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddBinding
    private val decimalFormat = DecimalFormat("#,###")
    private var result: String = ""


    private val watcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if(!TextUtils.isEmpty(charSequence.toString()) && charSequence.toString() != result){
                result = decimalFormat.format(charSequence.toString().replace(",","").toDouble())
                binding.krwEditView.setText(result);
                binding.krwEditView.setSelection(result.length);
            }
        }

        override fun afterTextChanged(p0: Editable?) {
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add)
        setContentView(binding.root)

        setSupportActionBar(binding.addToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        editTextSetting()
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.add_toolbar_menu, menu)
//        return true
//    }

    private fun editTextSetting() {
        val krwText = Currency.getInstance(Locale.KOREA).symbol;
        binding.krwLabel.text = krwText

        binding.krwEditView.addTextChangedListener(watcher)
        binding.krwEditView.requestFocus()

        //키보드 보이게 하는 부분
	    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.krwEditView, InputMethodManager.SHOW_IMPLICIT)
    }
}